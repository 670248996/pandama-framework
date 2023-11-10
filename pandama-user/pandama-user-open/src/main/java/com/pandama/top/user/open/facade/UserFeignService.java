package com.pandama.top.user.open.facade;

import com.pandama.top.user.open.pojo.vo.UserLoginVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户Feign服务
 *
 * @author 王强
 * @date 2023-07-08 15:40:04
 */
@FeignClient(name = "pandama-user", path = "/pandama/user")
public interface UserFeignService {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return com.pandama.top.user.api.pojo.vo.UserLoginVO
     * @author 王强
     */
    @GetMapping(value = "/login/loadUserByUsername")
    UserLoginVO loadUserByUsername(@RequestParam("username") String username);

    /**
     * 根据手机号查询用户信息
     *
     * @param phone 手机号
     * @return com.pandama.top.user.api.pojo.vo.UserLoginVO
     * @author 王强
     */
    @GetMapping(value = "/login/loadUserByPhone")
    UserLoginVO loadUserByPhone(@RequestParam("phone") String phone);

    /**
     * 校验验证码
     *
     * @param authCode 验证码code
     * @param uuid     验证码uuid
     * @return java.lang.Boolean
     * @author 王强
     */
    @GetMapping(value = "/authCode/verify")
    Boolean verify(@RequestParam("authCode") String authCode, @RequestParam("uuid") String uuid);

}
