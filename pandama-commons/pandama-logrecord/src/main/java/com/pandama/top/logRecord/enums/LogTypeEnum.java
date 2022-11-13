package com.pandama.top.logRecord.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 日志类型枚举
 * @author: 王强
 * @dateTime: 2022-09-15 09:42:08
 */
@Getter
@AllArgsConstructor
public enum LogTypeEnum {
	/**
	 * 系统日志
	 */
	SYSTEM("1", "系统日志"),
	/**
	 * 操作日志
	 */
	BUSINESS("2", "操作日志"),
	/**
	 * 导入记录
	 */
	IMPORT("3", "导入记录"),
    ;

	/**
	 * 代码
	 */
	private final String code;

	/**
	 * 名称
	 */
	private final String name;
}
