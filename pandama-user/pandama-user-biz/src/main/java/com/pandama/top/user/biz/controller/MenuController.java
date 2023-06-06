package com.pandama.top.user.biz.controller;

import com.pandama.top.global.response.Response;
import com.pandama.top.user.api.pojo.dto.PermissionCreateDTO;
import com.pandama.top.user.api.pojo.dto.PermissionSearchDTO;
import com.pandama.top.user.api.pojo.dto.PermissionUpdateDTO;
import com.pandama.top.user.api.pojo.vo.*;
import com.pandama.top.user.biz.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 权限信息控制层
 * @author: 白剑民
 * @dateTime: 2022/10/31 13:37
 */
@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Api(tags = "权限信息相关接口")
@Validated
@Slf4j
public class MenuController {

    private final MenuService menuService;

    /**
     * @param dto 创建权限信息传参
     * @description: 创建权限信息
     * @author: 白剑民
     * @date: 2022-10-31 17:44:18
     * @return: com.gientech.iot.global.response.Response<com.gientech.iot.user.entity.vo.PermissionCreateResultVO>
     * @version: 1.0
     */
    @ApiOperation("创建权限信息")
    @PostMapping
    public Response<MenuCreateResultVO> create(@Valid @RequestBody PermissionCreateDTO dto) {
        return Response.success(menuService.create(dto));
    }

    /**
     * @param menuId 权限信息详情传参
     * @description: 权限信息详情
     * @author: 白剑民
     * @date: 2022-10-31 17:44:18
     * @return: com.gientech.iot.global.response.Response<com.gientech.iot.user.entity.vo.PermissionDetailResultVO>
     * @version: 1.0
     */
    @ApiOperation("权限信息详情")
    @GetMapping
    public Response<MenuDetailResultVO> detail(
            @ApiParam("权限id，必填项")
            @NotNull(message = "权限id，permissionId不能为null")
            @RequestParam("menuId") Long menuId) {
        return Response.success(menuService.detail(menuId));
    }

    /**
     * @param dto 权限信息更新传参
     * @description: 权限信息更新
     * @author: 白剑民
     * @date: 2022-10-31 17:50:53
     * @return: com.gientech.iot.global.response.Response<java.lang.Void>
     * @version: 1.0
     */
    @ApiOperation("权限信息更新")
    @PutMapping
    public Response<Void> update(@Valid @RequestBody PermissionUpdateDTO dto) {
        menuService.update(dto);
        return Response.success();
    }

    /**
     * @param permissionIds 权限id列表
     * @description: 删除权限信息
     * @author: 白剑民
     * @date: 2022-10-31 17:52:28
     * @return: com.gientech.iot.global.response.Response<java.lang.Void>
     * @version: 1.0
     */
    @ApiOperation("删除权限信息")
    @DeleteMapping
    public Response<Void> delete(
            @ApiParam("权限id列表，必填项")
            @NotEmpty(message = "权限id列表，permissionIds不能为null")
            @RequestParam("permissionIds") List<Long> permissionIds) {
        menuService.delete(permissionIds);
        return Response.success();
    }

    /**
     * @description: 获取权限信息列表
     * @author: 王强
     * @date: 2022-10-31 13:42:06
     * @return: com.gientech.iot.global.response.Response<java.util.List <
            * com.gientech.iot.user.api.pojo.vo.PermissionSearchResultVO>>
     * @version: 1.0
     */
    @ApiOperation("获取权限信息列表")
    @PostMapping("/list")
    public Response<List<MenuSearchResultVO>> list(@Validated @RequestBody PermissionSearchDTO dto) {
        return Response.success(menuService.list(dto));
    }

    /**
     * @param roleId 角色id
     * @description: 根据角色id获取角色其下权限树下拉
     * @author: 王强
     * @date: 2023-05-23 10:23:04
     * @return: Response<TreeSelectVO < PermissionTreeVO>>
     * @version: 1.0
     */
    @ApiOperation("获取角色其下权限树下拉")
    @GetMapping("/treeSelectByRoleId")
    public Response<TreeSelectVO> treeSelectByRoleId(
            @ApiParam("角色id，必填项")
            @NotNull(message = "角色id，roleId不能为null")
            @RequestParam("roleId") Long roleId) {
        return Response.success(menuService.getTreeSelectByRoleId(roleId));
    }

    /**
     * @param roleId        角色id
     * @param permissionIds 权限id列表
     * @description: 分配角色权限
     * @author: 白剑民
     * @date: 2022-10-31 17:01:22
     * @return: com.gientech.iot.global.response.Response<java.lang.Void>
     * @version: 1.0
     */
    @ApiOperation("分配角色权限")
    @PutMapping("/assignPermission")
    public Response<Void> assignRole(
            @ApiParam("角色id，必填项")
            @NotNull(message = "角色id，roleId不能为null")
            @RequestParam("roleId") Long roleId,
            @ApiParam("权限id列表，必填项")
            @NotEmpty(message = "权限id列表，permissionIds不能为null")
            @RequestBody List<Long> permissionIds) {
        menuService.assignPermission(roleId, permissionIds);
        return Response.success();
    }

    /**
     * @description: 启用或禁用权限
     * @author: 白剑民
     * @date: 2022-10-31 17:09:38
     * @return: com.gientech.iot.global.response.Response<java.lang.Void>
     * @version: 1.0
     */
    @ApiOperation("启用或禁用权限")
    @PutMapping("/changeStatus")
    public Response<Void> changePermissionStatus(
            @ApiParam("权限id，必填项")
            @NotNull(message = "权限id，permissionId不能为null")
            @RequestParam("menuId") Long menuId,
            @ApiParam("启用(true)或禁用(false)，必填项")
            @NotNull(message = "启用或禁用，status不能为null")
            @RequestParam("status") Boolean status) {
        menuService.changePermissionStatus(menuId, status);
        return Response.success();
    }

}
