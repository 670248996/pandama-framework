package com.pandama.top.app.pojo.vo;

import lombok.Data;

/**
 * @description: 设备详情出参
 * @author: 王强
 * @dateTime: 2023-04-19 11:13:16
 */
@Data
public class DeviceDetailVO {
    private Long id;
    private String deviceCode;
    private String deviceName;
    private String power;
    private Integer store;
}
