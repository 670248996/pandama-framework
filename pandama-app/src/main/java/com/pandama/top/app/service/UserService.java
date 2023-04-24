package com.pandama.top.app.service;

import com.pandama.top.app.pojo.dto.UserEditDTO;
import com.pandama.top.app.pojo.vo.UserInfoVO;
import com.pandama.top.app.pojo.vo.UserLoginVO;

/**
 * @description: 用户服务
 * @author: 王强
 * @dateTime: 2023-04-20 14:45:24
 */
public interface UserService {

    UserLoginVO loginByUsername(String dto);

    UserLoginVO loginByPhoneNumber(String dto);

    UserInfoVO getInfo();

    void editInfo(UserEditDTO dto);
}
