package com.pandama.top.logRecord.bean;

import lombok.Data;

import java.util.List;

/**
 * 发生变化的数据信息dto
 *
 * @author 王强
 * @date 2023-07-08 15:21:11
 */
@Data
public class DiffDTO {

    /**
     * 实体类名
     */
    private String oldClassName;

    /**
     * 实体类别名
     */
    private String oldClassAlias;

    /**
     * 实体类名
     */
    private String newClassName;

    /**
     * 实体类别名
     */
    private String newClassAlias;

    /**
     * 发生变化的字段信息列表
     */
    private List<DiffFieldDTO> diffFieldDTOList;
}
