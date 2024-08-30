package com.pandama.top.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandama.top.user.entity.SysRoleMenu;
import com.pandama.top.user.mapper.RoleMenuMapper;
import com.pandama.top.user.service.RoleMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色菜单服务impl
 *
 * @author 王强
 * @date 2023-07-08 15:54:04
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, SysRoleMenu> implements RoleMenuService {

    private final RoleMenuMapper roleMenuMapper;

    @Override
    public List<Long> getMenuIdsByRoleIds(List<Long> roleIds) {
        if (CollectionUtils.isNotEmpty(roleIds)) {
            return roleMenuMapper.getMenuIdsByRoleIds(roleIds);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Long> getRoleIdsByMenuIds(List<Long> menuIds) {
        if (CollectionUtils.isNotEmpty(menuIds)) {
            return roleMenuMapper.getRoleIdsByMenuIds(menuIds);
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        if (CollectionUtils.isNotEmpty(roleIds)) {
            roleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>()
                    .in(SysRoleMenu::getRoleId, roleIds));
        }
    }

    @Override
    public void deleteByMenuIds(List<Long> menuIds) {
        if (CollectionUtils.isNotEmpty(menuIds)) {
            roleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>()
                    .in(SysRoleMenu::getMenuId, menuIds));
        }
    }
}
