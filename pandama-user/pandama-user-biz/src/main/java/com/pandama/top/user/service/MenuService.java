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
 * 菜单服务
 *
 * @author 王强
 * @date 2023-07-08 15:55:46
 */
public interface MenuService extends IService<SysMenu> {

    /**
     * 创建
     *
     * @param dto 创建菜单传参
     * @return void
     * @author 王强
     */
    void create(MenuCreateDTO dto);

    /**
     * 详情
     *
     * @param id 菜单id
     * @return com.pandama.top.user.pojo.vo.MenuDetailResultVO
     * @author 王强
     */
    MenuDetailResultVO detail(Long id);

    /**
     * 更新
     *
     * @param dto 菜单信息更新传参
     * @return void
     * @author 王强
     */
    void update(MenuUpdateDTO dto);

    /**
     * 删除
     *
     * @param ids 菜单id列表
     * @return void
     * @author 王强
     */
    void delete(List<Long> ids);

    /**
     * 列表
     *
     * @param dto 入参
     * @return java.util.List<com.pandama.top.user.pojo.vo.MenuSearchResultVO>
     * @author 王强
     */
    List<MenuSearchResultVO> list(MenuSearchDTO dto);

    /**
     * 获取列表通过角色id
     *
     * @param roleIds 角色id
     * @return java.util.List<com.pandama.top.user.pojo.vo.MenuSearchResultVO>
     * @author 王强
     */
    List<MenuSearchResultVO> getListByRoleIds(List<Long> roleIds);

    /**
     * 树
     *
     * @param dto 入参
     * @return com.pandama.top.user.pojo.vo.TreeSelectVO
     * @author 王强
     */
    TreeSelectVO tree(MenuSearchDTO dto);

    /**
     * 获取树查询通过角色id
     *
     * @param roleId 角色id
     * @return com.pandama.top.user.pojo.vo.TreeSelectVO
     * @author 王强
     */
    TreeSelectVO getTreeSelectByRoleId(Long roleId);

    /**
     * 分配菜单
     *
     * @param roleId  角色id
     * @param menuIds 菜单id列表
     * @return void
     * @author 王强
     */
    void assignMenu(Long roleId, List<Long> menuIds);

    /**
     * 改变状态
     *
     * @param menuId 菜单id
     * @param status 启用或禁用
     * @return void
     * @author 王强
     */
    void changeStatus(Long menuId, Boolean status);
}
