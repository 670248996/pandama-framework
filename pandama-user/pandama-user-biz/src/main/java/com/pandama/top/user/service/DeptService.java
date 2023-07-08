package com.pandama.top.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pandama.top.user.pojo.dto.DeptCreateDTO;
import com.pandama.top.user.pojo.dto.DeptSearchDTO;
import com.pandama.top.user.pojo.dto.DeptUpdateDTO;
import com.pandama.top.user.pojo.vo.DeptDetailResultVO;
import com.pandama.top.user.pojo.vo.DeptSearchResultVO;
import com.pandama.top.user.pojo.vo.DeptTreeVO;
import com.pandama.top.user.entity.SysDept;

import java.util.List;
import java.util.Map;

/**
 * 部门服务
 *
 * @author 王强
 * @date 2023-07-08 15:54:12
 */
public interface DeptService extends IService<SysDept> {

    /**
     * 获取编号
     *
     * @return java.lang.String
     * @author 王强
     */
    String getNo();

    /**
     * 创建
     *
     * @param dto 创建部门传参
     * @return void
     * @author 王强
     */
    void create(DeptCreateDTO dto);

    /**
     * 详情
     *
     * @param id 部门id
     * @return com.pandama.top.user.pojo.vo.DeptDetailResultVO
     * @author 王强
     */
    DeptDetailResultVO detail(Long id);

    /**
     * 更新
     *
     * @param dto 部门信息更新传参
     * @return void
     * @author 王强
     */
    void update(DeptUpdateDTO dto);

    /**
     * 删除
     *
     * @param ids 部门id列表
     * @return void
     * @author 王强
     */
    void delete(List<Long> ids);

    /**
     * 列表
     *
     * @param dto 部门信息传参
     * @return java.util.List<com.pandama.top.user.pojo.vo.DeptSearchResultVO>
     * @author 王强
     */
    List<DeptSearchResultVO> list(DeptSearchDTO dto);

    /**
     * 根据指定用户id列表查询部门信息列表
     *
     * @param userIds 用户id列表
     * @return java.util.List<com.pandama.top.user.pojo.vo.DeptSearchResultVO>
     * @author 王强
     */
    List<DeptSearchResultVO> listByUserIds(List<Long> userIds);

    /**
     * 根据指定用户id列表查询用户部门信息Map
     *
     * @param userIds 用户id列表
     * @return java.util.Map<java.lang.Long, com.pandama.top.user.pojo.vo.DeptSearchResultVO>
     * @author 王强
     */
    Map<Long, DeptSearchResultVO> mapByUserIds(List<Long> userIds);

    /**
     * 树
     *
     * @param dto 部门信息传参
     * @return java.util.List<com.pandama.top.user.pojo.vo.DeptTreeVO>
     * @author 王强
     */
    List<DeptTreeVO> tree(DeptSearchDTO dto);

    /**
     * 获取树通过部门id
     *
     * @param id 父级部门id
     * @return java.util.List<com.pandama.top.user.pojo.vo.DeptTreeVO>
     * @author 王强
     */
    List<DeptTreeVO> getTreeById(Long id);
}
