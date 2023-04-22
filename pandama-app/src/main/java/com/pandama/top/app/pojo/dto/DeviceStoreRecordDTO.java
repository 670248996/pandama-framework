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
public class DeviceStoreRecordDTO {
    @NotNull(message = "设备ID不能为null")
    private Long deviceId;
    private StoreOperationTypeEnum operation;
}
