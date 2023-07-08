package com.pandama.top.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.pandama.top.auth.api.constant.RedisConstant;
import com.pandama.top.core.utils.TreeUtils;
import com.pandama.top.core.utils.UserInfoUtils;
import com.pandama.top.redis.utils.RedisUtils;
import com.pandama.top.user.pojo.dto.MenuSearchDTO;
import com.pandama.top.user.pojo.vo.MenuSearchResultVO;
import com.pandama.top.user.pojo.vo.RouterTreeResultVO;
import com.pandama.top.user.pojo.vo.SystemUserInfoVO;
import com.pandama.top.user.api.pojo.vo.UserLoginVO;
import com.pandama.top.user.entity.SysRole;
import com.pandama.top.user.mapper.UserMapper;
import com.pandama.top.user.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
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

    private final MenuService menuService;

    private final RedisUtils redisUtils;

    @Override
    public UserLoginVO loginByUsername(String username) {
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
    public UserLoginVO loginByPhone(String phone) {
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

    @Override
    public SystemUserInfoVO getUserInfo() {
        SystemUserInfoVO userInfo = userMapper.getSystemUserInfoById(UserInfoUtils.getUserId());
        // 缓存用户信息，一般在用户登出后清空，避免数据产生，缓存有效期24小时
        String userKey = String.format(RedisConstant.USER_INFO, UserInfoUtils.getUserId());
        redisUtils.setEx(userKey, JSON.toJSONString(userInfo), 1, TimeUnit.DAYS);
        if (userInfo != null) {
            this.setRoleAndPerm(userInfo);
        }
        return userInfo;
    }

    /**
     * @param userInfo 登录后的用户信息获取
     * @description: 封装角色和权限信息
     * @author: 白剑民
     * @date: 2023-04-27 10:40:02
     * @return: com.gientech.iot.user.api.entity.vo.SystemUserInfoVO
     * @version: 1.0
     */
    private void setRoleAndPerm(SystemUserInfoVO userInfo) {
        // 获取用户所有角色
        List<SysRole> roles = roleService.listByUserId(userInfo.getUserId());
        // 角色id列表
        List<Long> roleIds = new ArrayList<>();
        // 角色编号列表
        List<String> roleCodes = new ArrayList<>();
        // 遍历所有角色
        for (SysRole r : roles) {
            roleIds.add(r.getId());
            roleCodes.add(r.getRoleCode());
        }
        // 赋值角色编号列表
        userInfo.setRoleCodeList(roleCodes);
        List<MenuSearchResultVO> menuList = new ArrayList<>();
        // 管理员查询所有权限信息
        if (userInfo.getIsAdmin()) {
            MenuSearchDTO searchDTO = new MenuSearchDTO();
            searchDTO.setStatus(true);
            menuList = menuService.list(searchDTO);
            userInfo.setPermCodeList(Collections.singletonList("*:*:*"));
            userInfo.setRoleCodeList(Collections.singletonList("Admin"));
        }
        // 如果存在角色信息
        else if (roleIds.size() > 0) {
            // 获取角色下所有权限列表
            menuList = menuService.getListByRoleIds(roleIds);
            List<String> permList = menuList.stream()
                    .map(MenuSearchResultVO::getMenuCode).distinct().collect(Collectors.toList());
            userInfo.setPermCodeList(permList);
        }
        // 过滤出路由权限
        List<MenuSearchResultVO> collect = menuList.stream()
                .filter(p -> p.getMenuType().isRouter()).collect(Collectors.toList());
        // 路由权限封装成路由列表
        userInfo.setRouterList(TreeUtils.listToTree(collect, RouterTreeResultVO::new, (e, v) -> {
            v.setMeta(e.getMeta());
            v.setName(e.getMenuUrl());
            v.setPath(e.getMenuUrl());
            v.setType(e.getMenuType());
            v.setHidden(!e.getStatus());
            v.setRedirect(false);
        }));
    }
}
