package com.pandama.top.app.service;

import com.pandama.top.app.pojo.dto.PasswordEditDTO;
import com.pandama.top.app.pojo.dto.UserProfileDTO;
import com.pandama.top.app.pojo.vo.UserInfoVO;
import com.pandama.top.app.pojo.vo.UserLoginVO;
import com.pandama.top.app.pojo.vo.UserProfileVO;

/**
 * @description: 用户服务
 * @author: 王强
 * @dateTime: 2023-04-20 14:45:24
 */
public interface UserService {

    UserLoginVO loginByUsername(String dto);

    UserLoginVO loginByPhoneNumber(String dto);

    UserInfoVO getInfo();

    UserProfileVO getProfile();

    void editProfile(UserProfileDTO dto);

    void editPwd(PasswordEditDTO dto);
}
