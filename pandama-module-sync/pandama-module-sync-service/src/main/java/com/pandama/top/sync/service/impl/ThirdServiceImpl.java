package com.pandama.top.sync.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.pandama.top.core.utils.BeanConvertUtils;
import com.pandama.top.sync.entity.FlowAreaData;
import com.pandama.top.sync.entity.FlowData;
import com.pandama.top.sync.entity.PressureData;
import com.pandama.top.sync.mapper.FlowAreaDataMapper;
import com.pandama.top.sync.mapper.FlowAreaDataRealMapper;
import com.pandama.top.sync.mapper.FlowDataMapper;
import com.pandama.top.sync.mapper.PressureDataMapper;
import com.pandama.top.sync.pojo.vo.XYDeviceDataVo;
import com.pandama.top.sync.service.ThirdService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * @author: hyj
 * @date 2021/3/24 10:58
 */
@Slf4j
@Service
public class ThirdServiceImpl implements ThirdService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private FlowDataMapper flowDataMapper;

    @Autowired
    private PressureDataMapper pressureDataMapper;

    @Autowired
    private FlowAreaDataMapper flowAreaDataMapper;

    @Autowired
    private FlowAreaDataRealMapper flowAreaDataRealMapper;

    //亚信登录的用户
    private static final String USER_ID = "SW_GIS";
    //亚信登录的密码
    private static final String PWD = "u/ZnVVi1g5LqBAly8VEQDQ==";
    //亚信登录的appKey
    private static final String APP_KEY = "sjfw_zh";
    //亚信登录的token
    private static String TOKEN = "";


    /**
     * @description: 获取亚信的授权token
     * @author: hyj
     * @date: 2023-11-22 16:27:51
     * @return: java.lang.String
     * @version: 1.0
     */
    private void yxLogin() {
        Map<String, Object> map = new HashMap<>();
        String url = "http://10.2.192.42:8083/_api/api/auth?userId={userId}&pwd={pwd}&appkey={appkey}";
        map.put("userId", USER_ID);
        map.put("pwd", PWD);
        map.put("appkey", APP_KEY);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, map);
        String body = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        assert jsonObject != null;
        TOKEN = jsonObject.getString("token");
    }

    /**
     * 同步 深圳智能 RTU 设备数据
     *
     * @param start        开始
     * @param end          结束
     * @return java.util.List<com.pandama.top.sync.entity.xyDeviceVo.XYDeviceDataVo>
     * @author 王强
     */
    public List<XYDeviceDataVo> syncSzznRtuDeviceData(String start, String end) {
        Map<String, Object> map = new HashMap<>(16);
        String url = "http://10.2.192.42:8083/_api/api/szznrtu_data_new_info?appKey={appKey}&userId={userId}&token={token}" +  "&date_start={date_start}&date_end={date_end}";
        map.put("appKey", APP_KEY);
        map.put("userId", USER_ID);
        map.put("token", TOKEN);
        map.put("date_start", start);
        map.put("date_end", end);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, map);
        String body = responseEntity.getBody();
        List<SzznDeviceData> deviceDataList = JSONObject.parseArray(body, SzznDeviceData.class);
        return (List<XYDeviceDataVo>) BeanConvertUtils.convertCollection(deviceDataList, XYDeviceDataVo::new, (s, t) -> {
            t.setForwardFlow(s.getForwardTotalFlow());
            t.setReverseFlow(s.getReverseTotalFlow());
        }).orElse(new ArrayList<>());
    }

    /**
     * 同步 和达 RTU 设备数据
     *
     * @param start        开始
     * @param end          结束
     * @return java.util.List<com.pandama.top.sync.entity.xyDeviceVo.XYDeviceDataVo>
     * @author 王强
     */
    public List<XYDeviceDataVo> syncHdRtuDeviceData(String start, String end) {
        Map<String, Object> map = new HashMap<>(16);
        String url = "http://10.2.192.42:8083/_api/api/gis_hdds?appKey={appKey}&userId={userId}&token={token}" +
                "&date_start={date_start}&date_end={date_end}";
        map.put("appKey", APP_KEY);
        map.put("userId", USER_ID);
        map.put("token", TOKEN);
        map.put("date_start", start);
        map.put("date_end", end);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, map);
        String body = responseEntity.getBody();
        List<JSONObject> dataList = JSONObject.parseArray(body, JSONObject.class);
        if (CollectionUtils.isEmpty(dataList)) {
            return new ArrayList<>();
        }
        Map<String, List<JSONObject>> collect = dataList.stream().collect(Collectors.groupingBy(p -> p.get("device_id") + "_" + p.get("read_time")));
        List<HdDeviceData> deviceDataList = collect.values().stream().map(list -> {
            JSONObject data = list.get(0);
            for (JSONObject json : list) {
                data.put(String.valueOf(json.get("channel_name")), json.get("data_value"));
            }
            return JSONObject.parseObject(JSON.toJSONString(data), HdDeviceData.class);
        }).collect(Collectors.toList());
        return (List<XYDeviceDataVo>) BeanConvertUtils.convertCollection(deviceDataList, XYDeviceDataVo::new, (s, t) -> {
            t.setForwardFlow(s.getPositiveCumulative());
            t.setReverseFlow(s.getNegativeCumulative());
        }).orElse(new ArrayList<>());
    }

    /**
     * 同步亚信的数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @author 王强
     */
    @Override
    public void syncYXDeviceDataToDB(LocalDate startDate, LocalDate endDate) {
        StopWatch stopWatch = new StopWatch();
        log.info("开始同步");
        stopWatch.start();
        // 如果设备不为空，则
        yxLogin();
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        // 创建两个CompletableFuture任务
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            //从亚信中台获取深圳智能RTU设备数据
            List<XYDeviceDataVo> deviceDataList = syncSzznRtuDeviceData(startDate.toString(), endDate.toString());
            saveFlowData(deviceDataList);
        });
        futures.add(future1);
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            //从亚信中台获取和达RTU设备数据
            List<XYDeviceDataVo> deviceDataList = syncHdRtuDeviceData(startDate.toString(), endDate.toString());
            saveFlowData(deviceDataList);
        });
        futures.add(future2);
        // 等待所有的异步任务完成
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join();
        stopWatch.stop();
        log.info("完成同步: {}s", stopWatch.getTotalTimeSeconds());

    }
    private void saveFlowData(List<XYDeviceDataVo> deviceDataList) {
        Map<String, FlowData> insertFlowDataMap = new HashMap<>(16);
        Map<String, PressureData> insertPressureDataMap = new HashMap<>(16);
        Map<String, FlowAreaData> insertFlowAreaDataMap =new HashMap<>(16);
        Map<String, FlowAreaData> insertFlowAreaRealDataMap = new HashMap<>(16);
        deviceDataList.forEach(vo -> {
            //获取读数时间
            Timestamp ts = Timestamp.valueOf(vo.getReadTime());
            //正向流量
            BigDecimal forwardFlow = StringUtils.isNotBlank(vo.getForwardFlow()) ? new BigDecimal(vo.getForwardFlow()) : BigDecimal.ZERO;
            //反向流量
            BigDecimal reverseFlow = StringUtils.isNotBlank(vo.getReverseFlow()) ? new BigDecimal(vo.getReverseFlow()) : BigDecimal.ZERO;
            //瞬时流量
            BigDecimal instantFlow = StringUtils.isNotBlank(vo.getInstantFlow()) ? new BigDecimal(vo.getInstantFlow()) : BigDecimal.ZERO;
            //压力
            BigDecimal pressure = StringUtils.isNotBlank(vo.getPressure()) ? new BigDecimal(vo.getPressure()) : BigDecimal.ZERO;
            //1.插入流量表
            FlowData flowData = new FlowData();
            flowData.setDeviceid(vo.getImei());
            flowData.setCreateTime(new Timestamp(System.currentTimeMillis()));
            flowData.setForwardflow(forwardFlow);
            flowData.setForwarderrortype(BigDecimal.ZERO);
            flowData.setReverseflow(reverseFlow);
            flowData.setReverseerrortype(BigDecimal.ZERO);
            flowData.setInstantflow(instantFlow);
            flowData.setFlowerrortype(BigDecimal.ZERO);
            flowData.setFlowspeederrortype(new BigDecimal(-99));
            flowData.setTs(ts);
            flowData.setOrgid("1");
            //数据来源 1kafka 2亚信中台
            flowData.setOrigin("2");
            insertFlowDataMap.put(vo.getImei() + "-" + ts, flowData);
            //2.插入压力表
            PressureData pressureData = new PressureData();
            pressureData.setDeviceid(vo.getImei());
            pressureData.setCreateTime(new Timestamp(System.currentTimeMillis()));
            pressureData.setErrortype(0);
            pressureData.setPressure(pressure);
            pressureData.setTs(ts);
            pressureData.setOrgid("1");
            //数据来源 1kafka 2亚信中台
            pressureData.setOrigin("2");
            insertPressureDataMap.put(vo.getImei() + "-" + ts, pressureData);
            //3.插入实时表
            FlowAreaData flowAreaData = new FlowAreaData();
            flowAreaData.setDeviceid(vo.getImei());
            flowAreaData.setCreateTime(new Timestamp(System.currentTimeMillis()));
            flowAreaData.setForwardflow(forwardFlow);
            flowAreaData.setReverseflow(reverseFlow);
            flowAreaData.setFlow(instantFlow);
            flowAreaData.setPressure(pressure);
            flowAreaData.setFlowModify(vo.getInstantFlow());
            flowAreaData.setTs(ts);
            flowAreaData.setOrgid("1");
            //数据来源 1kafka 2亚信中台
            flowAreaData.setOrigin("2");
            flowAreaData.setMark(1);
            insertFlowAreaDataMap.put(vo.getImei() + "-" + ts, flowAreaData);
            insertFlowAreaRealDataMap.put(vo.getImei(), flowAreaData);
        });
        int chunkSize = 1000;
        List<FlowData> insertFlowDataList = new ArrayList<>(insertFlowDataMap.values());
        List<List<FlowData>> chunks1 = IntStream.range(0, (int) Math.ceil((double) insertFlowDataList.size() / chunkSize))
                .mapToObj(n -> new ArrayList<>(insertFlowDataList.subList(n * chunkSize, Math.min((n + 1) * chunkSize, insertFlowDataList.size()))))
                .collect(Collectors.toList());
        chunks1.forEach(p -> flowDataMapper.batchInsertFlowData(p));

        List<PressureData> insertPressureDataList = new ArrayList<>(insertPressureDataMap.values());
        List<List<PressureData>> chunks2 = IntStream.range(0, (int) Math.ceil((double) insertPressureDataList.size() / chunkSize))
                .mapToObj(n -> new ArrayList<>(insertPressureDataList.subList(n * chunkSize, Math.min((n + 1) * chunkSize, insertPressureDataList.size()))))
                .collect(Collectors.toList());
        chunks2.forEach(p -> pressureDataMapper.batchInsertPressureData(p));

        List<FlowAreaData> insertFlowAreaDataList = new ArrayList<>(insertFlowAreaDataMap.values());
        List<List<FlowAreaData>> chunks3 = IntStream.range(0, (int) Math.ceil((double) insertFlowAreaDataList.size() / chunkSize))
                .mapToObj(n -> new ArrayList<>(insertFlowAreaDataList.subList(n * chunkSize, Math.min((n + 1) * chunkSize, insertFlowAreaDataList.size()))))
                .collect(Collectors.toList());
        chunks3.forEach(p -> flowAreaDataMapper.batchInsertFlowAreaData(p));

        insertFlowAreaRealDataMap.values().forEach(item -> flowAreaDataRealMapper.insertFlowAreaData(item));
    }

    @Data
    static class SzznDeviceData {
        /**
         * data_source
         */
        @JSONField(name = "data_source")
        private String dataSource;
        /**
         * 物联网设备id
         */
        @JSONField(name = "device_id")
        private String deviceId;
        /**
         * 正向流量
         */
        @JSONField(name = "forward_total_flow")
        private String forwardTotalFlow;
        /**
         * 逆向流量
         */
        @JSONField(name = "reverse_total_flow")
        private String reverseTotalFlow;
        /**
         * 瞬时流量
         */
        @JSONField(name = "instant_flow")
        private String instantFlow;
        /**
         * 实际流量
         */
        @JSONField(name = "real_flow")
        private String realFlow;
        /**
         * 压力
         */
        @JSONField(name = "pressure")
        private String pressure;
        /**
         * 读数时间
         */
        @JSONField(name = "read_time")
        private String readTime;
        /**
         * imei编号
         */
        @JSONField(name = "imei")
        private String imei;
    }

    @Data
    static class HdDeviceData {
        /**
         * 物联网设备id
         */
        @JSONField(name = "device_id")
        private String deviceId;
        /**
         * 物联网设备名称
         */
        @JSONField(name = "device_name")
        private String deviceName;
        /**
         * 正向流量
         */
        @JSONField(name = "positive_cumulative")
        private String positiveCumulative;
        /**
         * 逆向流量
         */
        @JSONField(name = "negative_cumulative")
        private String negativeCumulative;
        /**
         * 瞬时流量
         */
        @JSONField(name = "instant_flow")
        private String instantFlow;
        /**
         * 压力
         */
        @JSONField(name = "pressure")
        private String pressure;
        /**
         * 读数时间
         */
        @JSONField(name = "read_time")
        private String readTime;
        /**
         * imei编号
         */
        @JSONField(name = "imei")
        private String imei;
    }
}