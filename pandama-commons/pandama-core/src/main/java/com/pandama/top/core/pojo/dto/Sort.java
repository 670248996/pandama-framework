package com.pandama.top.core.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 排序
 *
 * @author 王强
 * @date 2024-09-03 12:26:19
 */
@Data
@AllArgsConstructor
public class Sort {

	/**
	 * 排序字段，表的字段名
	 */
	private String orderField;

	/**
	 * 是否反转，正序asc，倒序desc
	 */
	private String orderType;

}
