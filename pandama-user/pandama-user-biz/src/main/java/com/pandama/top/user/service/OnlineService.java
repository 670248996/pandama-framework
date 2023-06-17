package com.pandama.top.user.service;

import com.pandama.top.core.pojo.vo.PageVO;
import com.pandama.top.user.pojo.dto.OnlineSearchDTO;
import com.pandama.top.user.pojo.vo.OnlineSearchResultVO;

import java.util.List;

/**
 * @description: 用户信息接口类
 * @author: 白剑民
 * @dateTime: 2022/10/17 17:37
 */
public interface OnlineService {

    /**
     * @param dto 在线信息入参
     * @description: 在线信息分页
     * @author: 王强
     * @date: 2023-05-31 13:15:45
     * @return: PageResultVO<OnlineSearchResultVO>
     * @version: 1.0
     */
    PageVO<OnlineSearchResultVO> page(OnlineSearchDTO dto);

    /**
     * @param onlineIds 在线信息id
     * @description: 强制退出
     * @author: 王强
     * @date: 2023-05-31 13:15:46
     * @return: void
     * @version: 1.0
     */
    void delete(List<Long> onlineIds);
}
