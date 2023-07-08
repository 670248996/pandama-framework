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
 * 登录控制器
 *
 * @author 王强
 * @date 2023-07-08 15:43:52
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
     * 登录用户名
     *
     * @param username 账号
     * @return com.pandama.top.user.api.pojo.vo.UserLoginVO
     * @author 王强
     */
    @ApiOperation("根据账号获取登录信息")
    @GetMapping("/loginByUsername")
    public UserLoginVO loginByUsername(@ApiParam("账号，必填项") @NotBlank(message = "账号，username不能为null") @RequestParam("username") String username) {
        return loginService.loginByUsername(username);
    }

    /**
     * 登录通过电话
     *
     * @param phone 手机号
     * @return com.pandama.top.user.api.pojo.vo.UserLoginVO
     * @author 王强
     */
    @ApiOperation("根据手机号获取登录信息")
    @GetMapping("/loginByPhone")
    public UserLoginVO loginByPhone(@ApiParam("手机号，必填项") @NotBlank(message = "手机号，phone不能为null") @RequestParam("phone") String phone) {
        return loginService.loginByPhone(phone);
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
        return Response.success(loginService.getUserInfo());
    }

}
