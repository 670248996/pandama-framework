package com.pandama.top.app.controller;

import com.pandama.top.app.pojo.dto.UserEditDTO;
import com.pandama.top.app.pojo.vo.UserInfoVO;
import com.pandama.top.app.pojo.vo.UserLoginVO;
import com.pandama.top.app.service.UserService;
import com.pandama.top.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 用户控制器
 * @author: 王强
 * @dateTime: 2023-04-19 13:09:51
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {

    private final UserService userService;

    @RequestMapping(value = "/loginByUsername", method = RequestMethod.GET)
    public UserLoginVO loginByUsername(@RequestParam("username") String username) {
        return userService.loginByUsername(username);
    }

    @RequestMapping(value = "/loginByPhoneNumber", method = RequestMethod.GET)
    public UserLoginVO loginByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        return userService.loginByPhoneNumber(phoneNumber);
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public Response<UserInfoVO> getUserInfo() {
        return Response.success(new UserInfoVO());
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public Response<UserInfoVO> profile() {
        return Response.success(userService.getInfo());
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public Response<UserInfoVO> profile(@Validated @RequestBody UserEditDTO dto) {
        userService.editInfo(dto);
        return Response.success();
    }

}
