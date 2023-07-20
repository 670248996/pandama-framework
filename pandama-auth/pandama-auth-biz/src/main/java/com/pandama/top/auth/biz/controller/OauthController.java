package com.pandama.top.auth.biz.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.pandama.top.auth.api.constant.MessageConstant;
import com.pandama.top.auth.api.pojo.AccessToken;
import com.pandama.top.auth.api.constant.RedisConstant;
import com.pandama.top.core.global.response.Response;
import com.pandama.top.logRecord.annotation.OperationLog;
import com.pandama.top.redis.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Oauth2获取令牌接口
 *
 * @author 王强
 * @date 2023-07-08 11:53:38
 */
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OauthController {

    private final TokenEndpoint tokenEndpoint;

    private final RedisUtils redisUtils;

    private final KeyPair keyPair;

    /**
     * Oauth2登录认证
     */
    @OperationLog(bizEvent = "'1'", msg = "'【' + #user.realName + '】登录成功'", tag = "'1'")
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public Response<AccessToken> token(Principal principal, @RequestParam Map<String, String> parameters)
            throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken token = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        assert token != null;
        AccessToken oauth2Token = AccessToken.builder()
                .accessToken(token.getValue())
                .refreshToken(token.getRefreshToken() == null ? "" : token.getRefreshToken().getValue())
                .expiresIn(token.getExpiresIn())
                .tokenType(token.getTokenType())
                .scope(token.getScope()).build();
        String tokenRedisKey = String.format(RedisConstant.ACCESS_TOKEN, token.getAdditionalInformation().get("id"));
        redisUtils.setEx(tokenRedisKey, token.getValue(), token.getExpiresIn(), TimeUnit.SECONDS);
        return Response.success(MessageConstant.LOGIN_SUCCESS, oauth2Token);
    }

    @RequestMapping(value = "/publicKey", method = RequestMethod.GET)
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}
