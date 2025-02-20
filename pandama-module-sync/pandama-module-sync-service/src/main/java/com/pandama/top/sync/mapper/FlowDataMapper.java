package com.pandama.top.sync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandama.top.sync.entity.FlowData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: ydh
 */
@Repository
public interface FlowDataMapper extends BaseMapper<FlowData> {

    void insertFlowData(FlowData data);

    void batchInsertFlowData(@Param("list") List<FlowData> list);

}
