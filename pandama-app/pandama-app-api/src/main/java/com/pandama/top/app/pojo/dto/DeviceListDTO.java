package com.pandama.top.app.pojo.dto;

import lombok.Data;

/**
 * @description: 设备列表入参
 * @author: 王强
 * @dateTime: 2023-04-20 13:44:01
 */
@Data
public class DeviceListDTO {
    private String deviceCode;
    private String deviceName;
    private String power;
}
