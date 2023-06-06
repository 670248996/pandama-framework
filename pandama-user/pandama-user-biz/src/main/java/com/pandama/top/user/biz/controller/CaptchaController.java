package com.pandama.top.user.biz.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.UUID;
import com.pandama.top.user.api.pojo.UserConstants;
import com.pandama.top.user.api.pojo.vo.AuthCodeResultVO;
import com.pandama.top.user.biz.enums.CustomErrorCodeEnum;
import com.google.code.kaptcha.Producer;
import com.pandama.top.enums.AuthCodeTypeEnum;
import com.pandama.top.authcode.KaptchaProperties;
import com.pandama.top.cache.utils.RedisUtils;
import com.pandama.top.global.exception.CommonException;
import com.pandama.top.global.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.validation.constraints.NotBlank;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @description: 验证码操作处理
 * @author: 白剑民
 * @dateTime: 2023/04/21 13:41
 */
@Slf4j
@Api(tags = "图形验证码相关接口")
@RestController
@RequestMapping("/authCode")
public class CaptchaController {

    private final String CAPTCHA_CODE_KEY = "CAPTCHA_CODES:";

    /**
     * 注入字符类型的验证码生成器
     */
    @Resource(name = "captchaProducerText")
    private Producer captchaProducer;

    /**
     * 注入数字类型的验证码生成器
     */
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    private final RedisUtils redisUtils;

    private final KaptchaProperties properties;

    public CaptchaController(RedisUtils redisUtils, KaptchaProperties properties) {
        this.redisUtils = redisUtils;
        this.properties = properties;
    }

    /**
     * @description: 获取图形验证码，返回图片base64字符
     * @author: 王强
     * @date: 2023-06-06 13:09:51
     * @return: Response<AuthCodeResultVO>
     * @version: 1.0
     */
    @ApiOperation("获取图形验证码")
    @GetMapping
    public Response<AuthCodeResultVO> getCode() {
        AuthCodeResultVO result = new AuthCodeResultVO();
        boolean captchaEnabled = properties.getStatus();
        result.setCaptchaEnabled(captchaEnabled);
        if (!captchaEnabled) {
            return Response.success(result);
        }
        // 保存验证码信息
        String uuid = UUID.fastUUID().toString(true);
        String verifyKey = CAPTCHA_CODE_KEY + uuid;

        String capStr, code = null;
        BufferedImage image = null;
        // 生成验证码
        AuthCodeTypeEnum captchaType = properties.getAuthCodeType();
        if (captchaType == AuthCodeTypeEnum.MATH) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if (captchaType == AuthCodeTypeEnum.CHAR) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }
        redisUtils.setEx(verifyKey, code, properties.getTimeout(), TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            assert image != null;
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            throw new CommonException(CustomErrorCodeEnum.AUTH_CODE_ERROR);
        }
        result.setUuid(uuid);
        result.setImg(Base64.encode(os.toByteArray()));
        return Response.success(result);
    }

    /**
     * @param authCode 验证码
     * @param uuid     验证码缓存key
     * @description: 校验图片验证码
     * @author: 白剑民
     * @date: 2023-04-24 10:18:22
     * @return: boolean
     * @version: 1.0
     */
    @ApiOperation("校验图片验证码")
    @GetMapping("/verify")
    public boolean verify(
            @ApiParam("验证码authCode，必填项")
            @NotBlank(message = "验证码，authCode不能为null")
            @RequestParam("authCode") String authCode,
            @ApiParam("验证码uuid，必填项")
            @NotBlank(message = "验证码，uuid不能为null")
            @RequestParam("uuid") String uuid) {
        boolean captchaEnabled = properties.getStatus();
        String verifyKey = CAPTCHA_CODE_KEY + uuid;
        Object code = redisUtils.get(verifyKey).orElse("");
        redisUtils.delete(verifyKey);
        return !captchaEnabled || code.equals(authCode);
    }
}
