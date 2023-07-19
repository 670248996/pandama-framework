package com.pandama.top.auth.biz.controller;

import com.pandama.top.auth.api.constant.MessageConstant;
import com.pandama.top.auth.api.pojo.Oauth2TokenDTO;
import com.pandama.top.auth.api.constant.RedisConstant;
import com.pandama.top.core.global.response.Response;
import com.pandama.top.logRecord.annotation.OperationLog;
import com.pandama.top.logRecord.context.LogRecordContext;
import com.pandama.top.redis.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
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

    /**
     * Oauth2登录认证
     */
    @OperationLog(bizEvent = "'1'", msg = "'【' + #user.realName + '】登录成功'", tag = "'1'")
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public Response<OAuth2AccessToken> token(Principal principal, @RequestParam Map<String, String> parameters)
            throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
//        assert oAuth2AccessToken != null;
//        Oauth2TokenDTO oauth2Token = Oauth2TokenDTO.builder()
//                .token(oAuth2AccessToken.getValue())
//                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
//                .expiresIn(oAuth2AccessToken.getExpiresIn())
//                .tokenHead("Bearer ").build();
//        String tokenRedisKey = String.format(RedisConstant.ACCESS_TOKEN, oAuth2AccessToken.getAdditionalInformation().get("id"));
//        redisUtils.setEx(tokenRedisKey, oAuth2AccessToken.getValue(), oAuth2AccessToken.getExpiresIn(), TimeUnit.SECONDS);
        return Response.success(MessageConstant.LOGIN_SUCCESS, oAuth2AccessToken);
    }
}
