package com.pandama.top.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandama.top.redis.utils.RedisUtils;
import com.pandama.top.core.global.exception.CommonException;
import com.pandama.top.core.pojo.vo.PageVO;
import com.pandama.top.user.entity.SysDeptUser;
import com.pandama.top.user.entity.SysRole;
import com.pandama.top.user.entity.SysUser;
import com.pandama.top.user.entity.SysUserRole;
import com.pandama.top.user.mapper.UserMapper;
import com.pandama.top.core.utils.BeanConvertUtils;
import com.pandama.top.core.utils.IdCardUtils;
import com.pandama.top.user.pojo.dto.*;
import com.pandama.top.user.pojo.vo.UserAuthRoleVO;
import com.pandama.top.user.pojo.vo.UserDetailResultVO;
import com.pandama.top.user.pojo.vo.UserSearchResultVO;
import com.pandama.top.user.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 用户信息实现类
 * @author: 白剑民
 * @dateTime: 2022/10/17 17:37
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements UserService {

    private final UserMapper userMapper;

    private final DeptUserService departmentUserService;

    private final RoleService roleService;

    private final UserRoleService userRoleService;

    private final MenuService menuService;

    private final PasswordEncoder passwordEncoder;

    private final RedisUtils redisUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserRegisterDTO dto) {
        // 将传参字段转换赋值成用户实体属性
        SysUser user = BeanConvertUtils.convert(dto, SysUser::new, (s, t) -> {
            String idCardNo = s.getIdCardNo();
            t.setPassword(passwordEncoder.encode(s.getPassword()));
            // 如果传入身份证号，则根据身份证号解析生日、年龄、性别
            if (null != idCardNo && idCardNo.length() > 0) {
                t.setBirthday(IdCardUtils.getBirthByIdCard(idCardNo));
                t.setAge(IdCardUtils.getAgeByIdCard(idCardNo));
                t.setGender(IdCardUtils.getGenderByIdCard(idCardNo));
            }
            // TODO 密码策略
            t.setPasswordExpireTime(LocalDateTime.now().plusDays(30));
        }).orElseThrow(() -> new CommonException("用户创建出错"));
        userMapper.insert(user);
        if (dto.getDeptId() != null) {
            // 保存用户新关联的部门信息
            departmentUserService.save(new SysDeptUser(dto.getDeptId(), user.getId()));
        }
    }

    @Override
    public void batchRegister(List<UserRegisterDTO> dtoList) {
        // 执行注册方法
        dtoList.forEach(this::register);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> userIds) {
        userMapper.deleteBatchIds(userIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserUpdateDTO dto) {
        // 将传参字段转换赋值成用户实体属性
        SysUser user = BeanConvertUtils.convert(dto, SysUser::new, (s, t) -> t.setId(dto.getUserId())).orElse(new SysUser());
        userMapper.updateById(user);
        // 删除用户之前关联的部门信息
        departmentUserService.deleteByUserIds(Collections.singletonList(dto.getUserId()));
        if (dto.getDeptId() != null) {
            // 保存用户新关联的部门信息
            departmentUserService.save(new SysDeptUser(dto.getDeptId(), dto.getUserId()));
        }
    }

    @Override
    public PageVO<UserSearchResultVO> page(UserSearchDTO dto) {
        Page<UserSearchResultVO> page = userMapper.page(new Page<>(dto.getCurrent(), dto.getSize()), dto);
        return new PageVO<>(page.getTotal(), page.getSize(), page.getCurrent(), page.getPages(), page.getRecords());
    }

    @Override
    public List<UserDetailResultVO> listByEnterpriseId(Long enterpriseId) {
        return userMapper.getUserListByEnterpriseId(enterpriseId);
    }

    @Override
    public List<UserDetailResultVO> listByDeptId(Long deptId) {
        return userMapper.getUserListByDeptId(deptId);
    }

    @Override
    public UserDetailResultVO getUserInfoById(Long userId) {
        return userMapper.getUserInfoById(userId);
    }

    @Override
    public void changeStatus(Long userId, Boolean status) {
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        sysUser.setStatus(status);
        userMapper.updateById(sysUser);
    }

    @Override
    public void updatePassword(PasswordUpdateDTO dto) {
        SysUser user = userMapper.selectById(dto.getUserId());
        // 原密码
        String oldPassword = user.getPassword();
        // 判断是否需要校验原密码
        if (dto.getIsNeedCheck()) {
            // 校验原密码
            if (!passwordEncoder.matches(dto.getOldPassword(), oldPassword)) {
                throw new CommonException("原密码错误");
            }
        }
        // 赋值新密码
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        // TODO 密码策略
        // 重置密码过期时间(当前过期时间 + 密码有效期 = 新的过期时间)
        LocalDateTime newExpireTime = LocalDateTime.now().plusDays(30);
        user.setPasswordExpireTime(newExpireTime);
        userMapper.updateById(user);
    }

    @Override
    public PageVO<UserAuthRoleVO> authRolePage(UserAuthRoleSearchDTO dto) {
        List<Long> roleIds = userRoleService.getRoleIdsByUserIds(Collections.singletonList(dto.getUserId()));
        Page<SysRole> page = roleService.page(new Page<>(dto.getCurrent(), dto.getSize()),
                new LambdaQueryWrapper<SysRole>().eq(CollectionUtils.isEmpty(roleIds), SysRole::getId, 0)
                        .in(CollectionUtils.isNotEmpty(roleIds), SysRole::getId, roleIds)
                        .like(StringUtils.isNotEmpty(dto.getRoleName()), SysRole::getRoleName, dto.getRoleName()));
        List<UserAuthRoleVO> list = (List<UserAuthRoleVO>) BeanConvertUtils.convertCollection(page.getRecords(), UserAuthRoleVO::new, (s, t) -> {
            t.setRoleId(s.getId());
        }).orElse(new ArrayList<>());
        return new PageVO<>(page.getTotal(), page.getSize(), page.getCurrent(), page.getPages(), list);
    }

    @Override
    public PageVO<UserAuthRoleVO> unAuthRolePage(UserAuthRoleSearchDTO dto) {
        List<Long> roleIds = userRoleService.getRoleIdsByUserIds(Collections.singletonList(dto.getUserId()));
        Page<SysRole> page = roleService.page(new Page<>(dto.getCurrent(), dto.getSize()),
                new LambdaQueryWrapper<SysRole>()
                        .notIn(CollectionUtils.isNotEmpty(roleIds), SysRole::getId, roleIds)
                        .like(StringUtils.isNotEmpty(dto.getRoleName()), SysRole::getRoleName, dto.getRoleName()));
        List<UserAuthRoleVO> list = (List<UserAuthRoleVO>) BeanConvertUtils.convertCollection(page.getRecords(), UserAuthRoleVO::new, (s, t) -> {
            t.setRoleId(s.getId());
        }).orElse(new ArrayList<>());
        return new PageVO<>(page.getTotal(), page.getSize(), page.getCurrent(), page.getPages(), list);
    }

    @Override
    public void authRoleCancel(UserAuthRoleCancelDTO dto) {
        userRoleService.remove(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, dto.getUserId())
                .in(SysUserRole::getRoleId, dto.getRoleIds()));
    }

    @Override
    public void authRoleConfirm(UserAuthRoleConfirmDTO dto) {
        List<Long> roleIds = userRoleService.getRoleIdsByUserIds(Collections.singletonList(dto.getUserId()));
        List<SysUserRole> userRoleList = dto.getRoleIds().stream()
                .filter(p -> !roleIds.contains(p))
                .map(p -> new SysUserRole(dto.getUserId(), p)).collect(Collectors.toList());
        // 创建关联信息
        if (CollectionUtils.isNotEmpty(userRoleList)) {
            userRoleService.saveBatch(userRoleList);
        }
    }
}
