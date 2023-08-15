package com.pandama.top.app.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 设备添加入参
 *
 * @author 王强
 * @date 2023-07-08 15:11:04
 */
@Data
public class DeviceAddDTO implements Serializable {
    private String deviceType;
    private String deviceCode;
    private String deviceName;
    private String power;
    private Integer store;
    private String customerName;
}
