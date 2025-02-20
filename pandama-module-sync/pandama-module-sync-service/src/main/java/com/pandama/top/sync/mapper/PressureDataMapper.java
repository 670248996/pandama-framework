package com.pandama.top.sync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandama.top.sync.entity.PressureData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: ydh
 */
@Repository
public interface PressureDataMapper extends BaseMapper<PressureData> {

    void insertPressureData(PressureData data);

    void batchInsertPressureData(@Param("list") List<PressureData> list);

}
