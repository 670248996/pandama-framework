package com.pandama.top.user.biz.controller;

import com.pandama.top.user.api.pojo.dto.*;
import com.pandama.top.user.api.pojo.vo.*;
import com.pandama.top.user.biz.service.UserService;
import com.pandama.top.global.response.Response;
import com.pandama.top.pojo.vo.PageResultVO;
import com.pandama.top.utils.BeanConvertUtils;
import com.pandama.top.utils.UserInfoUtils;
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
import javax.validation.constraints.NotBlank;
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
     * @param username 账号
     * @description: 根据账号获取登录信息
     * @author: 白剑民
     * @date: 2023-04-06 14:05:12
     * @return: com.gientech.iot.global.response.Response<com.gientech.iot.pojo.vo.UserLoginVO>
     * @version: 1.0
     */
    @ApiOperation("根据账号获取登录信息")
    @GetMapping("/loginByUsername")
    public UserLoginVO getUserLoginInfoByUsername(
            @ApiParam("账号，必填项")
            @NotBlank(message = "账号，username不能为null")
            @RequestParam("username") String username) {
        return userService.getUserLoginInfoByUsername(username);
    }

    /**
     * @param phone 手机号
     * @description: 根据手机号获取登录信息
     * @author: 白剑民
     * @date: 2023-04-06 14:05:45
     * @return: com.gientech.iot.global.response.Response<com.gientech.iot.pojo.vo.UserLoginVO>
     * @version: 1.0
     */
    @ApiOperation("根据手机号获取登录信息")
    @GetMapping("/loginByPhone")
    public UserLoginVO getUserLoginInfoByPhone(
            @ApiParam("手机号，必填项")
            @NotBlank(message = "手机号，phone不能为null")
            @RequestParam("phone") String phone) {
        return userService.getUserLoginInfoByPhone(phone);
    }

    /**
     * @description: 根据token获取用户信息
     * @author: 白剑民
     * @date: 2022-10-28 16:22:37
     * @return: com.gientech.iot.global.response.Response<com.gientech.iot.user.api.entity.vo.UserInfoVO>
     * @version: 1.0
     */
    @ApiOperation("根据token获取用户信息，注：此接口应在登录后先行调用")
    @GetMapping("/getByToken")
    public Response<SystemUserInfoVO> getInfoByToken() {
        return Response.success(userService.getUserInfoByToken());
    }

    /**
     * @description: 登录成功
     * @author: 王强
     * @date: 2023-05-29 12:05:50
     * @return: void
     * @version: 1.0
     */
    @ApiOperation(value = "登录成功", hidden = true)
    @GetMapping("/loginSuccess")
    public void loginSuccess() {
    }

    /**
     * @description: 注销成功
     * @author: 王强
     * @date: 2023-05-29 12:05:52
     * @return: void
     * @version: 1.0
     */
    @ApiOperation(value = "注销成功", hidden = true)
    @GetMapping("/logoutSuccess")
    public void logoutSuccess() {
    }

    /**
     * @param dto 用户注册传参
     * @description: 用户注册
     * @author: 白剑民
     * @date: 2022-10-28 16:22:37
     * @return: com.gientech.iot.global.response.Response<com.gientech.iot.user.entity.vo.UserRegisterResultVO>
     * @version: 1.0
     */
    @ApiOperation("用户注册")
    @PostMapping
    public Response<UserRegisterResultVO> register(@Valid @RequestBody UserRegisterDTO dto) {
        return Response.success(userService.register(dto));
    }

    /**
     * @param dtoList 批量用户注册传参
     * @description: 批量用户注册
     * @author: 白剑民
     * @date: 2022-10-31 10:55:44
     * @return: com.gientech.iot.global.response.Response<
            * java.util.List < com.gientech.iot.user.entity.vo.UserRegisterResultVO>>
     * @version: 1.0
     */
    @ApiOperation("批量用户注册")
    @PostMapping("/batchRegister")
    public Response<List<UserRegisterResultVO>> batchRegister(@Valid @RequestBody List<UserRegisterDTO> dtoList) {
        return Response.success(userService.batchRegister(dtoList));
    }

    /**
     * @param userId 用户id
     * @description: 根据id获取用户信息
     * @author: 白剑民
     * @date: 2022-10-26 17:14:21
     * @return: com.gientech.iot.global.response.Response<com.gientech.iot.user.entity.vo.UserInfoVO>
     * @version: 1.0
     */
    @ApiOperation("根据id获取用户信息")
    @GetMapping
    public Response<UserDetailResultVO> detail(
            @ApiParam("用户id，必填项")
            @NotNull(message = "用户id，userId不能为null")
            @RequestParam("userId") Long userId) {
        return Response.success(userService.getUserInfoById(userId));
    }

    /**
     * @param dto 用户信息更新传参
     * @description: 用户信息更新
     * @author: 白剑民
     * @date: 2022-10-27 09:41:48
     * @return: com.gientech.iot.global.response.Response<java.lang.Void>
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
     * @description: 删除用户信息
     * @author: 白剑民
     * @date: 2022-10-28 15:21:33
     * @return: com.gientech.iot.global.response.Response<java.lang.Void>
     * @version: 1.0
     */
    @ApiOperation("删除用户信息")
    @DeleteMapping
    public Response<Void> delete(
            @ApiParam("用户id列表，必填项")
            @NotEmpty(message = "用户id列表，userIds不能为null")
            @RequestParam List<Long> userIds) {
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
    public Response<PageResultVO<UserSearchResultVO>> page(@Validated @RequestBody UserSearchDTO dto) {
        return Response.success(userService.page(dto));
    }

    /**
     * @param enterpriseId 企业/机构id
     * @description: 获取企业/机构其下所有用户列表
     * @author: 白剑民
     * @date: 2022-10-24 17:01:50
     * @return: com.gientech.iot.global.response.Response<
            * java.util.List < com.gientech.iot.user.entity.vo.UserInfoVO>>
     * @version: 1.0
     */
    @ApiOperation("获取企业/机构其下所有用户列表")
    @GetMapping("/listByEnterpriseId")
    public Response<List<UserDetailResultVO>> getUserListByEnterpriseId(
            @ApiParam("企业/机构id，必填项")
            @NotNull(message = "企业/机构id，enterpriseId不能为null")
            @RequestParam("enterpriseId") Long enterpriseId) {
        return Response.success(userService.getUserListByEnterpriseId(enterpriseId));
    }

    /**
     * @param deptId 部门id
     * @description: 获取指定部门下所有用户列表
     * @author: 白剑民
     * @date: 2022-10-24 17:03:22
     * @return: com.gientech.iot.global.response.Response<
            * java.util.List < com.gientech.iot.user.entity.vo.UserInfoVO>>
     * @version: 1.0
     */
    @ApiOperation("获取指定部门下所有用户列表")
    @GetMapping("/listByDepartmentId")
    public Response<List<UserDetailResultVO>> listByDepartmentId(
            @ApiParam("部门id，必填项")
            @NotNull(message = "部门id，departmentId不能为null")
            @RequestParam("deptId") Long deptId) {
        return Response.success(userService.getUserListByDepartmentId(deptId));
    }

    /**
     * @param userIds 用户id列表
     * @description:
     * @author: 白剑民
     * @date: 2022-10-28 15:59:25
     * @return: com.gientech.iot.global.response.Response<
            * java.util.List < com.gientech.iot.user.entity.vo.UserInfoVO>>
     * @version: 1.0
     */
    @ApiOperation("根据id列表获取用户信息")
    @PostMapping("/getByIds")
    public Response<List<UserDetailResultVO>> getUserInfoByIds(
            @ApiParam("用户id列表，必填项")
            @NotEmpty(message = "用户id列表，userIds不能为null")
            @RequestBody List<Long> userIds) {
        return Response.success(userService.getUserInfoByIds(userIds));
    }

    /**
     * @param idCardNo 身份证号
     * @description: 根据身份证号获取用户信息
     * @author: 白剑民
     * @date: 2022-10-26 17:14:08
     * @return: com.gientech.iot.global.response.Response<com.gientech.iot.user.entity.vo.UserInfoVO>
     * @version: 1.0
     */
    @ApiOperation("根据身份证号获取用户信息")
    @GetMapping("/getByIdCardNo")
    public Response<UserDetailResultVO> getUserInfoByIdCardNo(
            @ApiParam("用户身份证号，必填项")
            @NotBlank(message = "用户身份证号，idCardNo不能为null")
            @RequestParam("idCardNo") String idCardNo) {
        return Response.success(userService.getUserInfoByIdCardNo(idCardNo));
    }

    /**
     * @param idCardNos 身份证证号列表
     * @description: 根据身份证号列表获取用户信息
     * @author: 白剑民
     * @date: 2022-10-28 16:15:51
     * @return: com.gientech.iot.global.response.Response<
            * java.util.List < com.gientech.iot.user.entity.vo.UserInfoVO>>
     * @version: 1.0
     */
    @ApiOperation("根据身份证号列表获取用户信息")
    @PostMapping("/getByIdCardNos")
    public Response<List<UserDetailResultVO>> getUserInfoByIdCardNos(
            @ApiParam("用户身份证号列表，必填项")
            @NotEmpty(message = "用户身份证号列表，idCardNos不能为null")
            @RequestBody List<String> idCardNos) {
        return Response.success(userService.getUserInfoByIdCardNos(idCardNos));
    }

    /**
     * @param code 用户编号
     * @description: 根据用户编号获取用户信息
     * @author: 白剑民
     * @date: 2022-10-28 15:36:00
     * @return: com.gientech.iot.global.response.Response<com.gientech.iot.user.entity.vo.UserInfoVO>
     * @version: 1.0
     */
    @ApiOperation("根据用户编号获取用户信息")
    @GetMapping("/getByCode")
    public Response<UserDetailResultVO> getUserInfoByCode(
            @ApiParam("用户编号，必填项")
            @NotBlank(message = "用户编号，code不能为null")
            @RequestParam("code") String code) {
        return Response.success(userService.getUserInfoByCode(code));
    }

    /**
     * @param codes 用户编号列表
     * @description: 根据用户编号列表获取用户信息
     * @author: 白剑民
     * @date: 2022-10-28 16:17:19
     * @return: com.gientech.iot.global.response.Response<
            * java.util.List < com.gientech.iot.user.entity.vo.UserInfoVO>>
     * @version: 1.0
     */
    @ApiOperation("根据用户编号列表获取用户信息")
    @PostMapping("/getByCodes")
    public Response<List<UserDetailResultVO>> getUserInfoByCodes(
            @ApiParam("用户编号列表，必填项")
            @NotEmpty(message = "用户编号列表，codes不能为null")
            @RequestBody List<String> codes) {
        return Response.success(userService.getUserInfoByCodes(codes));
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
    public Response<Void> changeStatus(
            @ApiParam("用户id，必填项")
            @NotNull(message = "用户id，userId不能为null")
            @RequestParam("userId") Long userId,
            @ApiParam("启用(true)或禁用(false)，必填项")
            @NotNull(message = "启用或禁用，status不能为null")
            @RequestParam("status") Boolean status) {
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
    public Response<PageResultVO<UserAuthRoleVO>> authUserPage(@Valid @RequestBody UserAuthRoleSearchDTO dto) {
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
    public Response<PageResultVO<UserAuthRoleVO>> unAuthUserPage(@Valid @RequestBody UserAuthRoleSearchDTO dto) {
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
}
