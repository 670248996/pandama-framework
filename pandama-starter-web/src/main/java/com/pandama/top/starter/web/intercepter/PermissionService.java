package com.pandama.top.starter.web.intercepter;

import com.pandama.top.core.utils.UserInfoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RuoYi首创 自定义权限实现，ss取自SpringSecurity首字母
 *
 * @author ruoyi
 */
@Service("ss")
public class PermissionService {
    /**
     * 所有权限标识
     */
    private static final String ALL_PERMISSION = "*:*:*";
    /**
     * 管理员角色权限标识
     */
    private static final String SUPER_ADMIN = "admin";

    private static final String ROLE_DELIMETER = ",";

    private static final String PERMISSION_DELIMETER = ",";

    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        List<String> permCodeList = UserInfoUtils.getUserInfo().getPermCodeList();
        return hasPermissions(permCodeList, permission);
    }

    /**
     * 验证用户是否不具备某权限，与 hasPermi逻辑相反
     *
     * @param permission 权限字符串
     * @return 用户是否不具备某权限
     */
    public boolean lacksPermi(String permission) {
        return !hasPermi(permission);
    }

    /**
     * 验证用户是否具有以下任意一个权限
     *
     * @param permissions 以 PERMISSION_NAMES_DELIMETER 为分隔符的权限列表
     * @return 用户是否具有以下任意一个权限
     */
    public boolean hasAnyPermi(String permissions) {
        if (StringUtils.isEmpty(permissions)) {
            return false;
        }
        List<String> permCodeList = UserInfoUtils.getUserInfo().getPermCodeList();
        for (String permission : permissions.split(PERMISSION_DELIMETER)) {
            if (permission != null && hasPermissions(permCodeList, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否拥有某个角色
     *
     * @param role 角色字符串
     * @return 用户是否具备某角色
     */
    public boolean hasRole(String role) {
        if (StringUtils.isEmpty(role)) {
            return false;
        }
        List<String> roleCodeList = UserInfoUtils.getUserInfo().getRoleCodeList();
        return hasRoles(roleCodeList, role);
    }

    /**
     * 验证用户是否不具备某角色，与 isRole逻辑相反。
     *
     * @param role 角色名称
     * @return 用户是否不具备某角色
     */
    public boolean lacksRole(String role) {
        return !hasRole(role);
    }

    /**
     * 判断是否包含权限
     *
     * @param permissions 权限列表
     * @param permission  权限字符串
     * @return 用户是否具备某权限
     */
    private boolean hasPermissions(List<String> permissions, String permission) {
        return permissions.contains(ALL_PERMISSION) || permissions.contains(StringUtils.trim(permission));
    }

    /**
     * 判断是否包含权限
     *
     * @param roleCodes 权限列表
     * @param roleCode  权限字符串
     * @return 用户是否具备某权限
     */
    private boolean hasRoles(List<String> roleCodes, String roleCode) {
        return roleCodes.contains(SUPER_ADMIN) || roleCodes.contains(StringUtils.trim(roleCode));
    }
}
