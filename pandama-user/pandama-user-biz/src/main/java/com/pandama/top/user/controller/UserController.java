package com.pandama.top.user.controller;

import com.pandama.top.user.pojo.dto.*;
import com.pandama.top.user.pojo.vo.SystemUserInfoVO;
import com.pandama.top.user.pojo.vo.UserAuthRoleVO;
import com.pandama.top.user.pojo.vo.UserDetailResultVO;
import com.pandama.top.user.pojo.vo.UserSearchResultVO;
import com.pandama.top.user.service.UserService;
import com.pandama.top.core.global.response.Response;
import com.pandama.top.core.pojo.vo.PageVO;
import com.pandama.top.core.utils.BeanConvertUtils;
import com.pandama.top.core.utils.UserInfoUtils;
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
 * 用户控制器
 *
 * @author 王强
 * @date 2023-07-08 15:45:30
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Api(tags = "用户信息相关接口")
@Validated
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * 注册
     *
     * @param dto 用户注册传参
     * @return com.pandama.top.core.global.response.Response<?>
     * @author 王强
     */
    @ApiOperation("用户注册")
    @PostMapping
    public Response<?> register(@Valid @RequestBody UserRegisterDTO dto) {
        userService.register(dto);
        return Response.success();
    }

    /**
     * 批量注册
     *
     * @param dtoList 批量用户注册传参
     * @return com.pandama.top.core.global.response.Response<?>
     * @author 王强
     */
    @ApiOperation("批量用户注册")
    @PostMapping("/batchRegister")
    public Response<?> batchRegister(@Valid @RequestBody List<UserRegisterDTO> dtoList) {
        userService.batchRegister(dtoList);
        return Response.success();
    }

    /**
     * 详情
     *
     * @param id 用户id
     * @return com.pandama.top.core.global.response.Response<com.pandama.top.user.pojo.vo.UserDetailResultVO>
     * @author 王强
     */
    @ApiOperation("根据id获取用户信息")
    @GetMapping
    public Response<UserDetailResultVO> detail(@ApiParam("用户id，必填项") @NotNull(message = "用户id，id不能为null") @RequestParam("id") Long id) {
        return Response.success(userService.getUserInfoById(id));
    }

    /**
     * 更新
     *
     * @param dto 用户信息更新传参
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("用户信息更新")
    @PutMapping
    public Response<Void> update(@Valid @RequestBody UserUpdateDTO dto) {
        userService.update(dto);
        return Response.success();
    }

    /**
     * 删除
     *
     * @param ids 用户id列表
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("删除用户信息")
    @DeleteMapping
    public Response<Void> delete(@ApiParam("用户id列表，必填项") @NotEmpty(message = "用户id列表，ids不能为null") @RequestParam List<Long> ids) {
        userService.delete(ids);
        return Response.success();
    }

    /**
     * 页面
     *
     * @param dto 获取用户分页入参
     * @return com.pandama.top.core.global.response.Response<com.pandama.top.core.pojo.vo.PageVO < com.pandama.top.user.pojo.vo.UserSearchResultVO>>
     * @author 王强
     */
    @ApiOperation("获取用户分页")
    @PostMapping("/page")
    public Response<PageVO<UserSearchResultVO>> page(@Validated @RequestBody UserSearchDTO dto) {
        return Response.success(userService.page(dto));
    }

    /**
     * 获取指定部门下所有用户列表
     *
     * @param deptId 部门id
     * @return com.pandama.top.core.global.response.Response<java.util.List < com.pandama.top.user.pojo.vo.UserDetailResultVO>>
     * @author 王强
     */
    @ApiOperation("获取指定部门下所有用户列表")
    @GetMapping("/listByDeptId")
    public Response<List<UserDetailResultVO>> listByDeptId(@ApiParam("部门id，必填项") @NotNull(message = "部门id，deptId不能为null") @RequestParam("deptId") Long deptId) {
        return Response.success(userService.listByDeptId(deptId));
    }

    /**
     * 改变状态
     *
     * @param id 用户id
     * @param status 启用或禁用
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("启用或禁用用户账号")
    @PutMapping("/changeStatus")
    public Response<Void> changeStatus(@ApiParam("用户id，必填项") @NotNull(message = "用户id，id不能为null") @RequestParam("id") Long id,
                                       @ApiParam("用户状态，必填项") @NotNull(message = "用户状态，status不能为null") @RequestParam("status") Boolean status) {
        userService.changeStatus(id, status);
        return Response.success();
    }

    /**
     * 重置密码
     *
     * @param dto 用户密码重置传参
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("用户密码重置")
    @PutMapping("/resetPassword")
    public Response<Void> resetPassword(@Valid @RequestBody PasswordResetDTO dto) {
        PasswordUpdateDTO passwordUpdateDTO = BeanConvertUtils.convert(dto, PasswordUpdateDTO::new, (s, t) -> {
            t.setNewPassword(dto.getPassword());
            t.setIsNeedCheck(Boolean.FALSE);
        }).orElse(new PasswordUpdateDTO());
        userService.updatePassword(passwordUpdateDTO);
        return Response.success();
    }

    /**
     * 更新密码
     *
     * @param dto 用户密码修改传参
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("用户密码修改")
    @PutMapping("/updatePassword")
    public Response<Void> updatePassword(@Valid @RequestBody PasswordUpdateDTO dto) {
        dto.setUserId(UserInfoUtils.getUserId());
        userService.updatePassword(dto);
        return Response.success();
    }

    /**
     * 获取用户授权角色信息分页
     *
     * @param dto 查询入参
     * @return com.pandama.top.core.global.response.Response<com.pandama.top.core.pojo.vo.PageVO < com.pandama.top.user.pojo.vo.UserAuthRoleVO>>
     * @author 王强
     */
    @ApiOperation("获取用户授权角色分页")
    @PostMapping("/authRole/authPage")
    public Response<PageVO<UserAuthRoleVO>> authUserPage(@Valid @RequestBody UserAuthRoleSearchDTO dto) {
        return Response.success(userService.authRolePage(dto));
    }

    /**
     * 获取角色未授权用户信息分页
     *
     * @param dto 查询入参
     * @return com.pandama.top.core.global.response.Response<com.pandama.top.core.pojo.vo.PageVO < com.pandama.top.user.pojo.vo.UserAuthRoleVO>>
     * @author 王强
     */
    @ApiOperation("获取角色授权用户分页")
    @PostMapping("/authRole/unAuthPage")
    public Response<PageVO<UserAuthRoleVO>> unAuthUserPage(@Valid @RequestBody UserAuthRoleSearchDTO dto) {
        return Response.success(userService.unAuthRolePage(dto));
    }

    /**
     * 角色取消授权用户
     *
     * @param dto 取消入参
     * @return com.pandama.top.core.global.response.Response<?>
     * @author 王强
     */
    @ApiOperation("角色取消授权用户")
    @PutMapping("/authRole/cancel")
    public Response<?> authUserCancel(@Valid @RequestBody UserAuthRoleCancelDTO dto) {
        userService.authRoleCancel(dto);
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
    @PutMapping("/authRole/confirm")
    public Response<Void> authUserConfirm(@Valid @RequestBody UserAuthRoleConfirmDTO dto) {
        userService.authRoleConfirm(dto);
        return Response.success();
    }

    /**
     * 获取用户信息
     *
     * @return com.pandama.top.core.global.response.Response<com.pandama.top.user.pojo.vo.SystemUserInfoVO>
     * @author 王强
     */
    @ApiOperation("根据token获取用户信息")
    @GetMapping("/getUserInfo")
    public Response<SystemUserInfoVO> getUserInfo() {
        return Response.success(userService.getUserInfo());
    }
}
