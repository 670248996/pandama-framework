package com.pandama.top.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pandama.top.user.pojo.dto.MenuCreateDTO;
import com.pandama.top.user.pojo.dto.MenuSearchDTO;
import com.pandama.top.user.pojo.dto.MenuUpdateDTO;
import com.pandama.top.user.entity.SysMenu;
import com.pandama.top.user.pojo.vo.MenuDetailResultVO;
import com.pandama.top.user.pojo.vo.MenuSearchResultVO;
import com.pandama.top.user.pojo.vo.TreeSelectVO;

import java.util.List;

/**
 * @description: 菜单信息接口类
 * @author: 白剑民
 * @dateTime: 2022/10/17 17:37
 */
public interface MenuService extends IService<SysMenu> {

    /**
     * @param dto 创建菜单传参
     * @description: 创建
     * @author: 王强
     * @date: 2023-06-16 14:43:58
     * @return: MenuCreateResultVO
     * @version: 1.0
     */
    void create(MenuCreateDTO dto);

    /**
     * @param id 菜单id
     * @description: 菜单详情
     * @author: 白剑民
     * @date: 2022-10-31 17:40:09
     * @return: com.gientech.iot.user.entity.vo.MenuDetailResultVO
     * @version: 1.0
     */
    MenuDetailResultVO detail(Long id);

    /**
     * @param dto 菜单信息更新传参
     * @description: 更新
     * @author: 王强
     * @date: 2023-05-22 16:11:17
     * @return: void
     * @version: 1.0
     */
    void update(MenuUpdateDTO dto);

    /**
     * @param menuIds 菜单id列表
     * @description: 删除菜单信息
     * @author: 白剑民
     * @date: 2022-10-31 17:51:25
     * @version: 1.0
     */
    void delete(List<Long> menuIds);

    /**
     * @description: 菜单列表（根据用户菜单查询菜单列表）
     * @author: 白剑民
     * @date: 2022-10-31 17:51:25
     * @version: 1.0
     */
    List<MenuSearchResultVO> list(MenuSearchDTO dto);

    /**
     * @param roleIds 角色id
     * @description: 根据角色id获取其下所有菜单列表
     * @author: 王强
     * @date: 2023-06-16 15:04:07
     * @return: List<MenuSearchResultVO>
     * @version: 1.0
     */
    List<MenuSearchResultVO> getListByRoleIds(List<Long> roleIds);

    /**
     * @param dto 入参
     * @description: 菜单树
     * @author: 王强
     * @date: 2023-06-17 10:05:40
     * @return: TreeSelectVO
     * @version: 1.0
     */
    TreeSelectVO tree(MenuSearchDTO dto);

    /**
     * @param roleId 角色id
     * @description: 通过角色id获取树下拉
     * @author: 王强
     * @date: 2023-05-23 09:58:32
     * @return: TreeSelectVO
     * @version: 1.0
     */
    TreeSelectVO getTreeSelectByRoleId(Long roleId);

    /**
     * @param roleId        角色id
     * @param menuIds 菜单id列表
     * @description: 分配角色菜单
     * @author: 白剑民
     * @date: 2022-10-31 16:54:21
     * @version: 1.0
     */
    void assignMenu(Long roleId, List<Long> menuIds);

    /**
     * @param menuId 菜单id
     * @param status 启用或禁用
     * @description: 启用或禁用菜单
     * @author: 白剑民
     * @date: 2022-10-31 17:06:53
     * @version: 1.0
     */
    void changeStatus(Long menuId, Boolean status);
}
