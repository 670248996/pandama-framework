package com.pandama.top.user.controller;

import com.pandama.top.core.global.response.Response;
import com.pandama.top.core.pojo.vo.PageVO;
import com.pandama.top.user.pojo.dto.*;
import com.pandama.top.user.pojo.vo.RoleAuthUserVO;
import com.pandama.top.user.pojo.vo.RoleDetailResultVO;
import com.pandama.top.user.pojo.vo.RoleSearchResultVO;
import com.pandama.top.user.entity.SysRole;
import com.pandama.top.user.service.RoleService;
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
 * 角色控制器
 *
 * @author 王强
 * @date 2023-07-08 15:44:42
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
     * 创建
     *
     * @param dto 角色信息创建传参
     * @return com.pandama.top.core.global.response.Response<?>
     * @author 王强
     */
    @ApiOperation("角色信息创建")
    @PostMapping
    public Response<?> create(@Valid @RequestBody RoleCreateDTO dto) {
        roleService.create(dto);
        return Response.success();
    }

    /**
     * 详情
     *
     * @param id 角色id
     * @return com.pandama.top.core.global.response.Response<com.pandama.top.user.pojo.vo.RoleDetailResultVO>
     * @author 王强
     */
    @ApiOperation("角色信息详情")
    @GetMapping
    public Response<RoleDetailResultVO> detail(@ApiParam("角色id，必填项") @NotNull(message = "角色id，id不能为null") @RequestParam("id") Long id) {
        return Response.success(roleService.detail(id));
    }

    /**
     * 更新
     *
     * @param dto 角色信息更新传参
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("角色信息更新")
    @PutMapping
    public Response<Void> update(@Valid @RequestBody RoleUpdateDTO dto) {
        roleService.update(dto);
        return Response.success();
    }

    /**
     * 删除
     *
     * @param ids 角色id
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("角色信息删除")
    @DeleteMapping
    public Response<Void> delete(@ApiParam("角色id列表，必填项") @NotEmpty(message = "角色id列表，ids不能为null") @RequestParam("ids") List<Long> ids) {
        roleService.delete(ids);
        return Response.success();
    }

    /**
     * 列表
     *
     * @param dto 角色信息列表传参
     * @return com.pandama.top.core.global.response.Response<java.util.List < com.pandama.top.user.pojo.vo.RoleSearchResultVO>>
     * @author 王强
     */
    @ApiOperation("角色信息列表")
    @PostMapping("/list")
    public Response<List<RoleSearchResultVO>> list(@Valid @RequestBody RoleSearchDTO dto) {
        return Response.success(roleService.list(dto));
    }

    /**
     * 页面
     *
     * @param dto 角色信息分页传参
     * @return com.pandama.top.core.global.response.Response<com.pandama.top.core.pojo.vo.PageVO < com.pandama.top.user.pojo.vo.RoleSearchResultVO>>
     * @author 王强
     */
    @ApiOperation("角色信息分页")
    @PostMapping("/page")
    public Response<PageVO<RoleSearchResultVO>> page(@Valid @RequestBody RoleSearchDTO dto) {
        return Response.success(roleService.page(dto));
    }

    /**
     * 获取角色列表通过用户id
     *
     * @param userId 用户id
     * @return com.pandama.top.core.global.response.Response<java.util.List < com.pandama.top.user.entity.SysRole>>
     * @author 王强
     */
    @ApiOperation("获取用户的所有角色列表")
    @GetMapping("/listByUserId")
    public Response<List<SysRole>> getRoleListByUserId(@ApiParam("用户id，必填项") @NotNull(message = "用户id，userId不能为null") @RequestParam("userId") Long userId) {
        return Response.success(roleService.listByUserId(userId));
    }

    /**
     * 获取角色授权用户信息分页
     *
     * @param dto 查询入参
     * @return com.pandama.top.core.global.response.Response<com.pandama.top.core.pojo.vo.PageVO < com.pandama.top.user.pojo.vo.RoleAuthUserVO>>
     * @author 王强
     */
    @ApiOperation("获取角色授权用户分页")
    @PostMapping("/authUser/authPage")
    public Response<PageVO<RoleAuthUserVO>> authUserPage(@Valid @RequestBody RoleAuthUserSearchDTO dto) {
        return Response.success(roleService.authUserPage(dto));
    }

    /**
     * 获取角色未授权用户信息分页
     *
     * @param dto 查询入参
     * @return com.pandama.top.core.global.response.Response<com.pandama.top.core.pojo.vo.PageVO < com.pandama.top.user.pojo.vo.RoleAuthUserVO>>
     * @author 王强
     */
    @ApiOperation("获取角色授权用户分页")
    @PostMapping("/authUser/unAuthPage")
    public Response<PageVO<RoleAuthUserVO>> unAuthUserPage(@Valid @RequestBody RoleAuthUserSearchDTO dto) {
        return Response.success(roleService.unAuthUserPage(dto));
    }

    /**
     * 角色取消授权用户
     *
     * @param dto 取消入参
     * @return com.pandama.top.core.global.response.Response<?>
     * @author 王强
     */
    @ApiOperation("角色取消授权用户")
    @PutMapping("/authUser/cancel")
    public Response<?> authUserCancel(@Valid @RequestBody RoleAuthUserCancelDTO dto) {
        roleService.authUserCancel(dto);
        return Response.success();
    }

    /**
     * 分配用户角色
     *
     * @param dto 入参
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("角色授权用户")
    @PutMapping("/authUser/confirm")
    public Response<Void> authUserConfirm(@Valid @RequestBody RoleAuthUserConfirmDTO dto) {
        roleService.authUserConfirm(dto);
        return Response.success();
    }

    /**
     * 改变状态
     *
     * @param id 角色id
     * @param status 启用或禁用
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("启用或禁用角色")
    @PutMapping("/changeStatus")
    public Response<Void> changeStatus(@ApiParam("角色id，必填项") @NotNull(message = "角色id，id不能为null") @RequestParam("id") Long id,
                                       @ApiParam("角色状态，必填项") @NotNull(message = "角色状态，status不能为null") @RequestParam("status") Boolean status) {
        roleService.changeStatus(id, status);
        return Response.success();
    }

}
