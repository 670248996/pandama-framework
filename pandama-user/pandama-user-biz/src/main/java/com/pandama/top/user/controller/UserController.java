package com.pandama.top.user.controller;

import com.pandama.top.logRecord.annotation.OperationLog;
import com.pandama.top.logRecord.context.LogRecordContext;
import com.pandama.top.logRecord.enums.LogTypeEnum;
import com.pandama.top.core.enums.LoginTypeEnum;
import com.pandama.top.user.pojo.dto.*;
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
 * @description: 用户信息控制层
 * @author: 白剑民
 * @dateTime: 2022/10/17 17:32
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
     * @param dto 用户注册传参
     * @description: 注册
     * @author: 王强
     * @date: 2023-06-16 15:16:54
     * @return: Response<UserRegisterResultVO>
     * @version: 1.0
     */
    @ApiOperation("用户注册")
    @PostMapping
    public Response<?> register(@Valid @RequestBody UserRegisterDTO dto) {
        userService.register(dto);
        return Response.success();
    }

    /**
     * @param dtoList 批量用户注册传参
     * @description: 批量注册
     * @author: 王强
     * @date: 2023-06-16 15:16:57
     * @return: Response<List < UserRegisterResultVO>>
     * @version: 1.0
     */
    @ApiOperation("批量用户注册")
    @PostMapping("/batchRegister")
    public Response<?> batchRegister(@Valid @RequestBody List<UserRegisterDTO> dtoList) {
        userService.batchRegister(dtoList);
        return Response.success();
    }

    /**
     * @param userId 用户id
     * @description: 详情
     * @author: 王强
     * @date: 2023-06-16 15:17:04
     * @return: Response<UserDetailResultVO>
     * @version: 1.0
     */
    @ApiOperation("根据id获取用户信息")
    @GetMapping
    public Response<UserDetailResultVO> detail(@ApiParam("用户id，必填项") @NotNull(message = "用户id，userId不能为null") @RequestParam("userId") Long userId) {
        return Response.success(userService.getUserInfoById(userId));
    }

    /**
     * @param dto 用户信息更新传参
     * @description: 更新
     * @author: 王强
     * @date: 2023-06-16 15:17:06
     * @return: Response<Void>
     * @version: 1.0
     */
    @ApiOperation("用户信息更新")
    @PutMapping
    public Response<Void> update(@Valid @RequestBody UserUpdateDTO dto) {
        userService.update(dto);
        return Response.success();
    }

    /**
     * @param userIds 用户id列表
     * @description: 删除
     * @author: 王强
     * @date: 2023-06-16 15:17:09
     * @return: Response<Void>
     * @version: 1.0
     */
    @ApiOperation("删除用户信息")
    @DeleteMapping
    public Response<Void> delete(@ApiParam("用户id列表，必填项") @NotEmpty(message = "用户id列表，userIds不能为null") @RequestParam List<Long> userIds) {
        userService.delete(userIds);
        return Response.success();
    }

    /**
     * @param dto 获取用户分页入参
     * @description: 获取用户分页
     * @author: 王强
     * @date: 2023-05-24 10:01:22
     * @return: Response<PageResultVO < UserInfoVO>>
     * @version: 1.0
     */
    @ApiOperation("获取用户分页")
    @PostMapping("/page")
    public Response<PageVO<UserSearchResultVO>> page(@Validated @RequestBody UserSearchDTO dto) {
        return Response.success(userService.page(dto));
    }

    /**
     * @param enterpriseId 企业/机构id
     * @description: 获取企业/机构其下所有用户列表
     * @author: 王强
     * @date: 2023-06-16 15:17:23
     * @return: Response<List < UserDetailResultVO>>
     * @version: 1.0
     */
    @ApiOperation("获取企业/机构其下所有用户列表")
    @GetMapping("/listByEnterpriseId")
    public Response<List<UserDetailResultVO>> getUserListByEnterpriseId(@ApiParam("企业/机构id，必填项") @NotNull(message = "企业/机构id，enterpriseId不能为null") @RequestParam("enterpriseId") Long enterpriseId) {
        return Response.success(userService.listByEnterpriseId(enterpriseId));
    }

    /**
     * @param deptId 部门id
     * @description: 获取指定部门下所有用户列表
     * @author: 王强
     * @date: 2023-06-16 15:17:32
     * @return: Response<List < UserDetailResultVO>>
     * @version: 1.0
     */
    @ApiOperation("获取指定部门下所有用户列表")
    @GetMapping("/listByDeptId")
    public Response<List<UserDetailResultVO>> listByDeptId(@ApiParam("部门id，必填项") @NotNull(message = "部门id，deptId不能为null") @RequestParam("deptId") Long deptId) {
        return Response.success(userService.listByDeptId(deptId));
    }

    /**
     * @param userId 用户id
     * @param status 启用或禁用
     * @description: 启用或禁用用户账号
     * @author: 白剑民
     * @date: 2022-10-27 10:12:28
     * @version: 1.0
     */
    @ApiOperation("启用或禁用用户账号")
    @PutMapping("/changeStatus")
    public Response<Void> changeStatus(@ApiParam("用户id，必填项") @NotNull(message = "用户id，userId不能为null") @RequestParam("userId") Long userId, @ApiParam("用户状态，必填项") @NotNull(message = "用户状态，status不能为null") @RequestParam("status") Boolean status) {
        userService.changeStatus(userId, status);
        return Response.success();
    }

    /**
     * @param dto 用户密码重置传参
     * @description: 用户密码重置
     * @author: 白剑民
     * @date: 2022-10-27 10:34:36
     * @return: com.gientech.iot.global.response.Response<java.lang.Void>
     * @version: 1.0
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
     * @param dto 用户密码修改传参
     * @description: 用户密码修改
     * @author: 白剑民
     * @date: 2022-10-27 10:34:36
     * @return: com.gientech.iot.global.response.Response<java.lang.Void>
     * @version: 1.0
     */
    @ApiOperation("用户密码修改")
    @PutMapping("/updatePassword")
    public Response<Void> updatePassword(@Valid @RequestBody PasswordUpdateDTO dto) {
        dto.setUserId(UserInfoUtils.getUserId());
        userService.updatePassword(dto);
        return Response.success();
    }

    /**
     * @param dto 查询入参
     * @description: 获取用户授权角色信息分页
     * @author: 王强
     * @date: 2023-05-23 12:23:26
     * @return: Response<PageResultVO < RoleAuthUserVO>>
     * @version: 1.0
     */
    @ApiOperation("获取用户授权角色分页")
    @PostMapping("/authRole/authPage")
    public Response<PageVO<UserAuthRoleVO>> authUserPage(@Valid @RequestBody UserAuthRoleSearchDTO dto) {
        return Response.success(userService.authRolePage(dto));
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
    @PostMapping("/authRole/unAuthPage")
    public Response<PageVO<UserAuthRoleVO>> unAuthUserPage(@Valid @RequestBody UserAuthRoleSearchDTO dto) {
        return Response.success(userService.unAuthRolePage(dto));
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
    @PutMapping("/authRole/cancel")
    public Response<?> authUserCancel(@Valid @RequestBody UserAuthRoleCancelDTO dto) {
        userService.authRoleCancel(dto);
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
    @PutMapping("/authRole/confirm")
    public Response<Void> authUserConfirm(@Valid @RequestBody UserAuthRoleConfirmDTO dto) {
        userService.authRoleConfirm(dto);
        return Response.success();
    }

    /**
     * @description: 登录成功
     * @author: 王强
     * @date: 2023-05-29 12:05:50
     * @return: void
     * @version: 1.0
     */
    @OperationLog(bizEvent = "#event", msg = "'【' + #user.realName + '】登录成功", operatorId = "#user.userId", tag = "#tag")
    @ApiOperation(value = "登录成功", hidden = true)
    @GetMapping("/loginSuccess")
    public void loginSuccess() {
        com.pandama.top.core.pojo.vo.UserLoginVO userInfo = UserInfoUtils.getUserInfo();
        LogRecordContext.putVariables("event", LoginTypeEnum.LOGIN.getCode());
        LogRecordContext.putVariables("tag", LogTypeEnum.LOGIN_LOG.getCode());
        LogRecordContext.putVariables("user", userInfo);
    }

    /**
     * @description: 注销成功
     * @author: 王强
     * @date: 2023-05-29 12:05:52
     * @return: void
     * @version: 1.0
     */
    @OperationLog(bizEvent = "#event", msg = "'【' + #user.realName + '】退出成功", operatorId = "#user.userId", tag = "#tag")
    @ApiOperation(value = "注销成功", hidden = true)
    @GetMapping("/logoutSuccess")
    public void logoutSuccess() {
        com.pandama.top.core.pojo.vo.UserLoginVO userInfo = UserInfoUtils.getUserInfo();
        LogRecordContext.putVariables("event", LoginTypeEnum.LOGOUT.getCode());
        LogRecordContext.putVariables("tag", LogTypeEnum.LOGIN_LOG.getCode());
        LogRecordContext.putVariables("user", userInfo);
    }
}
