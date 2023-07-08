package com.pandama.top.app.entity;

import com.pandama.top.mybatisplus.entity.BaseEntity;
import lombok.Data;

/**
 * 设备
 *
 * @author 王强
 * @date 2023-07-08 15:10:14
 */
@Data
public class Device extends BaseEntity {
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 设备编号
     */
    private String deviceCode;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 功率
     */
    private String power;
    /**
     * 库存
     */
    private Integer store;
    /**
     * 总入库数量
     */
    private Integer totalInputNum;
    /**
     * 总出库数量
     */
    private Integer totalOutputNum;
}
