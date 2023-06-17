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
     * @param dto 入参
     * @description: 列表
     * @author: 王强
     * @date: 2023-05-29 10:23:54
     * @return: List<SysMenu>
     * @version: 1.0
     */
    List<SysMenu> list(@Param("dto") MenuSearchDTO dto);

    /**
     * @param parentIds id列表
     * @description: 获取指定根节点下的关联菜单列表
     * @author: 白剑民
     * @date: 2022-10-31 15:30:49
     * @return: java.util.List<java.lang.Long>
     * @version: 1.0
     */
    List<SysMenu> getMenuListByParentIds(@Param("parentIds") List<Long> parentIds, @Param("showParent") boolean showParent);

    /**
     * @param roleIds 角色id列表
     * @description: 根据角色id列表获取其下所有菜单列表
     * @author: 王强
     * @date: 2023-06-16 15:04:18
     * @return: List<SysMenu>
     * @version: 1.0
     */
    List<SysMenu> getMenuListByRoleIds(@Param("roleIds") List<Long> roleIds);
}
