package com.pandama.top.user.biz.controller;

import com.pandama.top.global.response.Response;
import com.pandama.top.pojo.vo.PageResultVO;
import com.pandama.top.user.api.pojo.dto.*;
import com.pandama.top.user.api.pojo.vo.RoleAuthUserVO;
import com.pandama.top.user.api.pojo.vo.RoleCreateResultVO;
import com.pandama.top.user.api.pojo.vo.RoleDetailResultVO;
import com.pandama.top.user.api.pojo.vo.RoleSearchResultVO;
import com.pandama.top.user.biz.entity.SysRole;
import com.pandama.top.user.biz.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 角色信息控制层
 * @author: 白剑民
 * @dateTime: 2022/10/31 11:14
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Api(tags = "角色信息相关接口")
@Validated
@Slf4j
public class RoleController {

    private final RoleService roleService;

    /**
     * @param dto 角色信息创建传参
     * @description: 角色信息创建
     * @author: 白剑民
     * @date: 2022-10-31 16:28:11
     * @return: com.gientech.iot.global.response.Response<com.gientech.iot.user.entity.vo.RoleCreateResultVO>
     * @version: 1.0
     */
    @ApiOperation("角色信息创建")
    @PostMapping
    public Response<RoleCreateResultVO> create(@Valid @RequestBody RoleCreateDTO dto) {
        return Response.success(roleService.create(dto));
    }

    /**
     * @param roleId 角色id
     * @description: 角色信息详情
     * @author: 王强
     * @date: 2023-05-22 17:36:37
     * @return: Response<RoleDetailResultVO>
     * @version: 1.0
     */
    @ApiOperation("角色信息详情")
    @GetMapping
    public Response<RoleDetailResultVO> detail(
            @ApiParam("角色id，必填项")
            @NotNull(message = "角色id，roleId不能为null")
            @RequestParam("roleId") Long roleId) {
        return Response.success(roleService.detail(roleId));
    }

    /**
     * @param dto 角色信息更新传参
     * @description: 角色信息更新
     * @author: 白剑民
     * @date: 2022-10-31 15:17:42
     * @return: com.gientech.iot.global.response.Response<java.util.List < com.gientech.iot.user.entity.Role>>
     * @version: 1.0
     */
    @ApiOperation("角色信息更新")
    @PutMapping
    public Response<Void> update(@Valid @RequestBody RoleUpdateDTO dto) {
        roleService.update(dto);
        return Response.success();
    }

    /**
     * @param roleIds 角色id
     * @description: 角色信息删除
     * @author: 白剑民
     * @date: 2022-10-31 16:22:08
     * @return: com.gientech.iot.global.response.Response<java.lang.Void>
     * @version: 1.0
     */
    @ApiOperation("角色信息删除")
    @DeleteMapping
    public Response<Void> delete(
            @ApiParam("角色id列表，必填项")
            @NotEmpty(message = "角色id列表，roleIds不能为null")
            @RequestParam("roleIds") List<Long> roleIds) {
        roleService.delete(roleIds);
        return Response.success();
    }

    /**
     * @param dto 角色信息列表传参
     * @description: 角色信息列表
     * @author: 王强
     * @date: 2023-05-22 17:36:37
     * @return: Response<List < RoleSearchResultVO>>
     * @version: 1.0
     */
    @ApiOperation("角色信息列表")
    @PostMapping("/list")
    public Response<List<RoleSearchResultVO>> list(@Valid @RequestBody RoleSearchDTO dto) {
        return Response.success(roleService.list(dto));
    }

    /**
     * @param dto 角色信息分页传参
     * @description: 角色信息分页
     * @author: 王强
     * @date: 2023-05-22 17:36:37
     * @return: Response<PageResultVO < RoleSearchResultVO>>
     * @version: 1.0
     */
    @ApiOperation("角色信息分页")
    @PostMapping("/page")
    public Response<PageResultVO<RoleSearchResultVO>> page(@Valid @RequestBody RoleSearchDTO dto) {
        return Response.success(roleService.page(dto));
    }

    /**
     * @param userId 用户id
     * @description: 根据用户id获取其下所有角色列表
     * @author: 白剑民
     * @date: 2022-10-31 13:33:33
     * @return: com.gientech.iot.global.response.Response<java.util.List < com.gientech.iot.user.entity.Role>>
     * @version: 1.0
     */
    @ApiOperation("获取用户的所有角色列表")
    @GetMapping("/listByUserId")
    public Response<List<SysRole>> getRoleListByUserId(
            @ApiParam("用户id，必填项")
            @NotNull(message = "用户id，userId不能为null")
            @RequestParam("userId") Long userId) {
        return Response.success(roleService.listByUserId(userId));
    }

    /**
     * @param dto 查询入参
     * @description: 获取角色授权用户信息分页
     * @author: 王强
     * @date: 2023-05-23 12:23:26
     * @return: Response<PageResultVO < RoleAuthUserVO>>
     * @version: 1.0
     */
    @ApiOperation("获取角色授权用户分页")
    @PostMapping("/authUser/authPage")
    public Response<PageResultVO<RoleAuthUserVO>> authUserPage(@Valid @RequestBody RoleAuthUserSearchDTO dto) {
        return Response.success(roleService.authUserPage(dto));
    }

    /**
     * @param dto 查询入参
     * @description: 获取角色未授权用户信息分页
     * @author: 王强
     * @date: 2023-05-23 12:23:26
     * @return: Response<PageResultVO < RoleAuthUserVO>>
     * @version: 1.0
     */
    @ApiOperation("获取角色授权用户分页")
    @PostMapping("/authUser/unAuthPage")
    public Response<PageResultVO<RoleAuthUserVO>> unAuthUserPage(@Valid @RequestBody RoleAuthUserSearchDTO dto) {
        return Response.success(roleService.unAuthUserPage(dto));
    }

    /**
     * @param dto 取消入参
     * @description: 角色取消授权用户
     * @author: 王强
     * @date: 2023-05-23 12:23:26
     * @return: Response<PageResultVO < RoleAuthUserVO>>
     * @version: 1.0
     */
    @ApiOperation("角色取消授权用户")
    @PutMapping("/authUser/cancel")
    public Response<?> authUserCancel(@Valid @RequestBody RoleAuthUserCancelDTO dto) {
        roleService.authUserCancel(dto);
        return Response.success();
    }

    /**
     * @param dto 入参
     * @description: 分配用户角色
     * @author: 王强
     * @date: 2023-05-23 12:47:53
     * @return: Response<Void>
     * @version: 1.0
     */
    @ApiOperation("角色授权用户")
    @PutMapping("/authUser/confirm")
    public Response<Void> authUserConfirm(@Valid @RequestBody RoleAuthUserConfirmDTO dto) {
        roleService.authUserConfirm(dto);
        return Response.success();
    }

    /**
     * @param roleId   角色id
     * @param status 启用或禁用
     * @description: 启用或禁用角色
     * @author: 白剑民
     * @date: 2022-10-31 17:25:36
     * @return: com.gientech.iot.global.response.Response<java.lang.Void>
     * @version: 1.0
     */
    @ApiOperation("启用或禁用角色")
    @PutMapping("/changeStatus")
    public Response<Void> changeStatus(
            @ApiParam("角色id，必填项")
            @NotNull(message = "角色id，roleId不能为null")
            @RequestParam("roleId") Long roleId,
            @ApiParam("启用(true)或禁用(false)，必填项")
            @NotNull(message = "启用或禁用，status不能为null")
            @RequestParam("status") Boolean status) {
        roleService.changeStatus(roleId, status);
        return Response.success();
    }

}
