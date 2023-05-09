package com.pandama.top.app.controller;

import com.pandama.top.app.pojo.dto.PasswordEditDTO;
import com.pandama.top.app.pojo.dto.UserProfileDTO;
import com.pandama.top.app.pojo.vo.UserInfoVO;
import com.pandama.top.app.pojo.vo.UserLoginVO;
import com.pandama.top.app.pojo.vo.UserProfileVO;
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

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public Response<UserInfoVO> getUserInfo() {
        return Response.success(userService.getInfo());
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public Response<UserProfileVO> profile() {
        return Response.success(userService.getProfile());
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public Response<UserProfileVO> profile(@Validated @RequestBody UserProfileDTO dto) {
        userService.editProfile(dto);
        return Response.success();
    }

    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public Response<UserInfoVO> updatePwd(@Validated @RequestBody PasswordEditDTO dto) {
        userService.editPwd(dto);
        return Response.success();
    }

}
