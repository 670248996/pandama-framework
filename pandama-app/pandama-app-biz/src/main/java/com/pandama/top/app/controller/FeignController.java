package com.pandama.top.app.controller;

import com.pandama.top.app.pojo.vo.UserLoginVO;
import com.pandama.top.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: feign接口控制器
 * @author: 王强
 * @dateTime: 2023-04-19 13:09:51
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/feign")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FeignController {

    private final UserService userService;

    @RequestMapping(value = "/loginByUsername", method = RequestMethod.GET)
    public UserLoginVO loginByUsername(@RequestParam("username") String username) {
        return userService.loginByUsername(username);
    }

    @RequestMapping(value = "/loginByPhoneNumber", method = RequestMethod.GET)
    public UserLoginVO loginByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        return userService.loginByPhoneNumber(phoneNumber);
    }

}
