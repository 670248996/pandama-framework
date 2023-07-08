package com.pandama.top.user.service;

import com.pandama.top.user.pojo.dto.DictCreateDTO;
import com.pandama.top.user.pojo.dto.DictSearchDTO;
import com.pandama.top.user.pojo.dto.DictUpdateDTO;
import com.pandama.top.user.pojo.vo.DictDetailResultVO;
import com.pandama.top.user.pojo.vo.DictSearchResultVO;

import java.util.List;

/**
 * 字典服务
 *
 * @author 王强
 * @date 2023-07-08 15:55:14
 */
public interface DictService {

    /**
     * 创建
     *
     * @param dto 创建字典传参
     * @return void
     * @author 王强
     */
    void create(DictCreateDTO dto);

    /**
     * 删除
     *
     * @param dictIds 字典主键id
     * @return void
     * @author 王强
     */
    void delete(List<Long> dictIds);

    /**
     * 更新
     *
     * @param dto 字典更新传参
     * @return void
     * @author 王强
     */
    void update(DictUpdateDTO dto);

    /**
     * 详情
     *
     * @param dictId 字典主键id
     * @return com.pandama.top.user.pojo.vo.DictDetailResultVO
     * @author 王强
     */
    DictDetailResultVO detail(Long dictId);

    /**
     * 列表
     *
     * @param dto 入参
     * @return java.util.List<com.pandama.top.user.pojo.vo.DictSearchResultVO>
     * @author 王强
     */
    List<DictSearchResultVO> list(DictSearchDTO dto);

    /**
     * 改变状态
     *
     * @param dictId 字典主键id
     * @param status 状态
     * @return void
     * @author 王强
     */
    void changeStatus(Long dictId, Boolean status);

}
