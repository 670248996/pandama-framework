package com.pandama.top.app.pojo.dto;

import lombok.Data;

/**
 * 设备列表入参
 *
 * @author 王强
 * @date 2023-07-08 15:11:08
 */
@Data
public class DeviceListDTO {
    private String deviceCode;
    private String deviceName;
    private String power;
}
