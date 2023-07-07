package com.pandama.top.auth.biz.component;

import com.pandama.top.auth.api.pojo.SecurityUser;
import com.pandama.top.auth.api.pojo.TokenInfo;
import com.pandama.top.core.utils.MapUtils;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

/**
 * JWT内容增强器
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        TokenInfo info = new TokenInfo();
        // 把用户ID设置到JWT中
        info.setId(securityUser.getId());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(MapUtils.beanToMap(info));
        return accessToken;
    }
}
