package com.pandama.top.user.service;

import com.pandama.top.user.pojo.dto.DictCreateDTO;
import com.pandama.top.user.pojo.dto.DictSearchDTO;
import com.pandama.top.user.pojo.dto.DictUpdateDTO;
import com.pandama.top.user.pojo.vo.DictDetailResultVO;
import com.pandama.top.user.pojo.vo.DictSearchResultVO;

import java.util.List;

/**
 * @description: 字典接口类
 * @author: 白剑民
 * @dateTime: 2023/5/2 20:38
 */
public interface DictService {

    /**
     * @param dto 创建字典传参
     * @description: 创建字典
     * @author: 白剑民
     * @date: 2023-05-20 21:08:53
     * @return: com.gientech.iot.user.api.pojo.vo.DictCreateResultVO
     * @version: 1.0
     */
    void create(DictCreateDTO dto);

    /**
     * @param dictIds 字典主键id
     * @description: 根据主键id删除字典
     * @author: 白剑民
     * @date: 2023-05-22 09:31:46
     * @version: 1.0
     */
    void delete(List<Long> dictIds);

    /**
     * @param dto 字典更新传参
     * @description: 更新字典
     * @author: 白剑民
     * @date: 2023-05-22 11:04:12
     * @version: 1.0
     */
    void update(DictUpdateDTO dto);

    /**
     * @param dictId 字典主键id
     * @description: 根据主键id获取字典信息
     * @author: 白剑民
     * @date: 2023-05-20 21:19:00
     * @return: com.gientech.iot.user.biz.entity.SysDictionary
     * @version: 1.0
     */
    DictDetailResultVO detail(Long dictId);

    /**
     * @param dto 入参
     * @description: 列表
     * @author: 王强
     * @date: 2023-06-06 13:15:25
     * @return: List<DictInfoVO>
     * @version: 1.0
     */
    List<DictSearchResultVO> list(DictSearchDTO dto);

    /**
     * @param dictId 字典主键id
     * @param status 状态
     * @description: 启用禁用字典项
     * @author: 王强
     * @date: 2023-06-16 14:40:29
     * @return: void
     * @version: 1.0
     */
    void changeStatus(Long dictId, Boolean status);

}
