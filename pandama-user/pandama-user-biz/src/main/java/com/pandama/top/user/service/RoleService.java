package com.pandama.top.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pandama.top.core.pojo.vo.PageVO;
import com.pandama.top.user.pojo.dto.*;
import com.pandama.top.user.pojo.vo.RoleAuthUserVO;
import com.pandama.top.user.pojo.vo.RoleDetailResultVO;
import com.pandama.top.user.pojo.vo.RoleSearchResultVO;
import com.pandama.top.user.entity.SysRole;

import java.util.List;

/**
 * 角色服务
 *
 * @author 王强
 * @date 2023-07-08 15:56:26
 */
public interface RoleService extends IService<SysRole> {

    /**
     * 创建
     *
     * @param dto 创建角色传参
     * @return void
     * @author 王强
     */
    void create(RoleCreateDTO dto);

    /**
     * 详情
     *
     * @param id 角色id
     * @return com.pandama.top.user.pojo.vo.RoleDetailResultVO
     * @author 王强
     */
    RoleDetailResultVO detail(Long id);

    /**
     * 更新
     *
     * @param dto 角色信息更新传参
     * @return void
     * @author 王强
     */
    void update(RoleUpdateDTO dto);

    /**
     * 删除
     *
     * @param ids 角色id列表
     * @return void
     * @author 王强
     */
    void delete(List<Long> ids);

    /**
     * 列表
     *
     * @param dto 入参
     * @return java.util.List<com.pandama.top.user.pojo.vo.RoleSearchResultVO>
     * @author 王强
     */
    List<RoleSearchResultVO> list(RoleSearchDTO dto);

    /**
     * 页面
     *
     * @param dto 入参
     * @return com.pandama.top.core.pojo.vo.PageVO<com.pandama.top.user.pojo.vo.RoleSearchResultVO>
     * @author 王强
     */
    PageVO<RoleSearchResultVO> page(RoleSearchDTO dto);

    /**
     * 通过用户id列表
     *
     * @param userId 用户id
     * @return java.util.List<com.pandama.top.user.entity.SysRole>
     * @author 王强
     */
    List<SysRole> listByUserId(Long userId);

    /**
     * 角色授权用户信息列表
     *
     * @param dto 入参
     * @return com.pandama.top.core.pojo.vo.PageVO<com.pandama.top.user.pojo.vo.RoleAuthUserVO>
     * @author 王强
     */
    PageVO<RoleAuthUserVO> authUserPage(RoleAuthUserSearchDTO dto);

    /**
     * 角色授权用户信息列表
     *
     * @param dto 入参
     * @return com.pandama.top.core.pojo.vo.PageVO<com.pandama.top.user.pojo.vo.RoleAuthUserVO>
     * @author 王强
     */
    PageVO<RoleAuthUserVO> unAuthUserPage(RoleAuthUserSearchDTO dto);

    /**
     * 角色授权用户信息列表
     *
     * @param dto 入参
     * @return void
     * @author 王强
     */
    void authUserCancel(RoleAuthUserCancelDTO dto);

    /**
     * 角色授权用户
     *
     * @param dto 入参
     * @return void
     * @author 王强
     */
    void authUserConfirm(RoleAuthUserConfirmDTO dto);

    /**
     * 改变状态
     *
     * @param id 角色id
     * @param status 启用或禁用
     * @return void
     * @author 王强
     */
    void changeStatus(Long id, Boolean status);
}
