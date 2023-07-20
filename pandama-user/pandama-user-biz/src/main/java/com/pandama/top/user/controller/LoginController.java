package com.pandama.top.user.controller;

import com.pandama.top.user.api.pojo.vo.UserLoginVO;
import com.pandama.top.user.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * 加载用户用户名
     *
     * @param username 账号
     * @return com.pandama.top.user.api.pojo.vo.UserLoginVO
     * @author 王强
     */
    @ApiOperation("根据账号获取登录信息")
    @GetMapping("/loadUserByUsername")
    public UserLoginVO loadUserByUsername(@RequestParam("username") String username) {
        return loginService.loadUserByUsername(username);
    }

    /**
     * 加载用户通过电话
     *
     * @param phone 手机号
     * @return com.pandama.top.user.api.pojo.vo.UserLoginVO
     * @author 王强
     */
    @ApiOperation("根据手机号获取登录信息")
    @GetMapping("/loadUserByPhone")
    public UserLoginVO loadUserByPhone(@RequestParam("phone") String phone) {
        return loginService.loadUserByPhone(phone);
    }

}
