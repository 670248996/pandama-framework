package com.pandama.top.logRecord.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 日志类型枚举
 *
 * @author 王强
 * @date 2023-07-08 15:22:45
 */
@Getter
@AllArgsConstructor
public enum LogTypeEnum {
	/**
	 * 登陆日志
	 */
	LOGIN_LOG("1", "登陆日志"),
	/**
	 * 操作日志
	 */
	OPERATE_LOG("2", "操作日志"),
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
