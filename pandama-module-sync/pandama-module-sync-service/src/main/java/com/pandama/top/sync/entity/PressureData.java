package com.pandama.top.sync.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author ydh
 * @date 2020\12\24 0024
 */
@Data
@Accessors(chain = true)
@TableName("jx_pressure")
public class PressureData {

    private Timestamp ts;
    private BigDecimal pressure;
    private int errortype;
    private String deviceid;
    private Timestamp createTime;
    private String origin;
    private String orgid;
}
