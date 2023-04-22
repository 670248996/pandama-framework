package com.pandama.top.app.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 设备编辑入参
 * @author: 王强
 * @dateTime: 2023-04-20 13:44:03
 */
@Data
public class DeviceEditDTO {
    @NotNull(message = "设备ID不能为null")
    private Long id;
    private String deviceCode;
    private String deviceName;
    private String power;
}
