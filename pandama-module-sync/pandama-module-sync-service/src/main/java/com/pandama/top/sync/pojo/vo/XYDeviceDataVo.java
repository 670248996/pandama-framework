/**
  * Copyright 2021 json.cn 
  */
package com.pandama.top.sync.pojo.vo;

import lombok.Data;


@Data
public class XYDeviceDataVo {
    /**
     * 物联网设备id
     */
    private String deviceId;
    /**
     * 正向流量
     */
    private String forwardFlow;
    /**
     * 逆向流量
     */
    private String reverseFlow;
    /**
     * 实际流量
     */
    private String realFlow;
    /**
     * 压力
     */
    private String pressure;
    /**
     * 瞬时流量
     */
    private String instantFlow;
    /**
     * data_source
     */
    private String dataSource;
    /**
     * 读数时间
     */
    private String readTime;
    /**
     * imei编号
     */
    private String imei;
}