package com.pandama.top.user.biz.service;

import com.pandama.top.pojo.vo.PageResultVO;
import com.pandama.top.user.api.pojo.dto.DictCreateDTO;
import com.pandama.top.user.api.pojo.dto.DictSearchDTO;
import com.pandama.top.user.api.pojo.dto.DictUpdateDTO;
import com.pandama.top.user.api.pojo.vo.DictCreateResultVO;
import com.pandama.top.user.api.pojo.vo.DictSearchResultVO;
import com.pandama.top.user.api.pojo.vo.DictTreeVO;
import com.pandama.top.user.biz.entity.SysDict;

import java.util.List;

/**
 * @description: 数据字典接口类
 * @author: 白剑民
 * @dateTime: 2023/5/2 20:38
 */
public interface DictService {

    /**
     * @param dto 创建数据字典传参
     * @description: 创建数据字典
     * @author: 白剑民
     * @date: 2023-05-20 21:08:53
     * @return: com.gientech.iot.user.api.pojo.vo.DictCreateResultVO
     * @version: 1.0
     */
    DictCreateResultVO create(DictCreateDTO dto);

    /**
     * @param dictIds 字典主键id
     * @description: 根据主键id删除字典
     * @author: 白剑民
     * @date: 2023-05-22 09:31:46
     * @version: 1.0
     */
    void delete(List<Long> dictIds);

    /**
     * @param dto 数据字典更新传参
     * @description: 更新数据字典
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
    SysDict getDictById(Long dictId);

    /**
     * @param dto 搜索传参
     * @description: 数据字典搜索
     * @author: 白剑民
     * @date: 2023-05-22 13:45:52
     * @return: com.pandama.top.pojo.vo.PageResultVO<com.gientech.iot.user.api.pojo.vo.DictTreeVO>
     * @version: 1.0
     */
    PageResultVO<DictTreeVO> page(DictSearchDTO dto);

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
     * @param dictType 数据字典类型
     * @description: 根据数据字典类型获取字典列表
     * @author: 白剑民
     * @date: 2023-05-22 14:58:20
     * @return: java.util.List<com.gientech.iot.user.api.pojo.vo.DictTreeVO>
     * @version: 1.0
     */
    List<DictTreeVO> getDictByType(String dictType);

    /**
     * @description: 获取所有字典类型
     * @author: 白剑民
     * @date: 2023-05-25 10:29:56
     * @return: java.util.List<com.gientech.iot.user.api.pojo.vo.DictInfoVO>
     * @version: 1.0
     */
    List<DictSearchResultVO> listType();

    /**
     * @param dictId 数据字典主键id
     * @description: 启用禁用数据字典项
     * @author: 白剑民
     * @date: 2023-05-25 22:56:04
     * @version: 1.0
     */
    void changeStatus(Long dictId);

}
