package com.pandama.top.gateway.feign;

import com.pandama.top.gateway.bean.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 调用feign接口
 * @author: 王强
 * @dateTime: 2022-08-02 16:11:15
 */
@Component
@FeignClient(name = "pandama-app", url = "http://127.0.0.1:9999/pandama/app")
public interface LoginFeignClient {

    /**
     * @param username 用户名
     * @description: 登录用户名
     * @author: 王强
     * @date: 2023-04-22 00:19:12
     * @return: User
     * @version: 1.0
     */
    @GetMapping(value = "/user/loginByUsername")
    User loginByUsername(@RequestParam("username") String username);

    /**
     * @param phoneNumber 电话号码
     * @description: 通过电话号码登录
     * @author: 王强
     * @date: 2023-04-22 00:19:14
     * @return: User
     * @version: 1.0
     */
    @GetMapping(value = "/user/loginByPhoneNumber")
    User loginByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

}
