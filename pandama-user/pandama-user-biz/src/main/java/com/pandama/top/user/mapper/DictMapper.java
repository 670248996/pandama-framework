package com.pandama.top.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandama.top.user.pojo.dto.DictSearchDTO;
import com.pandama.top.user.entity.SysDict;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字典mapper
 *
 * @author 王强
 * @date 2023-07-08 15:48:09
 */
@Repository
public interface DictMapper extends BaseMapper<SysDict> {

    /**
     * 列表
     *
     * @param dto 入参
     * @return java.util.List<com.pandama.top.user.entity.SysDict>
     * @author 王强
     */
    List<SysDict> list(@Param("dto") DictSearchDTO dto);

    /**
     * 获取字典列表通过父id
     *
     * @param parentIds  父级部门id
     * @param showParent 是否展示父级部门
     * @return java.util.List<com.pandama.top.user.entity.SysDict>
     * @author 王强
     */
    List<SysDict> getDictListByParentIds(@Param("parentIds") List<Long> parentIds, @Param("showParent") boolean showParent);
}
