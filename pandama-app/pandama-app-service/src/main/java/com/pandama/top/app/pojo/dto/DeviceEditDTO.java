package com.pandama.top.app.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 设备编辑入参
 *
 * @author 王强
 * @date 2023-07-08 15:11:06
 */
@Data
public class DeviceEditDTO {
    @NotNull(message = "设备ID不能为null")
    private Long id;
    private String deviceCode;
    private String deviceName;
    private String power;
}
