package com.pandama.top.user.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pandama.top.user.api.pojo.dto.PermissionCreateDTO;
import com.pandama.top.user.api.pojo.dto.PermissionSearchDTO;
import com.pandama.top.user.api.pojo.dto.PermissionUpdateDTO;
import com.pandama.top.user.api.pojo.vo.*;
import com.pandama.top.user.biz.entity.SysMenu;

import java.util.List;

/**
 * @description: 权限信息接口类
 * @author: 白剑民
 * @dateTime: 2022/10/17 17:37
 */
public interface MenuService extends IService<SysMenu> {

    /**
     * @param dto 创建权限传参
     * @description: 创建权限
     * @author: 白剑民
     * @date: 2022-10-31 17:40:09
     * @return: com.gientech.iot.user.entity.vo.PermissionCreateResultVO
     * @version: 1.0
     */
    MenuCreateResultVO create(PermissionCreateDTO dto);

    /**
     * @param id 权限id
     * @description: 权限详情
     * @author: 白剑民
     * @date: 2022-10-31 17:40:09
     * @return: com.gientech.iot.user.entity.vo.PermissionDetailResultVO
     * @version: 1.0
     */
    MenuDetailResultVO detail(Long id);

    /**
     * @param dto 权限信息更新传参
     * @description: 更新
     * @author: 王强
     * @date: 2023-05-22 16:11:17
     * @return: void
     * @version: 1.0
     */
    void update(PermissionUpdateDTO dto);

    /**
     * @param permissionIds 权限id列表
     * @description: 删除权限信息
     * @author: 白剑民
     * @date: 2022-10-31 17:51:25
     * @version: 1.0
     */
    void delete(List<Long> permissionIds);

    /**
     * @description: 权限列表（根据用户权限查询权限列表）
     * @author: 白剑民
     * @date: 2022-10-31 17:51:25
     * @version: 1.0
     */
    List<MenuSearchResultVO> list(PermissionSearchDTO dto);

    /**
     * @param roleId 角色id
     * @description: 根据角色id获取其下所有权限列表
     * @author: 白剑民
     * @date: 2022-10-31 13:39:20
     * @return: java.util.List<com.gientech.iot.user.entity.Permission>
     * @version: 1.0
     */
    List<MenuSearchResultVO> getListByRoleIds(List<Long> roleIds);

    /**
     * @param roleId 角色id
     * @description: 通过角色id获取树下拉
     * @author: 王强
     * @date: 2023-05-23 09:58:32
     * @return: TreeSelectVO<PermissionTreeVO>
     * @version: 1.0
     */
    TreeSelectVO getTreeSelectByRoleId(Long roleId);

    /**
     * @param roleId        角色id
     * @param permissionIds 权限id列表
     * @description: 分配角色权限
     * @author: 白剑民
     * @date: 2022-10-31 16:54:21
     * @version: 1.0
     */
    void assignPermission(Long roleId, List<Long> permissionIds);

    /**
     * @param menuId 权限id
     * @param status     启用或禁用
     * @description: 启用或禁用权限
     * @author: 白剑民
     * @date: 2022-10-31 17:06:53
     * @version: 1.0
     */
    void changePermissionStatus(Long menuId, Boolean status);
}
