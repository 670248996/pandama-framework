package com.pandama.top.user.api.service;

import com.pandama.top.user.api.pojo.vo.UserLoginVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pandama-user", path = "/pandama/user")
public interface UserFeignService {

    /**
     * @param username 用户名
     * @description: 根据用户名查询用户信息
     * @author: 王强
     * @date: 2022-10-24 13:35:28
     * @return: LoginVO
     * @version: 1.0
     */
    @GetMapping(value = "/login/loginByUsername")
    UserLoginVO findUserByUsername(@RequestParam("username") String username);

    /**
     * @param phone 手机号
     * @description: 根据手机号查询用户信息
     * @author: 王强
     * @date: 2022-10-24 13:35:28
     * @return: LoginVO
     * @version: 1.0
     */
    @GetMapping(value = "/login/loginByPhone")
    UserLoginVO findUserByPhone(@RequestParam("phone") String phone);

    /**
     * @param authCode  验证码code
     * @param uuid      验证码uuid
     * @description: 校验验证码
     * @author: 王强
     * @date: 2023-04-21 17:50:20
     * @return: boolean
     * @version: 1.0
     */
    @GetMapping(value = "/authCode/verify")
    Boolean verify(@RequestParam("authCode") String authCode, @RequestParam("uuid") String uuid);

    /**
     * @description: 登录成功
     * @author: 王强
     * @date: 2023-05-29 12:16:24
     * @return: void
     * @version: 1.0
     */
    @GetMapping(value = "/login/loginSuccess")
    void loginSuccess();

    /**
     * @description: 注销成功
     * @author: 王强
     * @date: 2023-05-29 12:16:25
     * @return: void
     * @version: 1.0
     */
    @GetMapping(value = "/login/logoutSuccess")
    void logoutSuccess();

}
