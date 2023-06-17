package com.pandama.top.user.pojo.vo;

import com.pandama.top.user.enums.MenuTypeEnum;
import com.pandama.top.user.pojo.UserConstants;
import com.pandama.top.core.pojo.vo.TreeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 动态路由信息出参
 * @author: 王强
 * @dateTime: 2023-04-24 17:03:45
 */
@EqualsAndHashCode(callSuper = false)
@Data
@ApiModel("动态路由信息")
public class RouterTreeResultVO extends TreeVO {

    private static final long serialVersionUID = -8106549102452574307L;
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("是否隐藏")
    private Boolean hidden;

    @ApiModelProperty("重定向")
    private Boolean redirect;

    @ApiModelProperty("组件")
    private String component;

    @ApiModelProperty("类型")
    private MenuTypeEnum type;

    @ApiModelProperty("路由元数据")
    private MenuMetaResultVO meta;

    /**
     * @description: 获取路径
     * @author: 王强
     * @date: 2023-04-25 15:28:40
     * @return: String
     * @version: 1.0
     */
    public String getPath() {
        String routerPath;
        // 一级
        if (0 == getParentId().intValue()) {
            routerPath = "/" + path;
        }
        // 非一级
        else {
            routerPath = path;
        }
        return routerPath;
    }

    /**
     * @description: 获取组件
     * @author: 王强
     * @date: 2023-04-25 15:28:37
     * @return: String
     * @version: 1.0
     */
    public String getComponent() {
        String component;
        // 一级目录
        if (0 == getParentId().intValue() && MenuTypeEnum.DIR.equals(getType())) {
            component = UserConstants.RouterComponent.LAY_OUT;
        }
        // 非一级目录
        else if (MenuTypeEnum.DIR.equals(getType())) {
            component = UserConstants.RouterComponent.PARENT_VIEW;
        }
        // 一级菜单
        else if (0 == getParentId().intValue() && MenuTypeEnum.MENU.equals(getType())) {
            component = meta.getComponent();
        }
        // 非一级菜单
        else {
            component = meta.getComponent();
        }
        return component;
    }
}
