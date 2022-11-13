package com.pandama.top.logRecord.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 操作日志枚举（与字典表对应）
 * @author: 王强
 * @dateTime: 2022-07-29 14:41:20
 */
@Getter
@AllArgsConstructor
public enum OperationLogEnum {
	// 对应操作日志列表页面 功能模块
	/**
	 * 任务管理
	 */
	MANAGER("201", "任务管理"),
	/**
	 * 任务标注
	 */
	TAGGING("202", "任务标注"),
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
	 * @dateTime: 2022-09-13 12:49:14
	 */
	@Getter
	@AllArgsConstructor
	public enum Manager {
		// 对应任务管理日志列表页面 操作事件，筛选条件 操作事件
		/**
		 * 创建任务
		 */
		CREATE_TASK(MANAGER.code + "01", "创建任务"),
		/**
		 * 编辑任务要求
		 */
		UPDATE_REQUIRE(MANAGER.code + "02", "编辑任务要求"),
		/**
		 * 新增标注配置
		 */
		INSERT_CONFIG(MANAGER.code + "03", "新增标注配置"),
		/**
		 * 删除标注配置
		 */
		DELETE_CONFIG(MANAGER.code + "04", "删除标注配置"),
		/**
		 * 编辑标注配置
		 */
		UPDATE_CONFIG(MANAGER.code + "05", "编辑标注配置"),
		/**
		 * 广场可见
		 */
		VISIBLE(MANAGER.code + "06", "广场可见"),
		/**
		 * 关闭任务
		 */
		CLOSE_TASK(MANAGER.code + "07", "关闭任务"),
		/**
		 * 追加任务
		 */
		APPEND_TASK(MANAGER.code + "08", "追加任务"),
		/**
		 * 分配任务
		 */
		DISTRIBUTE(MANAGER.code + "09", "分配任务"),
		/**
		 * 手动分配
		 */
		HANDLER(MANAGER.code + "10", "手动分配"),
		/**
		 * 导出列表数据
		 */
		EXPORT(MANAGER.code + "11", "导出列表数据"),
		/**
		 * 下载标注结果
		 */
		DOWNLOAD(MANAGER.code + "12", "下载标注结果"),
		/**
		 * 标注任务
		 */
		TAGGING_TASK(MANAGER.code + "13", "标注任务"),
		/**
		 * 新增批注
		 */
		ANNOTATION(MANAGER.code + "14", "新增批注"),
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
	 * @dateTime: 2022-09-13 12:49:22
	 */
	@Getter
	@AllArgsConstructor
	public enum Tagging {
		// 对应任务标注日志列表页面 操作事件，筛选条件 操作事件
		/**
		 * 编辑任务要求
		 */
		UPDATE_REQUIRE(TAGGING.code + "01", "编辑任务要求"),
		/**
		 * 新增标注配置
		 */
		INSERT_CONFIG(TAGGING.code + "02", "新增标注配置"),
		/**
		 * 删除标注配置
		 */
		DELETE_CONFIG(TAGGING.code + "03", "删除标注配置"),
		/**
		 * 编辑标注配置
		 */
		UPDATE_CONFIG(TAGGING.code + "04", "编辑标注配置"),
		/**
		 * 导出列表数据
		 */
		EXPORT(TAGGING.code + "05", "导出列表数据"),
		/**
		 * 下载标注结果
		 */
		DOWNLOAD(TAGGING.code + "06", "下载标注结果"),
		/**
		 * 标注任务
		 */
		TAGGING_TASK(TAGGING.code + "07", "标注任务"),
		/**
		 * 领取任务
		 */
		RECEIVE_TASK(TAGGING.code + "08", "领取任务"),
		/**
		 * 新增备注
		 */
		REMARK(TAGGING.code + "09", "新增备注"),
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
