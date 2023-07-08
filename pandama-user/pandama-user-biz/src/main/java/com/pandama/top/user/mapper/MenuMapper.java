package com.pandama.top.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandama.top.user.pojo.dto.MenuSearchDTO;
import com.pandama.top.user.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 菜单信息Mapper接口
 * @author: 白剑民
 * @dateTime: 2022/10/21 15:55
 */
@Repository
public interface MenuMapper extends BaseMapper<SysMenu> {

    /**
     * 列表
     *
     * @param dto 入参
     * @return java.util.List<com.pandama.top.user.entity.SysMenu>
     * @author 王强
     */
    List<SysMenu> list(@Param("dto") MenuSearchDTO dto);

    /**
     * 获取菜单列表通过父id
     *
     * @param parentIds  id列表
     * @param showParent 显示父
     * @return java.util.List<com.pandama.top.user.entity.SysMenu>
     * @author 王强
     */
    List<SysMenu> getMenuListByParentIds(@Param("parentIds") List<Long> parentIds, @Param("showParent") boolean showParent);

    /**
     * 获取菜单列表通过角色id
     *
     * @param roleIds 角色id列表
     * @return java.util.List<com.pandama.top.user.entity.SysMenu>
     * @author 王强
     */
    List<SysMenu> getMenuListByRoleIds(@Param("roleIds") List<Long> roleIds);
}
