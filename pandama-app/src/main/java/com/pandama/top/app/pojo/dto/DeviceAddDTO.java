package com.pandama.top.app.pojo.dto;

import lombok.Data;

/**
 * @description: 设备添加入参
 * @author: 王强
 * @dateTime: 2023-04-19 12:00:27
 */
@Data
public class DeviceAddDTO {
    private String deviceType;
    private String deviceCode;
    private String deviceName;
    private String power;
    private Integer store;
    private String customerName;
}
