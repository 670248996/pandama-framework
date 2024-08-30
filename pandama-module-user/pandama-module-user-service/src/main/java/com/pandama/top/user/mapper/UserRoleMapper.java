package com.pandama.top.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandama.top.user.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色mapper
 *
 * @author 王强
 * @date 2023-07-08 15:48:51
 */
@Repository
public interface UserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 获取用户id通过角色id
     *
     * @param roleIds 角色id列表
     * @return java.util.List<java.lang.Long>
     * @author 王强
     */
    List<Long> getUserIdsByRoleIds(@Param("roleIds") List<Long> roleIds);

    /**
     * 获取角色id通过用户id
     *
     * @param userIds 用户id列表
     * @return java.util.List<java.lang.Long>
     * @author 王强
     */
    List<Long> getRoleIdsByUserIds(@Param("userIds") List<Long> userIds);

}
