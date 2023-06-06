package com.pandama.top.user.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandama.top.global.exception.CommonException;
import com.pandama.top.user.api.pojo.vo.*;
import com.pandama.top.user.biz.entity.SysRoleMenu;
import com.pandama.top.user.biz.service.MenuService;
import com.pandama.top.user.biz.service.RoleMenuService;
import com.pandama.top.user.biz.service.RoleService;
import com.pandama.top.utils.BeanConvertUtils;
import com.pandama.top.utils.TreeUtils;
import com.pandama.top.user.api.pojo.dto.PermissionCreateDTO;
import com.pandama.top.user.api.pojo.dto.PermissionMetaDTO;
import com.pandama.top.user.api.pojo.dto.PermissionSearchDTO;
import com.pandama.top.user.api.pojo.dto.PermissionUpdateDTO;
import com.pandama.top.user.biz.entity.SysMenu;
import com.pandama.top.user.biz.enums.CustomErrorCodeEnum;
import com.pandama.top.user.biz.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 权限信息实现类
 * @author: 白剑民
 * @dateTime: 2022/10/31 13:36
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MenuServiceImpl extends ServiceImpl<MenuMapper, SysMenu> implements MenuService {

    private final MenuMapper menuMapper;

    private final RoleMenuService roleMenuService;

    private final RoleService roleService;

    @Override
    public MenuCreateResultVO create(PermissionCreateDTO dto) {
        SysMenu parent = dto.getParentId() == 0 ? new SysMenu() : menuMapper.selectById(dto.getParentId());
        if (parent == null) {
            throw new CommonException(CustomErrorCodeEnum.PARENT_NOT_EXIT);
        }
        // 将传参字段转换赋值成权限实体属性
        SysMenu sysPermission = BeanConvertUtils.convert(dto, SysMenu::new, (s, t) -> {
                    t.setMeta(JSON.toJSONString(s.getMeta()));
                    t.setParentId(s.getParentId());
                    t.setIds(parent.getIds() == null ? String.valueOf(t.getId()) : parent.getIds() + "," + t.getId());
                    t.setLevel(t.getIds().split(",").length);
                }).orElseThrow(() -> new CommonException(CustomErrorCodeEnum.PERMISSION_CREATE_ERROR));
        menuMapper.insert(sysPermission);
        // 封装返回内容
        MenuCreateResultVO resultVO = new MenuCreateResultVO();
        resultVO.setId(sysPermission.getId());
        return resultVO;
    }

    @Override
    public MenuDetailResultVO detail(Long id) {
        SysMenu permission = menuMapper.selectById(id);
        return BeanConvertUtils.convert(permission, MenuDetailResultVO::new, (s, t) -> {
            t.setId(s.getId());
            t.setMeta(JSONObject.parseObject(s.getMeta(), PermissionMetaDTO.class));
        }).orElse(new MenuDetailResultVO());
    }

    @Override
    public void update(PermissionUpdateDTO dto) {
        SysMenu permission = menuMapper.selectById(dto.getMenuId());
        // 判断是否为迁移权限操作（变更父级权限id）
        if (StringUtils.isEmpty(permission.getIds()) || !Objects.equals(permission.getParentId(), dto.getParentId())) {
            SysMenu parent = dto.getParentId() == 0 ? new SysMenu() : menuMapper.selectById(dto.getParentId());
            if (parent == null) {
                throw new CommonException(CustomErrorCodeEnum.PARENT_NOT_EXIT);
            }
            // 更新权限信息
            SysMenu dept = BeanConvertUtils.convert(dto, SysMenu::new, (s, t) -> {
                t.setId(s.getMenuId());
                t.setParentId(dto.getParentId());
                t.setIds(parent.getIds() == null ? String.valueOf(t.getId()) : parent.getIds() + "," + t.getId());
                t.setLevel(t.getIds().split(",").length);
                t.setMeta(JSON.toJSONString(s.getMeta()));
            }).orElseThrow(() -> new CommonException(CustomErrorCodeEnum.PERMISSION_UPDATE_ERROR));
            // 获取权限下的子权限列表
            List<SysMenu> childPermList =
                    menuMapper.getPermListByParentIds(Collections.singletonList(dto.getMenuId()), false);
            // 更新子权限ids
            for (SysMenu child : childPermList) {
                List<String> split = Arrays.asList(child.getIds() == null ? new String[]{""} : child.getIds().split(","));
                List<String> strings = split.subList(split.indexOf(String.valueOf(dept.getId())), split.size());
                strings.set(0, dept.getIds());
                child.setIds(strings.stream().filter(StringUtils::isNotEmpty).collect(Collectors.joining(",")));
            }
            childPermList.add(dept);
            // 批量更新权限信息
            updateBatchById(childPermList);
        } else {
            // 将传参字段转换赋值成权限实体属性
            SysMenu dept = BeanConvertUtils.convert(dto, SysMenu::new, (s, t) -> {
                t.setId(s.getMenuId());
                t.setMeta(JSON.toJSONString(s.getMeta()));
            }).orElseThrow(() -> new CommonException(CustomErrorCodeEnum.PERMISSION_UPDATE_ERROR));
            // 更新单个权限信息
            menuMapper.updateById(dept);
        }
    }

    @Override
    public void delete(List<Long> permissionIds) {
        List<SysMenu> permList = menuMapper.getPermListByParentIds(permissionIds, true);
        List<Long> permIds = permList.stream().map(SysMenu::getId).collect(Collectors.toList());
        // 获取指定权限列表下的关联角色列表
        List<Long> permissionList = roleMenuService.getRoleIdsByPermIds(permIds);
        // 判断权限有无关联角色
        if (permissionList.size() > 0) {
            throw new CommonException(CustomErrorCodeEnum.PERMISSION_HAS_ROLE);
        }
        // 权限逻辑删除
        menuMapper.deleteBatchIds(permList);
    }

    @Override
    public List<MenuSearchResultVO> list(PermissionSearchDTO dto) {
        List<SysMenu> list = menuMapper.list(dto);
        return (List<MenuSearchResultVO>) BeanConvertUtils.convertCollection(list,
                MenuSearchResultVO::new, (s, t) -> {
                    t.setMeta(JSONObject.parseObject(s.getMeta(), MenuMetaResultVO.class));
                }).orElse(new ArrayList<>());
    }

    @Override
    public List<MenuSearchResultVO> getListByRoleIds(List<Long> roleIds) {
        List<SysMenu> permList = menuMapper.getMenuListByRoleIds(roleIds);
        return (List<MenuSearchResultVO>) BeanConvertUtils.convertCollection(permList, MenuSearchResultVO::new, (s, t) -> {
            t.setMeta(JSONObject.parseObject(s.getMeta(), MenuMetaResultVO.class));
        }).orElse(new ArrayList<>());
    }

    @Override
    public TreeSelectVO getTreeSelectByRoleId(Long roleId) {
        Optional.ofNullable(roleService.getById(roleId))
                .orElseThrow(() -> new CommonException(CustomErrorCodeEnum.ROLE_NOT_EXIT));
        List<SysMenu> permList = menuMapper.getMenuListByRoleIds(Collections.singletonList(roleId));
        List<Long> permIds = permList.stream().map(SysMenu::getId).collect(Collectors.toList());
        return new TreeSelectVO(permIds, TreeUtils.listToTree(permList, MenuTreeVO::new));
    }

    @Override
    public void assignPermission(Long roleId, List<Long> permissionIds) {
        // 定义用户与角色关联列表
        List<SysRoleMenu> permissions = new ArrayList<>();
        permissionIds.forEach(menuId -> permissions.add(new SysRoleMenu(roleId, menuId)));
        // 创建关联信息
        roleMenuService.saveBatch(permissions);
    }

    @Override
    public void changePermissionStatus(Long menuId, Boolean status) {
        SysMenu sysPermission = menuMapper.selectById(menuId);
        sysPermission.setStatus(status);
        menuMapper.updateById(sysPermission);
    }

    /**
     * @description: 初始化数据
     * @author: 王强
     * @date: 2023-05-04 13:00:56
     * @return: void
     * @version: 1.0
     */
    @PostConstruct
    public void initData() {
        log.info("=====================初始化权限数据=====================");
        List<SysMenu> allPermList = menuMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                .select(SysMenu::getId, SysMenu::getIds, SysMenu::getParentId));
        Map<Long, String> deptIdsMap = new HashMap<>(allPermList.size());
        for (SysMenu perm : allPermList) {
            String parentIds = deptIdsMap.get(perm.getParentId());
            perm.setIds(parentIds == null ? String.valueOf(perm.getId()) : parentIds + "," + perm.getId());
            perm.setLevel(perm.getIds().split(",").length);
            deptIdsMap.put(perm.getId(), perm.getIds());
        }
        updateBatchById(allPermList);
    }
}
