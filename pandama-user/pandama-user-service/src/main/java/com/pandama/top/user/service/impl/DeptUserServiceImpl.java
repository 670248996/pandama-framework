package com.pandama.top.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandama.top.user.entity.SysUserDept;
import com.pandama.top.user.mapper.UserDeptMapper;
import com.pandama.top.user.service.DeptUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门用户服务impl
 *
 * @author 王强
 * @date 2023-07-08 15:53:42
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DeptUserServiceImpl extends ServiceImpl<UserDeptMapper, SysUserDept> implements DeptUserService {

    private final UserDeptMapper departmentUserMapper;

    @Override
    public List<Long> getUserIdsByDeptIds(List<Long> deptIds) {
        if (CollectionUtils.isNotEmpty(deptIds)) {
            return departmentUserMapper.getUserIdsByDeptIds(deptIds);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Long> getDeptIdsByUserIds(List<Long> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            return departmentUserMapper.getDeptIdsByUserIds(userIds);
        }
        return new ArrayList<>();
    }

    @Override
    public List<SysUserDept> getListByDeptIds(List<Long> deptIds) {
        if (CollectionUtils.isNotEmpty(deptIds)) {
            return departmentUserMapper.getListByDeptIds(deptIds);
        }
        return new ArrayList<>();
    }

    @Override
    public List<SysUserDept> getListByUserIds(List<Long> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            return departmentUserMapper.getListByUserIds(userIds);
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteByDeptIds(List<Long> deptIds) {
        if (CollectionUtils.isNotEmpty(deptIds)) {
            departmentUserMapper.deleteByDeptIds(deptIds);
        }
    }

    @Override
    public void deleteByUserIds(List<Long> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            departmentUserMapper.deleteByUserIds(userIds);
        }
    }
}
