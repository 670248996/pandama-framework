package com.pandama.top.user.service;

import com.pandama.top.core.pojo.vo.PageVO;
import com.pandama.top.user.pojo.dto.OnlineSearchDTO;
import com.pandama.top.user.pojo.vo.OnlineSearchResultVO;

import java.util.List;

/**
 * 在线服务
 *
 * @author 王强
 * @date 2023-07-08 15:56:08
 */
public interface OnlineService {

    /**
     * 页面
     *
     * @param dto 在线信息入参
     * @return com.pandama.top.core.pojo.vo.PageVO<com.pandama.top.user.pojo.vo.OnlineSearchResultVO>
     * @author 王强
     */
    PageVO<OnlineSearchResultVO> page(OnlineSearchDTO dto);

    /**
     * 删除
     *
     * @param ids 在线信息id
     * @author 王强
     */
    void delete(List<Long> ids);
}
