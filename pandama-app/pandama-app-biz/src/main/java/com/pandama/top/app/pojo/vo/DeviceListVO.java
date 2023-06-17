package com.pandama.top.app.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 设备列表出参
 * @author: 王强
 * @dateTime: 2023-04-19 11:12:43
 */
@Data
public class DeviceListVO implements Serializable {
    private Long id;
    private String deviceType;
    private String deviceCode;
    private String deviceName;
    private String power;
    private Integer store;
}
