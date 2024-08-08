package com.pandama.top.app.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pandama.top.app.example.CommonUtils;
import com.pandama.top.core.global.response.Response;
import com.pandama.top.milvus.utils.MilvusUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 设备控制器
 *
 * @author 王强
 * @date 2023-07-08 11:55:46
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/milvus")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MilvusController {

    private final MilvusUtils milvusUtils;

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public Response<?> generate(String collectionName, String partitionName, Integer num, Integer dimension) {
        List<JsonObject> featureDataList = new ArrayList<>();
        List<Float> floats = CommonUtils.generateFloatVector(dimension);
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(floats);
        Stream.iterate(0, n -> n + 1).parallel().limit(num).forEach(item -> {
            JsonObject featureData = new JsonObject();
//                    ByteBuffer buf = CommonUtils.generateFloat16Vector(dimension, true);
            featureData.addProperty("id", IdWorker.getId());
            featureData.add("feature", jsonElement);
            featureDataList.add(featureData);
        });
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            // 保存向量数据到milvus
            milvusUtils.insertRows(collectionName, partitionName, featureDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
        return Response.success();
    }


    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public Response<?> load(String collectionName, String partitionName) {
        milvusUtils.loadPartition(collectionName, partitionName);
        return Response.success();
    }
}
