package com.pandama.top.user.controller;

import com.pandama.top.core.global.response.Response;
import com.pandama.top.user.api.pojo.vo.UserLoginVO;
import com.pandama.top.user.pojo.vo.SystemUserInfoVO;
import com.pandama.top.user.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
