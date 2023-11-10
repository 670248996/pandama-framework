package com.pandama.top.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandama.top.user.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 角色与菜单关联Mapper接口
 * @author: 白剑民
 * @dateTime: 2022/10/17 17:32
 */
@Repository
public interface RoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 获取菜单id通过角色id
     *
     * @param roleIds 角色id列表
     * @return java.util.List<java.lang.Long>
     * @author 王强
     */
    List<Long> getMenuIdsByRoleIds(@Param("roleIds") List<Long> roleIds);

    /**
     * @param MenuIds 菜单id列表
     * @description: 获取指定菜单列表下的关联角色列表
     * @author: 白剑民
     * @date: 2022-10-31 17:53:34
     * @return: java.util.List<java.lang.Long>
     * @version: 1.0
     */
    List<Long> getRoleIdsByMenuIds(@Param("menuIds") List<Long> menuIds);

}
