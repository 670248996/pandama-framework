package com.pandama.top.app.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 设备列表出参
 *
 * @author 王强
 * @date 2023-07-08 15:11:17
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
