package com.pandama.top.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandama.top.auth.open.constant.RedisConstant;
import com.pandama.top.core.pojo.dto.Sort;
import com.pandama.top.core.utils.TreeUtils;
import com.pandama.top.core.utils.UserInfoUtils;
import com.pandama.top.redis.utils.RedisUtils;
import com.pandama.top.core.global.exception.CommonException;
import com.pandama.top.core.pojo.vo.PageVO;
import com.pandama.top.user.entity.SysUserDept;
import com.pandama.top.user.entity.SysRole;
import com.pandama.top.user.entity.SysUser;
import com.pandama.top.user.entity.SysUserRole;
import com.pandama.top.user.mapper.UserMapper;
import com.pandama.top.core.utils.BeanConvertUtils;
import com.pandama.top.core.utils.IdCardUtils;
import com.pandama.top.user.pojo.dto.*;
import com.pandama.top.user.pojo.vo.*;
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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户服务impl
 *
 * @author 王强
 * @date 2023-07-08 15:54:09
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
            departmentUserService.save(new SysUserDept(dto.getDeptId(), user.getId()));
        }
    }

    @Override
    public void batchRegister(List<UserRegisterDTO> dtoList) {
        // 执行注册方法
        dtoList.forEach(this::register);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids) {
        userMapper.deleteBatchIds(ids);
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
            departmentUserService.save(new SysUserDept(dto.getDeptId(), dto.getUserId()));
        }
    }

    @Override
    public PageVO<UserSearchResultVO> page(UserSearchDTO dto) {
        if (CollectionUtils.isEmpty(dto.getSorts())) {
            dto.getSorts().add(new Sort("create_time", "desc"));
        }
        Page<UserSearchResultVO> page = userMapper.page(new Page<>(dto.getCurrent(), dto.getSize()), dto);
        return new PageVO<>(page.getTotal(), page.getSize(), page.getCurrent(), page.getPages(), page.getRecords());
    }

    @Override
    public List<UserDetailResultVO> listByDeptId(Long deptId) {
        return userMapper.getUserListByDeptId(deptId);
    }

    @Override
    public UserDetailResultVO getUserInfoById(Long id) {
        return userMapper.getUserInfoById(id);
    }

    @Override
    public void changeStatus(Long id, Boolean status) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
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
    public void updateAvatar(AvatarUpdateDTO dto) {
        SysUser sysUser = new SysUser();
        sysUser.setId(dto.getUserId());
        sysUser.setAvatar(dto.getAvatar());
        userMapper.updateById(sysUser);
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
     * 封装角色和权限信息
     *
     * @param userInfo 登录后的用户信息获取
     * @author 王强
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
