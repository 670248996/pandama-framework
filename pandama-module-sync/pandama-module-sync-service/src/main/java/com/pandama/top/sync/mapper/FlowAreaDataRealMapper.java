package com.pandama.top.sync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandama.top.sync.entity.FlowAreaData;
import com.pandama.top.sync.entity.FlowAreaDataReal;
import org.springframework.stereotype.Repository;

/**
 * User: ydh
 */
@Repository
public interface FlowAreaDataRealMapper extends BaseMapper<FlowAreaDataReal> {

    void insertFlowAreaData(FlowAreaData data);
    void insertFlowAreaDataFlow(FlowAreaData data);
    void insertFlowAreaDataPressure(FlowAreaData data);
    void insertFlowAreaFlow(FlowAreaData data);
    void insertFlowAreaDataForwardflow(FlowAreaData data);
    void insertFlowAreaDataReverseflow(FlowAreaData data);
    void insertFlowAreaDataRealTotal(FlowAreaData data);

}
