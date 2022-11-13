package com.pandama.top.logRecord.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 导入日志枚举（与字典表对应）
 * @author: 王强
 * @dateTime: 2022-07-29 14:41:20
 */
@Getter
@AllArgsConstructor
public enum ImportLogEnum {
	/**
	 * 任务管理
	 */
	MANAGER("301", "任务管理"),
	/**
	 * 任务标注
	 */
	TAGGING("302", "任务标注"),
	;

	/**
	 * 代码
	 */
	private final String code;

	/**
	 * 名称
	 */
	private final String name;

	/**
	 * @description: 任务管理
	 * @author: 王强
	 * @dateTime: 2022-09-19 16:22:07
	 */
	@Getter
	@AllArgsConstructor
	public enum Manager {
		/**
		 * 导入
		 */
		IMPORT(MANAGER.code + "01", "导入"),
		/**
		 * 导出
		 */
		EXPORT(MANAGER.code + "02", "导出"),
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

	/**
	 * @description: 任务标注
	 * @author: 王强
	 * @dateTime: 2022-09-19 16:22:12
	 */
	@Getter
	@AllArgsConstructor
	public enum Tagging {
		/**
		 * 导入
		 */
		IMPORT(MANAGER.code + "01", "导入"),
		/**
		 * 导出
		 */
		EXPORT(MANAGER.code + "02", "导出"),
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
}
