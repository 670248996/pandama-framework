package com.pandama.top.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pandama.top.app.mapper.UserMapper;
import com.pandama.top.app.pojo.dto.PasswordEditDTO;
import com.pandama.top.app.pojo.dto.UserProfileDTO;
import com.pandama.top.app.pojo.entity.User;
import com.pandama.top.app.pojo.vo.UserInfoVO;
import com.pandama.top.app.pojo.vo.UserLoginVO;
import com.pandama.top.app.pojo.vo.UserProfileVO;
import com.pandama.top.app.service.UserService;
import com.pandama.top.global.exception.CommonException;
import com.pandama.top.utils.BeanConvertUtils;
import com.pandama.top.utils.UserInfoUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 设备服务impl
 * @author: 王强
 * @dateTime: 2023-04-19 16:44:34
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public UserLoginVO loginByUsername(String username) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        return BeanConvertUtils.convert(user, UserLoginVO::new).orElse(null);
    }

    @Override
    public UserLoginVO loginByPhoneNumber(String phoneNumber) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhoneNumber, phoneNumber));
        return BeanConvertUtils.convert(user, UserLoginVO::new).orElse(null);
    }

    @Override
    public UserInfoVO getInfo() {
        User user = userMapper.selectById(UserInfoUtils.getUserId());
        return BeanConvertUtils.convert(user, UserInfoVO::new).orElse(new UserInfoVO());
    }


    @Override
    public UserProfileVO getProfile() {
        User user = userMapper.selectById(UserInfoUtils.getUserId());
        return BeanConvertUtils.convert(user, UserProfileVO::new).orElse(new UserProfileVO());
    }

    @Override
    public void editProfile(UserProfileDTO dto) {
        User user = BeanConvertUtils.convert(dto, User::new).orElse(new User());
        userMapper.updateById(user);
    }

    @Override
    public void editPwd(PasswordEditDTO dto) {
        User user = userMapper.selectById(UserInfoUtils.getUserId());
        if (!StringUtils.equals(user.getPassword(), dto.getOldPassword())) {
            throw new CommonException("原密码错误");
        }
        user.setPassword(dto.getNewPassword());
        userMapper.updateById(user);
    }
}
