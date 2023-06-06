package com.pandama.top.user.biz.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandama.top.cache.utils.RedisUtils;
import com.pandama.top.global.exception.CommonException;
import com.pandama.top.pojo.vo.PageResultVO;
import com.pandama.top.user.api.pojo.dto.*;
import com.pandama.top.user.api.pojo.vo.*;
import com.pandama.top.user.biz.entity.SysDeptUser;
import com.pandama.top.user.biz.entity.SysRole;
import com.pandama.top.user.biz.entity.SysUser;
import com.pandama.top.user.biz.entity.SysUserRole;
import com.pandama.top.user.biz.enums.CustomErrorCodeEnum;
import com.pandama.top.user.biz.mapper.UserMapper;
import com.pandama.top.user.biz.service.*;
import com.pandama.top.utils.BeanConvertUtils;
import com.pandama.top.utils.IdCardUtils;
import com.pandama.top.utils.TreeUtils;
import com.pandama.top.utils.UserInfoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
    public UserRegisterResultVO register(UserRegisterDTO dto) {
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
        }).orElseThrow(() -> new CommonException(CustomErrorCodeEnum.USER_CREATE_ERROR));
        userMapper.insert(user);
        if (dto.getDeptId() != null) {
            // 保存用户新关联的部门信息
            departmentUserService.save(new SysDeptUser(dto.getDeptId(), user.getId()));
        }
        // 封装返回内容
        UserRegisterResultVO resultVO = new UserRegisterResultVO();
        resultVO.setId(user.getId());
        return resultVO;
    }

    @Override
    public List<UserRegisterResultVO> batchRegister(List<UserRegisterDTO> dtoList) {
        // 执行注册方法
        return dtoList.stream().map(this::register).collect(Collectors.toList());
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
    public UserLoginVO getUserLoginInfoByUsername(String username) {
        UserLoginVO userLoginVO = userMapper.getLoginInfoByUsernameOrPhone(username, null);
        if (userLoginVO != null) {
            if (userLoginVO.getIsAdmin()) {
                userLoginVO.setRoleCodeList(Collections.singletonList("Admin"));
            } else {
                List<SysRole> roleList = roleService.listByUserId(userLoginVO.getUserId());
                userLoginVO.setRoleCodeList(roleList.stream().map(SysRole::getRoleCode).collect(Collectors.toList()));
            }
        }
        return userLoginVO;
    }

    @Override
    public UserLoginVO getUserLoginInfoByPhone(String phone) {
        UserLoginVO userLoginVO = userMapper.getLoginInfoByUsernameOrPhone(null, phone);
        if (userLoginVO != null) {
            if (userLoginVO.getIsAdmin()) {
                userLoginVO.setRoleCodeList(Collections.singletonList("Admin"));
            } else {
                List<SysRole> roleList = roleService.listByUserId(userLoginVO.getUserId());
                userLoginVO.setRoleCodeList(roleList.stream().map(SysRole::getRoleCode).collect(Collectors.toList()));
            }
        }
        return userLoginVO;
    }

    @Override
    public PageResultVO<UserSearchResultVO> page(UserSearchDTO dto) {
        Page<UserSearchResultVO> page = userMapper.page(new Page<>(dto.getCurrent(), dto.getSize()), dto);
        return new PageResultVO<>(page.getTotal(), page.getSize(), page.getCurrent(), page.getPages(), page.getRecords());
    }

    /**
     * @param userInfo 登录后的用户信息获取
     * @description: 封装角色和权限信息
     * @author: 白剑民
     * @date: 2023-04-27 10:40:02
     * @return: com.gientech.iot.user.api.entity.vo.SystemUserInfoVO
     * @version: 1.0
     */
    private void setRoleAndPermission(SystemUserInfoVO userInfo) {
        // 用户不存在就抛出异常
        if (null == userInfo) {
            throw new CommonException(CustomErrorCodeEnum.USER_IS_NOT_EXIST);
        }
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
        List<MenuSearchResultVO> permissionList = new ArrayList<>();
        // 管理员查询所有权限信息
        if (userInfo.getIsAdmin()) {
            PermissionSearchDTO searchDTO = new PermissionSearchDTO();
            searchDTO.setStatus(true);
            permissionList = menuService.list(searchDTO);
            userInfo.setPermCodeList(Collections.singletonList("*:*:*"));
            userInfo.setRoleCodeList(Collections.singletonList("Admin"));
        }
        // 如果存在角色信息
        else if (roleIds.size() > 0) {
            // 获取角色下所有权限列表
            permissionList = menuService.getListByRoleIds(roleIds);
            List<String> permList = permissionList.stream()
                    .map(MenuSearchResultVO::getMenuCode).distinct().collect(Collectors.toList());
            userInfo.setPermCodeList(permList);
        }
        // 过滤出路由权限
        List<MenuSearchResultVO> collect = permissionList.stream()
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

    @Override
    public List<UserDetailResultVO> getUserListByEnterpriseId(Long enterpriseId) {
        return userMapper.getUserListByEnterpriseId(enterpriseId);
    }

    @Override
    public List<UserDetailResultVO> getUserListByDepartmentId(Long deptId) {
        return userMapper.getUserListByDepartmentId(deptId);
    }

    @Override
    public SystemUserInfoVO getUserInfoByToken() {
        SystemUserInfoVO userInfo = userMapper.getSystemUserInfoById(UserInfoUtils.getUserId());
        // 缓存用户信息，一般在用户登出后清空，避免数据产生，缓存有效期24小时
        String userKey = "user_info" + UserInfoUtils.getUserId();
        redisUtils.setEx(userKey, JSON.toJSONString(userInfo), 1, TimeUnit.DAYS);
        // 设置密码到期天数
        userInfo.setPwdExpireDays(DateUtil.betweenDay(DateUtil.date(),
                DateTime.from(userInfo.getPasswordExpireTime().atZone(ZoneId.systemDefault()).toInstant()), false));
        this.setRoleAndPermission(userInfo);
        return userInfo;
    }

    @Override
    public UserDetailResultVO getUserInfoById(Long userId) {
        return userMapper.getUserInfoById(userId);
    }

    @Override
    public List<UserDetailResultVO> getUserInfoByIds(List<Long> userIds) {
        return userMapper.getUserInfoByIds(userIds);
    }

    @Override
    public UserDetailResultVO getUserInfoByIdCardNo(String idCardNo) {
        return userMapper.getUserInfoByIdCardNo(idCardNo);
    }

    @Override
    public List<UserDetailResultVO> getUserInfoByIdCardNos(List<String> idCardNos) {
        return userMapper.getUserInfoByIdCardNos(idCardNos);
    }

    @Override
    public UserDetailResultVO getUserInfoByCode(String code) {
        return userMapper.getUserInfoByCode(code);
    }

    @Override
    public List<UserDetailResultVO> getUserInfoByCodes(List<String> codes) {
        return userMapper.getUserInfoByCodes(codes);
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
                throw new CommonException(CustomErrorCodeEnum.OLD_PASSWORD_NOT_MATCH_ERROR);
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
    public PageResultVO<UserAuthRoleVO> authRolePage(UserAuthRoleSearchDTO dto) {
        List<Long> roleIds = userRoleService.getRoleIdsByUserIds(Collections.singletonList(dto.getUserId()));
        Page<SysRole> page = roleService.page(new Page<>(dto.getCurrent(), dto.getSize()),
                new LambdaQueryWrapper<SysRole>().eq(CollectionUtils.isEmpty(roleIds), SysRole::getId, 0)
                        .in(CollectionUtils.isNotEmpty(roleIds), SysRole::getId, roleIds)
                        .like(StringUtils.isNotEmpty(dto.getRoleName()), SysRole::getRoleName, dto.getRoleName()));
        List<UserAuthRoleVO> list = (List<UserAuthRoleVO>) BeanConvertUtils.convertCollection(page.getRecords(), UserAuthRoleVO::new, (s, t) -> {
            t.setRoleId(s.getId());
        }).orElse(new ArrayList<>());
        return new PageResultVO<>(page.getTotal(), page.getSize(), page.getCurrent(), page.getPages(), list);
    }

    @Override
    public PageResultVO<UserAuthRoleVO> unAuthRolePage(UserAuthRoleSearchDTO dto) {
        List<Long> roleIds = userRoleService.getRoleIdsByUserIds(Collections.singletonList(dto.getUserId()));
        Page<SysRole> page = roleService.page(new Page<>(dto.getCurrent(), dto.getSize()),
                new LambdaQueryWrapper<SysRole>()
                        .notIn(CollectionUtils.isNotEmpty(roleIds), SysRole::getId, roleIds)
                        .like(StringUtils.isNotEmpty(dto.getRoleName()), SysRole::getRoleName, dto.getRoleName()));
        List<UserAuthRoleVO> list = (List<UserAuthRoleVO>) BeanConvertUtils.convertCollection(page.getRecords(), UserAuthRoleVO::new, (s, t) -> {
            t.setRoleId(s.getId());
        }).orElse(new ArrayList<>());
        return new PageResultVO<>(page.getTotal(), page.getSize(), page.getCurrent(), page.getPages(), list);
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
