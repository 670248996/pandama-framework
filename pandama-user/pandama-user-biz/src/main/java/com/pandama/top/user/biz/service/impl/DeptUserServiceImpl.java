package com.pandama.top.user.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandama.top.user.biz.entity.SysDeptUser;
import com.pandama.top.user.biz.mapper.DeptUserMapper;
import com.pandama.top.user.biz.service.DeptUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 部门信息实现类
 * @author: 白剑民
 * @dateTime: 2022/10/21 16:16
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DeptUserServiceImpl extends ServiceImpl<DeptUserMapper, SysDeptUser> implements DeptUserService {

    private final DeptUserMapper departmentUserMapper;

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
    public List<SysDeptUser> getListByDeptIds(List<Long> deptIds) {
        if (CollectionUtils.isNotEmpty(deptIds)) {
            return departmentUserMapper.getListByDeptIds(deptIds);
        }
        return new ArrayList<>();
    }

    @Override
    public List<SysDeptUser> getListByUserIds(List<Long> userIds) {
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
