package com.pandama.top.app.service;

import com.pandama.top.app.pojo.dto.DictionaryListDTO;
import com.pandama.top.app.pojo.vo.DictionaryListVO;
import com.pandama.top.app.pojo.vo.DictionaryTreeVO;

import java.util.List;

/**
 * @description: 字典服务
 * @author: 王强
 * @dateTime: 2023-04-22 12:25:21
 */
public interface DictionaryService {

    /**
     * @description: 树
     * @author: 王强
     * @date: 2023-04-22 12:26:13
     * @return: void
     * @version: 1.0
     */
    List<DictionaryTreeVO> tree();

    /**
     * @description: 树
     * @author: 王强
     * @date: 2023-04-22 12:26:13
     * @return: void
     * @version: 1.0
     */
    List<DictionaryListVO> list(DictionaryListDTO dto);
}
