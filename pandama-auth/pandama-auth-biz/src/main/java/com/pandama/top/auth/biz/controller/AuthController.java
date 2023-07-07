package com.pandama.top.auth.biz.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.pandama.top.auth.api.constant.MessageConstant;
import com.pandama.top.auth.api.constant.RedisConstant;
import com.pandama.top.core.global.response.Response;
import com.pandama.top.core.utils.UserInfoUtils;
import com.pandama.top.redis.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * 授权接口
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthController {

    private final KeyPair keyPair;

    private final RedisUtils redisUtils;

    @GetMapping("/rsa/publicKey")
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Response<?> logout() {
        String tokenRedisKey = String.format(RedisConstant.ACCESS_TOKEN, UserInfoUtils.getUserId());
        redisUtils.delete(tokenRedisKey);
        return Response.success(MessageConstant.LOGOUT_SUCCESS);
    }

}
