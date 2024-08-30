package com.pandama.top.user.entity;

import com.pandama.top.mybatisplus.entity.BaseEntity;
import com.pandama.top.user.enums.MenuTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单信息表
 *
 * @author 王强
 * @date 2023-07-08 15:47:14
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
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单类型（目录：DIR，菜单：MENU，按钮：BUTTON）
     */
    private MenuTypeEnum menuType;
    /**
     * 菜单编号/标识（a:b:c）
     */
    private String menuCode;
    /**
     * 菜单路径
     */
    private String menuUrl;
    /**
     * 菜单参数
     */
    private String menuParams;
    /**
     * 元数据（不同菜单类型特有属性JSON）
     */
    private String meta;
    /**
     * 是否可用(0: 不可用, 1: 可用)
     */
    private Boolean status;
    /**
     * 菜单等级
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
