package com.pandama.top.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pandama.top.app.mapper.UserMapper;
import com.pandama.top.app.pojo.dto.UserEditDTO;
import com.pandama.top.app.pojo.entity.User;
import com.pandama.top.app.pojo.vo.UserInfoVO;
import com.pandama.top.app.pojo.vo.UserLoginVO;
import com.pandama.top.app.service.UserService;
import com.pandama.top.utils.BeanConvertUtils;
import com.pandama.top.utils.UserInfoUtils;
import lombok.RequiredArgsConstructor;
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
    public void editInfo(UserEditDTO dto) {
        User user = BeanConvertUtils.convert(dto, User::new).orElse(new User());
        userMapper.updateById(user);
    }
}
