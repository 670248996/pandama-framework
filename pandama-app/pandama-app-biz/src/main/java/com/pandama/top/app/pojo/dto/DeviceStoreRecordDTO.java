package com.pandama.top.app.pojo.dto;

import com.pandama.top.app.enums.StoreOperationTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 设备商店记录入参
 *
 * @author 王强
 * @date 2023-07-08 15:11:13
 */
@Data
public class DeviceStoreRecordDTO {
    @NotNull(message = "设备ID不能为null")
    private Long deviceId;
    private StoreOperationTypeEnum operation;
}
