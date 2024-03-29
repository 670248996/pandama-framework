package com.pandama.top.starter.web.authCode;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 图形验证码配置
 *
 * @author 王强
 * @date 2023-07-08 15:31:47
 */
@Data
@ConfigurationProperties(prefix = "pandama.auth-code")
public class AuthCodeProperties {

    /**
     * 是否开启图形验证码验证
     */
    private Boolean enable = true;

    /**
     * 图形验证码类型，默认数字类型
     */
    private AuthCodeTypeEnum authCodeType = AuthCodeTypeEnum.MATH;

    /**
     * 图形验证码过期时间，默认五分钟
     */
    public Integer timeout = 5;
}
