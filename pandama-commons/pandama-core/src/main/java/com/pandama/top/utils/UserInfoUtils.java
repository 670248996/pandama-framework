package com.pandama.top.utils;

import com.alibaba.fastjson.JSON;
import com.pandama.top.global.exception.CommonException;
import com.pandama.top.global.response.ResponseCode;
import com.pandama.top.utils.vo.UserLoginVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * @description: 获取当前用户信息
 * @author: 王强
 * @dateTime: 2022-09-15 09:12:51
 */
public class UserInfoUtils {

    /**
     * @description: 获取当前登陆用户信息
     * @author: 王强
     * @date: 2022-09-15 09:11:15
     * @return: @return {@code UserLoginVO }
     * @version: 1.0
     */
    public static UserLoginVO getUserInfo() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String info = request.getHeader("user-info");
        UserLoginVO userLoginVO = new UserLoginVO();
        if (StringUtils.isEmpty(info)) {
            throw new CommonException(ResponseCode.GATEWAY_UN_PASS);
        } else {
            try {
                userLoginVO = JSON.parseObject(URLDecoder.decode(info, "UTF-8"), UserLoginVO.class);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return userLoginVO;
    }
}
