package com.pandama.top.logRecord.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 系统日志枚举（与字典表对应）
 * @author: 王强
 * @dateTime: 2022-07-29 14:41:20
 */
@Getter
@AllArgsConstructor
public enum SystemLogEnum {
	// 对应系统日志列表页面 功能模块，筛选条件 操作事件 级联条件一级功能模块
	/**
	 * 用户管理
	 */
	LOGIN("101", "登录"),
	/**
	 * 用户管理
	 */
	USER("102", "用户管理"),
	/**
	 * 角色管理
	 */
	ROLE("103", "角色管理"),
	/**
	 * 角色管理
	 */
	POWER("104", "权限管理"),
	/**
	 * 字典管理
	 */
	PARAM("105", "数据字典"),
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
	 * @description: 登录
	 * @author: 王强
	 * @dateTime: 2022-09-13 12:49:53
	 */
	@Getter
	@AllArgsConstructor
	public enum Login {
		// 对应系统日志列表页面 操作事件，筛选条件 操作事件 级联条件二级操作事件
		/**
		 * 登录
		 */
		IN(LOGIN.code + "01", "登录"),
		/**
		 * 登出
		 */
		OUT(LOGIN.code + "02", "登出"),
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
	 * @description: 用户管理
	 * @author: 王强
	 * @dateTime: 2022-09-13 12:49:49
	 */
	@Getter
	@AllArgsConstructor
	public enum User {
		// 对应系统日志列表页面 操作事件，筛选条件 操作事件 级联条件二级操作事件
		/**
		 * 分配角色
		 */
		ASSERT_ROLE(USER.code + "01", "分配角色"),
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
	 * @description: 角色管理
	 * @author: 王强
	 * @dateTime: 2022-09-13 12:49:45
	 */
	@Getter
	@AllArgsConstructor
	public enum Role {
		// 对应系统日志列表页面 操作事件，筛选条件 操作事件 级联条件二级操作事件
		/**
		 * 新增角色
		 */
		INSERT(ROLE.code + "01", "新增角色"),
		/**
		 * 删除角色
		 */
		DELETE(ROLE.code + "02", "删除角色"),
		/**
		 * 编辑角色
		 */
		UPDATE(ROLE.code + "03", "编辑角色"),
		/**
		 * 设置权限
		 */
		ASSERT_POWER(ROLE.code + "04", "设置权限"),
		/**
		 * 配置员工
		 */
		ASSERT_USER(ROLE.code + "05", "配置员工"),
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
	 * @description: 权限管理
	 * @author: 王强
	 * @dateTime: 2022-09-13 12:49:38
	 */
	@Getter
	@AllArgsConstructor
	public enum Power {
		// 对应系统日志列表页面 操作事件，筛选条件 操作事件 级联条件二级操作事件
		/**
		 * 新增权限
		 */
		INSERT(POWER.code + "01", "新增权限"),
		/**
		 * 删除权限
		 */
		DELETE(POWER.code + "02", "删除权限"),
		/**
		 * 修改权限
		 */
		UPDATE(POWER.code + "03", "修改权限"),
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
	 * @description: 字典数据
	 * @author: 王强
	 * @dateTime: 2022-09-13 12:49:38
	 */
	@Getter
	@AllArgsConstructor
	public enum Param {
		// 对应系统日志列表页面 操作事件，筛选条件 操作事件 级联条件二级操作事件
		/**
		 * 新增字典
		 */
		INSERT(PARAM.code + "01", "新增字典"),
		/**
		 * 删除字典
		 */
		DELETE(PARAM.code + "02", "删除字典"),
		/**
		 * 修改字典
		 */
		UPDATE(PARAM.code + "03", "修改字典"),
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
