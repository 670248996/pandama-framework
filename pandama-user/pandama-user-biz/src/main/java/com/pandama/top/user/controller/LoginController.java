package com.pandama.top.user.controller;

import com.pandama.top.core.global.response.Response;
import com.pandama.top.core.utils.UserInfoUtils;
import com.pandama.top.logRecord.annotation.OperationLog;
import com.pandama.top.logRecord.context.LogRecordContext;
import com.pandama.top.logRecord.enums.LogTypeEnum;
import com.pandama.top.user.enums.LoginTypeEnum;
import com.pandama.top.user.pojo.vo.SystemUserInfoVO;
import com.pandama.top.user.api.pojo.vo.UserLoginVO;
import com.pandama.top.user.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @description: 用户信息控制层
 * @author: 白剑民
 * @dateTime: 2022/10/17 17:32
 */
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Api(tags = "用户信息相关接口")
@Validated
@Slf4j
public class LoginController {

    private final LoginService loginService;

    /**
     * @param username 账号
     * @description: 根据账号获取登录信息
     * @author: 王强
     * @date: 2023-06-16 15:48:32
     * @return: UserLoginVO
     * @version: 1.0
     */
    @ApiOperation("根据账号获取登录信息")
    @GetMapping("/loginByUsername")
    public UserLoginVO loginByUsername(@ApiParam("账号，必填项") @NotBlank(message = "账号，username不能为null") @RequestParam("username") String username) {
        return loginService.loginByUsername(username);
    }

    /**
     * @param phone 手机号
     * @description: 根据手机号获取登录信息
     * @author: 王强
     * @date: 2023-06-16 15:48:28
     * @return: UserLoginVO
     * @version: 1.0
     */
    @ApiOperation("根据手机号获取登录信息")
    @GetMapping("/loginByPhone")
    public UserLoginVO loginByPhone(@ApiParam("手机号，必填项") @NotBlank(message = "手机号，phone不能为null") @RequestParam("phone") String phone) {
        return loginService.loginByPhone(phone);
    }

    /**
     * @description: 根据token获取用户信息
     * @author: 白剑民
     * @date: 2022-10-28 16:22:37
     * @return: com.gientech.iot.global.response.Response<com.gientech.iot.user.api.entity.vo.UserInfoVO>
     * @version: 1.0
     */
    @ApiOperation("根据token获取用户信息")
    @GetMapping("/getUserInfo")
    public Response<SystemUserInfoVO> getUserInfo() {
        return Response.success(loginService.getUserInfo());
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
