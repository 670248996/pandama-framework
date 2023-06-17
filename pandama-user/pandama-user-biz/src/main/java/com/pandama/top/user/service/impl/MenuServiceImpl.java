package com.pandama.top.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandama.top.core.global.exception.CommonException;
import com.pandama.top.user.entity.SysRoleMenu;
import com.pandama.top.user.pojo.vo.*;
import com.pandama.top.user.service.MenuService;
import com.pandama.top.user.service.RoleMenuService;
import com.pandama.top.user.service.RoleService;
import com.pandama.top.core.utils.BeanConvertUtils;
import com.pandama.top.core.utils.TreeUtils;
import com.pandama.top.user.pojo.dto.MenuCreateDTO;
import com.pandama.top.user.pojo.dto.MenuMetaDTO;
import com.pandama.top.user.pojo.dto.MenuSearchDTO;
import com.pandama.top.user.pojo.dto.MenuUpdateDTO;
import com.pandama.top.user.entity.SysMenu;
import com.pandama.top.user.enums.CustomErrorCodeEnum;
import com.pandama.top.user.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 菜单信息实现类
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
    public void create(MenuCreateDTO dto) {
        SysMenu parent = dto.getParentId() == 0 ? new SysMenu() : menuMapper.selectById(dto.getParentId());
        if (parent == null) {
            throw new CommonException(CustomErrorCodeEnum.PARENT_NOT_EXIT);
        }
        // 将传参字段转换赋值成菜单实体属性
        SysMenu sysMenu = BeanConvertUtils.convert(dto, SysMenu::new, (s, t) -> {
                    t.setMeta(JSON.toJSONString(s.getMeta()));
                    t.setParentId(s.getParentId());
                    t.setIds(parent.getIds() == null ? String.valueOf(t.getId()) : parent.getIds() + "," + t.getId());
                    t.setLevel(t.getIds().split(",").length);
                }).orElseThrow(() -> new CommonException(CustomErrorCodeEnum.MENU_CREATE_ERROR));
        menuMapper.insert(sysMenu);
    }

    @Override
    public MenuDetailResultVO detail(Long id) {
        SysMenu menu = menuMapper.selectById(id);
        return BeanConvertUtils.convert(menu, MenuDetailResultVO::new, (s, t) -> {
            t.setId(s.getId());
            t.setMeta(JSONObject.parseObject(s.getMeta(), MenuMetaDTO.class));
        }).orElse(new MenuDetailResultVO());
    }

    @Override
    public void update(MenuUpdateDTO dto) {
        SysMenu menu = menuMapper.selectById(dto.getMenuId());
        // 判断是否为迁移菜单操作（变更父级菜单id）
        if (StringUtils.isEmpty(menu.getIds()) || !Objects.equals(menu.getParentId(), dto.getParentId())) {
            SysMenu parent = dto.getParentId() == 0 ? new SysMenu() : menuMapper.selectById(dto.getParentId());
            if (parent == null) {
                throw new CommonException(CustomErrorCodeEnum.PARENT_NOT_EXIT);
            }
            // 更新菜单信息
            SysMenu dept = BeanConvertUtils.convert(dto, SysMenu::new, (s, t) -> {
                t.setId(s.getMenuId());
                t.setParentId(dto.getParentId());
                t.setIds(parent.getIds() == null ? String.valueOf(t.getId()) : parent.getIds() + "," + t.getId());
                t.setLevel(t.getIds().split(",").length);
                t.setMeta(JSON.toJSONString(s.getMeta()));
            }).orElseThrow(() -> new CommonException(CustomErrorCodeEnum.MENU_UPDATE_ERROR));
            // 获取菜单下的子菜单列表
            List<SysMenu> childPermList =
                    menuMapper.getMenuListByParentIds(Collections.singletonList(dto.getMenuId()), false);
            // 更新子菜单ids
            for (SysMenu child : childPermList) {
                List<String> split = Arrays.asList(child.getIds() == null ? new String[]{""} : child.getIds().split(","));
                List<String> strings = split.subList(split.indexOf(String.valueOf(dept.getId())), split.size());
                strings.set(0, dept.getIds());
                child.setIds(strings.stream().filter(StringUtils::isNotEmpty).collect(Collectors.joining(",")));
            }
            childPermList.add(dept);
            // 批量更新菜单信息
            updateBatchById(childPermList);
        } else {
            // 将传参字段转换赋值成菜单实体属性
            SysMenu dept = BeanConvertUtils.convert(dto, SysMenu::new, (s, t) -> {
                t.setId(s.getMenuId());
                t.setMeta(JSON.toJSONString(s.getMeta()));
            }).orElseThrow(() -> new CommonException(CustomErrorCodeEnum.MENU_UPDATE_ERROR));
            // 更新单个菜单信息
            menuMapper.updateById(dept);
        }
    }

    @Override
    public void delete(List<Long> menuIds) {
        List<SysMenu> menuList = menuMapper.getMenuListByParentIds(menuIds, true);
        List<Long> menuIdList = menuList.stream().map(SysMenu::getId).collect(Collectors.toList());
        // 获取指定菜单列表下的关联角色列表
        List<Long> roleIdList = roleMenuService.getRoleIdsByMenuIds(menuIdList);
        // 判断菜单有无关联角色
        if (roleIdList.size() > 0) {
            throw new CommonException(CustomErrorCodeEnum.MENU_HAS_ROLE);
        }
        // 菜单逻辑删除
        menuMapper.deleteBatchIds(menuList);
    }

    @Override
    public List<MenuSearchResultVO> list(MenuSearchDTO dto) {
        List<SysMenu> list = menuMapper.list(dto);
        return (List<MenuSearchResultVO>) BeanConvertUtils.convertCollection(list,
                MenuSearchResultVO::new, (s, t) -> {
                    t.setMeta(JSONObject.parseObject(s.getMeta(), MenuMetaResultVO.class));
                }).orElse(new ArrayList<>());
    }

    @Override
    public List<MenuSearchResultVO> getListByRoleIds(List<Long> roleIds) {
        List<SysMenu> list = menuMapper.getMenuListByRoleIds(roleIds);
        return (List<MenuSearchResultVO>) BeanConvertUtils.convertCollection(list, MenuSearchResultVO::new, (s, t) -> {
            t.setMeta(JSONObject.parseObject(s.getMeta(), MenuMetaResultVO.class));
        }).orElse(new ArrayList<>());
    }

    @Override
    public TreeSelectVO tree(MenuSearchDTO dto) {
        List<SysMenu> list = menuMapper.list(dto);
        List<Long> menuIds = list.stream().map(SysMenu::getId).collect(Collectors.toList());
        return new TreeSelectVO(menuIds, TreeUtils.listToTree(list, MenuTreeVO::new));
    }

    @Override
    public TreeSelectVO getTreeSelectByRoleId(Long roleId) {
        Optional.ofNullable(roleService.getById(roleId))
                .orElseThrow(() -> new CommonException(CustomErrorCodeEnum.ROLE_NOT_EXIT));
        List<SysMenu> permList = menuMapper.getMenuListByRoleIds(Collections.singletonList(roleId));
        List<Long> menuIds = permList.stream().map(SysMenu::getId).collect(Collectors.toList());
        return new TreeSelectVO(menuIds, TreeUtils.listToTree(permList, MenuTreeVO::new));
    }

    @Override
    public void assignMenu(Long roleId, List<Long> menuIds) {
        // 定义用户与角色关联列表
        List<SysRoleMenu> menus = new ArrayList<>();
        menuIds.forEach(menuId -> menus.add(new SysRoleMenu(roleId, menuId)));
        // 创建关联信息
        roleMenuService.saveBatch(menus);
    }

    @Override
    public void changeStatus(Long menuId, Boolean status) {
        SysMenu menu = new SysMenu();
        menu.setId(menuId);
        menu.setStatus(status);
        menuMapper.updateById(menu);
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
        log.info("=====================初始化菜单数据=====================");
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
