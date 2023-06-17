package com.pandama.top.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandama.top.core.global.exception.CommonException;
import com.pandama.top.core.pojo.vo.PageVO;
import com.pandama.top.core.utils.BeanConvertUtils;
import com.pandama.top.user.pojo.dto.*;
import com.pandama.top.user.pojo.vo.RoleAuthUserVO;
import com.pandama.top.user.pojo.vo.RoleDetailResultVO;
import com.pandama.top.user.pojo.vo.RoleSearchResultVO;
import com.pandama.top.user.enums.CustomErrorCodeEnum;
import com.pandama.top.user.mapper.RoleMapper;
import com.pandama.top.user.entity.SysRole;
import com.pandama.top.user.entity.SysRoleMenu;
import com.pandama.top.user.entity.SysUser;
import com.pandama.top.user.entity.SysUserRole;
import com.pandama.top.user.service.RoleMenuService;
import com.pandama.top.user.service.RoleService;
import com.pandama.top.user.service.UserRoleService;
import com.pandama.top.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 角色信息实现类
 * @author: 白剑民
 * @dateTime: 2022/10/31 11:04
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class RoleServiceImpl extends ServiceImpl<RoleMapper, SysRole> implements RoleService {

    private final RoleMapper roleMapper;

    private final UserRoleService userRoleService;

    private final RoleMenuService roleMenuService;

    private final UserService userService;

    @Override
    public void create(RoleCreateDTO dto) {
        // 将传参字段转换赋值成角色实体属性
        SysRole sysRole = BeanConvertUtils.convert(dto, SysRole::new)
                .orElseThrow(() -> new CommonException(CustomErrorCodeEnum.ROLE_CREATE_ERROR));
        roleMapper.insert(sysRole);
        // 添加角色关联菜单列表
        List<SysRoleMenu> rolePermList = dto.getMenuIds().stream()
                .map(p -> new SysRoleMenu(sysRole.getId(), p)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(rolePermList)) {
            roleMenuService.saveBatch(rolePermList);
        }
    }

    @Override
    public RoleDetailResultVO detail(Long id) {
        SysRole sysRole = roleMapper.selectById(id);
        return BeanConvertUtils.convert(sysRole, RoleDetailResultVO::new, (s, t) -> {
            t.setId(s.getId());
        }).orElse(new RoleDetailResultVO());
    }

    @Override
    public void update(RoleUpdateDTO dto) {
        // 将传参字段转换赋值成角色实体属性
        SysRole role = BeanConvertUtils.convert(dto, SysRole::new, (s, t) -> t.setId(s.getRoleId())).orElse(new SysRole());
        // 删除角色关联菜单列表
        roleMenuService.deleteByRoleIds(Collections.singletonList(dto.getRoleId()));
        // 添加角色关联菜单列表
        List<SysRoleMenu> rolePermList = dto.getMenuIds().stream()
                .map(p -> new SysRoleMenu(dto.getRoleId(), p)).collect(Collectors.toList());
        roleMenuService.saveBatch(rolePermList);
        // 更新角色信息
        roleMapper.updateById(role);
    }

    @Override
    public void delete(List<Long> roleIds) {
        // 获取指定角色列表下的关联用户列表
        List<Long> users = userRoleService.getUserIdsByRoleIds(roleIds);
        // 判断角色有无关联用户
        if (users.size() > 0) {
            throw new CommonException(CustomErrorCodeEnum.ROLE_HAS_USER);
        }
        // 删除角色关联菜单列表
        roleMenuService.deleteByRoleIds(roleIds);
        // 角色逻辑删除
        roleMapper.deleteBatchIds(roleIds);
    }

    @Override
    public List<RoleSearchResultVO> list(RoleSearchDTO dto) {
        List<SysRole> roleList = roleMapper.selectList(new LambdaQueryWrapper<SysRole>()
                .like(StringUtils.isNotEmpty(dto.getRoleName()), SysRole::getRoleName, dto.getRoleName())
                .like(StringUtils.isNotEmpty(dto.getRoleCode()), SysRole::getRoleCode, dto.getRoleCode())
                .eq(dto.getStatus() != null, SysRole::getStatus, dto.getStatus()));
        return (List<RoleSearchResultVO>) BeanConvertUtils.convertCollection(roleList, RoleSearchResultVO::new, (s, t) -> {
            t.setId(s.getId());
        }).orElse(new ArrayList<>());
    }

    @Override
    public PageVO<RoleSearchResultVO> page(RoleSearchDTO dto) {
        IPage<SysRole> page = roleMapper.selectPage(new Page<>(dto.getCurrent(), dto.getSize()),
                new LambdaQueryWrapper<SysRole>()
                        .like(StringUtils.isNotEmpty(dto.getRoleName()), SysRole::getRoleName, dto.getRoleName())
                        .like(StringUtils.isNotEmpty(dto.getRoleCode()), SysRole::getRoleCode, dto.getRoleCode())
                        .eq(dto.getStatus() != null, SysRole::getStatus, dto.getStatus())
                        .orderByDesc(SysRole::getCreateTime));
        List<RoleSearchResultVO> list = (List<RoleSearchResultVO>) BeanConvertUtils.convertCollection(page.getRecords(),
                RoleSearchResultVO::new, (s, t) -> {
                    t.setId(s.getId());
                }).orElse(new ArrayList<>());
        return new PageVO<>(page.getTotal(), page.getSize(), page.getCurrent(), page.getPages(), list);
    }

    @Override
    public List<SysRole> listByUserId(Long userId) {
        return roleMapper.getRoleListByUserId(userId);
    }

    @Override
    public PageVO<RoleAuthUserVO> authUserPage(RoleAuthUserSearchDTO dto) {
        List<Long> userIds = userRoleService.getUserIdsByRoleIds(Collections.singletonList(dto.getRoleId()));
        Page<SysUser> page = userService.page(new Page<>(dto.getCurrent(), dto.getSize()),
                new LambdaQueryWrapper<SysUser>().eq(CollectionUtils.isEmpty(userIds), SysUser::getId, 0)
                        .in(CollectionUtils.isNotEmpty(userIds), SysUser::getId, userIds)
                        .like(StringUtils.isNotEmpty(dto.getUsername()), SysUser::getUsername, dto.getUsername())
                        .like(StringUtils.isNotEmpty(dto.getPhone()), SysUser::getPhone, dto.getPhone()));
        List<RoleAuthUserVO> list = (List<RoleAuthUserVO>) BeanConvertUtils.convertCollection(page.getRecords(),
                RoleAuthUserVO::new, (s, t) -> t.setUserId(s.getId())).orElse(new ArrayList<>());
        return new PageVO<>(page.getTotal(), page.getSize(), page.getCurrent(), page.getPages(), list);
    }

    @Override
    public PageVO<RoleAuthUserVO> unAuthUserPage(RoleAuthUserSearchDTO dto) {
        List<Long> userIds = userRoleService.getUserIdsByRoleIds(Collections.singletonList(dto.getRoleId()));
        Page<SysUser> page = userService.page(new Page<>(dto.getCurrent(), dto.getSize()),
                new LambdaQueryWrapper<SysUser>().notIn(CollectionUtils.isNotEmpty(userIds), SysUser::getId, userIds)
                        .like(StringUtils.isNotEmpty(dto.getUsername()), SysUser::getUsername, dto.getUsername())
                        .like(StringUtils.isNotEmpty(dto.getPhone()), SysUser::getPhone, dto.getPhone()));
        List<RoleAuthUserVO> list = (List<RoleAuthUserVO>) BeanConvertUtils.convertCollection(page.getRecords(),
                RoleAuthUserVO::new, (s, t) -> t.setUserId(s.getId())).orElse(new ArrayList<>());
        return new PageVO<>(page.getTotal(), page.getSize(), page.getCurrent(), page.getPages(), list);
    }

    @Override
    public void authUserCancel(RoleAuthUserCancelDTO dto) {
        userRoleService.remove(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getRoleId, dto.getRoleId())
                .in(SysUserRole::getUserId, dto.getUserIds()));
    }

    @Override
    public void authUserConfirm(RoleAuthUserConfirmDTO dto) {
        List<Long> userIds = userRoleService.getUserIdsByRoleIds(Collections.singletonList(dto.getRoleId()));
        List<SysUserRole> userRoleList = dto.getUserIds().stream()
                .filter(p -> !userIds.contains(p))
                .map(p -> new SysUserRole(p, dto.getRoleId())).collect(Collectors.toList());
        // 创建关联信息
        if (CollectionUtils.isNotEmpty(userRoleList)) {
            userRoleService.saveBatch(userRoleList);
        }
    }

    @Override
    public void changeStatus(Long roleId, Boolean status) {
        SysRole sysRole = new SysRole();
        sysRole.setId(roleId);
        sysRole.setStatus(status);
        roleMapper.updateById(sysRole);
    }
}
