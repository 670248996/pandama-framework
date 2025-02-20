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
@TableName("jx_flow")
public class FlowData {

    private Timestamp ts;
    private BigDecimal forwardflow;
    private BigDecimal forwarderrortype;
    private BigDecimal reverseflow;
    private BigDecimal reverseerrortype;
    private BigDecimal instantflow;
    private BigDecimal flowerrortype;
    private BigDecimal flowspeed;
    private BigDecimal flowspeederrortype;
    private String deviceid;
    private Timestamp createTime;
    private String origin;
    private String orgid;

}
