package com.pandama.top.user.controller;

import com.pandama.top.core.global.response.Response;
import com.pandama.top.user.pojo.dto.MenuCreateDTO;
import com.pandama.top.user.pojo.dto.MenuSearchDTO;
import com.pandama.top.user.pojo.dto.MenuUpdateDTO;
import com.pandama.top.user.pojo.vo.MenuDetailResultVO;
import com.pandama.top.user.pojo.vo.MenuSearchResultVO;
import com.pandama.top.user.pojo.vo.TreeSelectVO;
import com.pandama.top.user.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 菜单控制器
 *
 * @author 王强
 * @date 2023-07-08 15:44:03
 */
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Api(tags = "菜单信息相关接口")
@Validated
@Slf4j
public class MenuController {

    private final MenuService menuService;

    /**
     * 创建
     *
     * @param dto 创建菜单信息传参
     * @return com.pandama.top.core.global.response.Response<?>
     * @author 王强
     */
    @ApiOperation("创建菜单信息")
    @PostMapping
    public Response<?> create(@Valid @RequestBody MenuCreateDTO dto) {
        menuService.create(dto);
        return Response.success();
    }

    /**
     * 详情
     *
     * @param menuId 菜单信息详情传参
     * @return com.pandama.top.core.global.response.Response<com.pandama.top.user.pojo.vo.MenuDetailResultVO>
     * @author 王强
     */
    @ApiOperation("菜单信息详情")
    @GetMapping
    public Response<MenuDetailResultVO> detail(@ApiParam("菜单id，必填项") @NotNull(message = "菜单id，menuId不能为null") @RequestParam("menuId") Long menuId) {
        return Response.success(menuService.detail(menuId));
    }

    /**
     * 更新
     *
     * @param dto 菜单信息更新传参
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("菜单信息更新")
    @PutMapping
    public Response<Void> update(@Valid @RequestBody MenuUpdateDTO dto) {
        menuService.update(dto);
        return Response.success();
    }

    /**
     * 删除
     *
     * @param menuIds 菜单id列表
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("删除菜单信息")
    @DeleteMapping
    public Response<Void> delete(@ApiParam("菜单id列表，必填项") @NotEmpty(message = "菜单id列表，menuIds不能为null") @RequestParam("menuIds") List<Long> menuIds) {
        menuService.delete(menuIds);
        return Response.success();
    }

    /**
     * 列表
     *
     * @param dto 入参
     * @return com.pandama.top.core.global.response.Response<java.util.List < com.pandama.top.user.pojo.vo.MenuSearchResultVO>>
     * @author 王强
     */
    @ApiOperation("获取菜单信息列表")
    @PostMapping("/list")
    public Response<List<MenuSearchResultVO>> list(@Validated @RequestBody MenuSearchDTO dto) {
        return Response.success(menuService.list(dto));
    }

    /**
     * 树
     *
     * @param dto 入参
     * @return com.pandama.top.core.global.response.Response<com.pandama.top.user.pojo.vo.TreeSelectVO>
     * @author 王强
     */
    @ApiOperation("获取菜单树下拉")
    @PostMapping("/tree")
    public Response<TreeSelectVO> tree(@Validated @RequestBody MenuSearchDTO dto) {
        return Response.success(menuService.tree(dto));
    }

    /**
     * 树查询通过角色id
     *
     * @param roleId 角色id
     * @return com.pandama.top.core.global.response.Response<com.pandama.top.user.pojo.vo.TreeSelectVO>
     * @author 王强
     */
    @ApiOperation("获取角色其下菜单树下拉")
    @GetMapping("/treeSelectByRoleId")
    public Response<TreeSelectVO> treeSelectByRoleId(@ApiParam("角色id，必填项") @NotNull(message = "角色id，roleId不能为null") @RequestParam("roleId") Long roleId) {
        return Response.success(menuService.getTreeSelectByRoleId(roleId));
    }

    /**
     * 分配角色
     *
     * @param roleId  角色id
     * @param menuIds 菜单id列表
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("分配角色菜单")
    @PutMapping("/assignMenu")
    public Response<Void> assignRole(@ApiParam("角色id，必填项") @NotNull(message = "角色id，roleId不能为null") @RequestParam("roleId") Long roleId, @ApiParam("菜单id列表，必填项") @NotEmpty(message = "菜单id列表，menuIds不能为null") @RequestBody List<Long> menuIds) {
        menuService.assignMenu(roleId, menuIds);
        return Response.success();
    }

    /**
     * 改变状态
     *
     * @param menuId 菜单id
     * @param status 状态
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("启用或禁用菜单")
    @PutMapping("/changeStatus")
    public Response<Void> changeStatus(@ApiParam("菜单id，必填项") @NotNull(message = "菜单id，menuId不能为null") @RequestParam("menuId") Long menuId, @ApiParam("菜单状态，必填项") @NotNull(message = "菜单状态，status不能为null") @RequestParam("status") Boolean status) {
        menuService.changeStatus(menuId, status);
        return Response.success();
    }

}
