package com.pandama.top.user.biz.entity;

import com.pandama.top.mybatisplus.entity.BaseEntity;
import com.pandama.top.user.api.enums.MenuTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 权限信息表
 * @author: 白剑民
 * @dateTime: 2022/10/17 16:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysMenu extends BaseEntity {
    /**
     * ids
     */
    private String ids;
    /**
     * 父级id
     */
    private Long parentId;
    /**
     * 权限名称
     */
    private String menuName;
    /**
     * 权限类型（目录：DIR，菜单：MENU，按钮：BUTTON，接口：API，数据：DATA）
     */
    private MenuTypeEnum menuType;
    /**
     * 权限编号/标识（a:b:c）
     */
    private String menuCode;
    /**
     * 权限路径
     */
    private String menuUrl;
    /**
     * 权限参数
     */
    private String menuParams;
    /**
     * 元数据（不同权限类型特有属性JSON）
     */
    private String meta;
    /**
     * 是否可用(0: 不可用, 1: 可用)
     */
    private Boolean status;
    /**
     * 权限等级
     */
    private Integer level;
    /**
     * 显示顺序
     */
    private Integer orderNum;
    /**
     * 终端类型（字典表枚举）
     */
    private Integer terminalType;
    /**
     * 备注
     */
    private String remark;
}
