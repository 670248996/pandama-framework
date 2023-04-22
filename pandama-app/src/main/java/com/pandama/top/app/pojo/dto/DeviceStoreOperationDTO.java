package com.pandama.top.app.pojo.dto;

import com.pandama.top.app.enums.StoreOperationTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 设备库存操作入参
 * @author: 王强
 * @dateTime: 2023-04-19 12:00:27
 */
@Data
public class DeviceStoreOperationDTO {
    @NotNull(message = "设备ID不能为null")
    private Long id;
    private String customerName;
    @NotNull(message = "操作数量不能为null")
    private Integer operationNum;
    private StoreOperationTypeEnum operation;
}
