package com.pandama.top.auth.biz.controller;

import com.pandama.top.auth.api.pojo.Oauth2TokenDTO;
import com.pandama.top.auth.api.constant.RedisConstant;
import com.pandama.top.core.global.response.Response;
import com.pandama.top.core.utils.UserInfoUtils;
import com.pandama.top.redis.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 自定义Oauth2获取令牌接口
 */
@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * Oauth2登录认证
     */
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public Response<Oauth2TokenDTO> login(Principal principal, @RequestParam Map<String, String> parameters)
            throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        assert oAuth2AccessToken != null;
        Oauth2TokenDTO oauth2TokenDto = Oauth2TokenDTO.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead("Bearer ").build();
        String tokenRedisKey = String.format(RedisConstant.ACCESS_TOKEN, oAuth2AccessToken.getAdditionalInformation().get("id"));
        redisUtils.setEx(tokenRedisKey, oAuth2AccessToken.getValue(), oAuth2AccessToken.getExpiresIn(), TimeUnit.SECONDS);
        return Response.success(oauth2TokenDto);
    }

    /**
     * 退出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Response<?> logout() {
        String tokenRedisKey = String.format(RedisConstant.ACCESS_TOKEN, UserInfoUtils.getUserId());
        redisUtils.delete(tokenRedisKey);
        return Response.success();
    }
}
