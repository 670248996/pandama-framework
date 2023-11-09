package com.pandama.top.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandama.top.user.entity.SysUserDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门用户mapper
 *
 * @author 王强
 * @date 2023-07-08 15:47:57
 */
@Repository
public interface UserDeptMapper extends BaseMapper<SysUserDept> {

    /**
     * @param deptIds 部门id列表
     * @description: 获取指定部门列表下的关联用户列表
     * @author: 白剑民
     * @date: 2022-10-26 14:44:04
     * @return: java.util.List<java.lang.Long>
     * @version: 1.0
     */
    List<Long> getUserIdsByDeptIds(@Param("deptIds") List<Long> deptIds);

    /**
     * @param userIds 用户id列表
     * @description: 获取指定用户列表下的关联部门列表
     * @author: 白剑民
     * @date: 2022-10-26 14:44:04
     * @return: java.util.List<java.lang.Long>
     * @version: 1.0
     */
    List<Long> getDeptIdsByUserIds(@Param("userIds") List<Long> userIds);

    /**
     * @param deptIds 部门id列表
     * @description: 获取指定部门列表下的部门用户列表
     * @author: 白剑民
     * @date: 2022-10-26 14:44:04
     * @return: java.util.List<java.lang.Long>
     * @version: 1.0
     */
    List<SysUserDept> getListByDeptIds(@Param("deptIds") List<Long> deptIds);

    /**
     * @param userIds 用户id列表
     * @description: 获取指定用户列表下的用户部门列表
     * @author: 白剑民
     * @date: 2022-10-26 14:44:04
     * @return: java.util.List<java.lang.Long>
     * @version: 1.0
     */
    List<SysUserDept> getListByUserIds(@Param("userIds") List<Long> userIds);

    /**
     * @param deptIds 部门id列表
     * @description: 删除指定部门列表下的关联数据
     * @author: 白剑民
     * @date: 2022-10-26 14:44:04
     * @return: java.util.List<java.lang.Long>
     * @version: 1.0
     */
    void deleteByDeptIds(@Param("deptIds") List<Long> deptIds);

    /**
     * @param userIds 用户id列表
     * @description: 删除指定用户列表下的关联数据
     * @author: 白剑民
     * @date: 2022-10-26 14:44:04
     * @return: java.util.List<java.lang.Long>
     * @version: 1.0
     */
    void deleteByUserIds(@Param("userIds") List<Long> userIds);
}
