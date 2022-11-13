package com.pandama.top.gateway.feign;

import com.pandama.top.gateway.bean.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description: 调用feign接口
 * @author: 王强
 * @dateTime: 2022-08-02 16:11:15
 */
@Component
@FeignClient(name = "pandama-user", path = "/pandama/system")
public interface LoginFeignClient {

    /**
     * @param username 用户名
     * @description: 根据用户名查询用户信息
     * @author: 王强
     * @date: 2022-10-24 13:35:28
     * @return: User
     * @version: 1.0
     */
    @GetMapping(value = "/user/name/{username}")
    User findUserByUsername(@PathVariable("username") String username);

    /**
     * @param phone 手机号
     * @description: 根据手机号查询用户信息
     * @author: 王强
     * @date: 2022-10-24 13:35:28
     * @return: User
     * @version: 1.0
     */
    @GetMapping(value = "/user/phone/{phone}")
    User findUserByPhone(@PathVariable("phone") String phone);

}
