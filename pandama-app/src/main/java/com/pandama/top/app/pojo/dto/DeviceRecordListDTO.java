package com.pandama.top.app.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @description: 设备列表入参
 * @author: 王强
 * @dateTime: 2023-04-20 13:44:01
 */
@Data
public class DeviceRecordListDTO {
    private String deviceCode;
    private String deviceName;
    private String power;
    private String customerName;
    private Integer operationNum;
    private Integer operationType;
    private String operationTypeName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING,timezone = "GMT+8")
    private LocalDateTime operationTime;
    private String createUserName;
}
