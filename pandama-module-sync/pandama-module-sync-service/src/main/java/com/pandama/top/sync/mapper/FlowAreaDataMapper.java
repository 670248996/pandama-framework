package com.pandama.top.sync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandama.top.sync.entity.FlowAreaData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: ydh
 */
@Repository
public interface FlowAreaDataMapper extends BaseMapper<FlowAreaData> {

    void insertFlowAreaData(FlowAreaData data);
    void insertFlowAreaDataFlow(FlowAreaData data);
    void insertFlowAreaDataPressure(FlowAreaData data);
    void insertFlowAreaFlow(FlowAreaData data);
    void insertFlowAreaDataForwardflow(FlowAreaData data);
    void insertFlowAreaDataReverseflow(FlowAreaData data);
    void insertFlowAreaDataRealTotal(FlowAreaData data);

    void batchInsertFlowAreaData(@Param("list") List<FlowAreaData> list);
    void batchInsertFlowAreaDataFlow(@Param("list") List<FlowAreaData> list);
    void batchInsertFlowAreaDataPressure(@Param("list") List<FlowAreaData> list);
    void batchInsertFlowAreaFlow(@Param("list") List<FlowAreaData> list);
    void batchInsertFlowAreaDataForwardflow(@Param("list") List<FlowAreaData> list);
    void batchInsertFlowAreaDataReverseflow(@Param("list") List<FlowAreaData> list);
    void batchInsertFlowAreaDataRealTotal(@Param("list") List<FlowAreaData> data);

}
