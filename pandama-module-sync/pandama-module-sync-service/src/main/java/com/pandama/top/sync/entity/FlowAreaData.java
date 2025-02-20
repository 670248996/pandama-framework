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
@TableName("jx_flow_area")
public class FlowAreaData {

    private Timestamp ts;
    private BigDecimal forwardflow;
    private BigDecimal reverseflow;
    private BigDecimal reading;
    private BigDecimal pressure;
    private BigDecimal valuestatus;
    private BigDecimal flow;
    private BigDecimal voltage;
    private String deviceid;
    private Timestamp createTime;
    private String origin;
    private String orgid;
    private int mark;
    private String flowModify;
    private BigDecimal realTotal;
}
