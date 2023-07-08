package com.pandama.top.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandama.top.user.entity.SysUserRole;
import com.pandama.top.user.mapper.UserRoleMapper;
import com.pandama.top.user.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户角色服务impl
 *
 * @author 王强
 * @date 2023-07-08 15:54:07
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, SysUserRole> implements UserRoleService {

    private final UserRoleMapper userRoleMapper;

    @Override
    public List<Long> getUserIdsByRoleIds(List<Long> roleIds) {
        if (CollectionUtils.isNotEmpty(roleIds)) {
            return userRoleMapper.getUserIdsByRoleIds(roleIds);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Long> getRoleIdsByUserIds(List<Long> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            return userRoleMapper.getRoleIdsByUserIds(userIds);
        }
        return new ArrayList<>();
    }
}
