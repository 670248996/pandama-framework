package com.pandama.top.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandama.top.user.pojo.dto.DeptSearchDTO;
import com.pandama.top.user.entity.SysDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 部门信息Mapper接口
 * @author: 白剑民
 * @dateTime: 2022/10/21 15:55
 */
@Repository
public interface DeptMapper extends BaseMapper<SysDept> {

    /**
     * @param dto 入参
     * @description: 列表
     * @author: 王强
     * @date: 2023-05-29 10:23:49
     * @return: List<SysDepartment>
     * @version: 1.0
     */
    List<SysDept> list(@Param("dto") DeptSearchDTO dto);

    /**
     * @param parentIds     父级部门id
     * @param showParent 是否展示父级部门
     * @description: 获取父级部门底下的子部门列表(包含父级部门)
     * @author: 白剑民
     * @date: 2022-10-25 10:56:00
     * @return: java.util.List<com.gientech.iot.user.entity.DepartmentTreeVO>
     * @version: 1.0
     */
    List<SysDept> getDeptListByParentIds(@Param("parentIds") List<Long> parentIds, @Param("showParent") boolean showParent);

}