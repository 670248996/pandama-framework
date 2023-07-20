package com.pandama.top.user.service.impl;

import com.pandama.top.user.api.pojo.vo.UserLoginVO;
import com.pandama.top.user.entity.SysRole;
import com.pandama.top.user.mapper.UserMapper;
import com.pandama.top.user.service.LoginService;
import com.pandama.top.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录服务impl
 *
 * @author 王强
 * @date 2023-07-08 15:53:48
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LoginServiceImpl implements LoginService {

    private final UserMapper userMapper;

    private final RoleService roleService;

    @Override
    public UserLoginVO loadUserByUsername(String username) {
        UserLoginVO userLoginVO = userMapper.getLoginInfoByUsernameOrPhone(username, null);
        if (userLoginVO != null) {
            if (userLoginVO.getIsAdmin()) {
                userLoginVO.setRoleCodeList(Collections.singletonList("Admin"));
            } else {
                List<SysRole> roleList = roleService.listByUserId(userLoginVO.getId());
                userLoginVO.setRoleCodeList(roleList.stream().map(SysRole::getRoleCode).collect(Collectors.toList()));
            }
        }
        return userLoginVO;
    }

    @Override
    public UserLoginVO loadUserByPhone(String phone) {
        UserLoginVO userLoginVO = userMapper.getLoginInfoByUsernameOrPhone(null, phone);
        if (userLoginVO != null) {
            if (userLoginVO.getIsAdmin()) {
                userLoginVO.setRoleCodeList(Collections.singletonList("Admin"));
            } else {
                List<SysRole> roleList = roleService.listByUserId(userLoginVO.getId());
                userLoginVO.setRoleCodeList(roleList.stream().map(SysRole::getRoleCode).collect(Collectors.toList()));
            }
        }
        return userLoginVO;
    }
}
