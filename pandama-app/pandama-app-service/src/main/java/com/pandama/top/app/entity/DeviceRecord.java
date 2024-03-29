package com.pandama.top.app.entity;

import com.pandama.top.mybatisplus.entity.BaseEntity;
import lombok.Data;

/**
 * 设备记录
 *
 * @author 王强
 * @date 2023-07-08 15:10:17
 */
@Data
public class DeviceRecord extends BaseEntity {
    /**
     * 设备ID
     */
    private Long deviceId;
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
    /**
     * 客户
     */
    private String customerName;
    /**
     * 操作数量
     */
    private Integer operationNum;

    /**
     * 操作类型(-1: 出库, 1:入库)
     */
    private Integer operationType;
}
