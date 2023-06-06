package com.pandama.top.app.pojo.vo;

import com.pandama.top.utils.BaseTreeVO;
import lombok.Data;

/**
 * @description: 字典树出参
 * @author: 王强
 * @dateTime: 2023-04-22 12:33:37
 */
@Data
public class DictionaryTreeVO extends BaseTreeVO {
    private String dictType;
    private String dictCode;
    private String dictName;
}
