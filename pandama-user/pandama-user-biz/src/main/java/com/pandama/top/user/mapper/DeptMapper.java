package com.pandama.top.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandama.top.user.pojo.dto.DeptSearchDTO;
import com.pandama.top.user.entity.SysDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门mapper
 *
 * @author 王强
 * @date 2023-07-08 15:47:55
 */
@Repository
public interface DeptMapper extends BaseMapper<SysDept> {

    /**
     * 列表
     *
     * @param dto 入参
     * @return java.util.List<com.pandama.top.user.entity.SysDept>
     * @author 王强
     */
    List<SysDept> list(@Param("dto") DeptSearchDTO dto);

    /**
     * 获取部门列表通过父id
     *
     * @param parentIds  父级部门id
     * @param showParent 是否展示父级部门
     * @return java.util.List<com.pandama.top.user.entity.SysDept>
     * @author 王强
     */
    List<SysDept> getDeptListByParentIds(@Param("parentIds") List<Long> parentIds, @Param("showParent") boolean showParent);

}
