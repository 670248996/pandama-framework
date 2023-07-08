package com.pandama.top.app.pojo.vo;

import lombok.Data;

/**
 * 设备详情出参
 *
 * @author 王强
 * @date 2023-07-08 15:11:16
 */
@Data
public class DeviceDetailVO {
    private Long id;
    private String deviceCode;
    private String deviceName;
    private String power;
    private Integer store;
}
