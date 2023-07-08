package com.pandama.top.logRecord.bean;


import com.pandama.top.rocketmq.pojo.BaseMessage;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 日志入参
 *
 * @author 王强
 * @date 2023-07-08 15:21:20
 */
@Data
public class LogDTO extends BaseMessage {
	/**
	 * 业务ID
	 */
	private String bizId;
	/**
	 * 业务名称
	 */
	private String bizName;
	/**
	 * 业务类型
	 */
	private String bizType;
	/**
	 * 业务事件
	 */
	private String bizEvent;
	/**
	 * 方法异常信息
	 */
	private String exception;
	/**
	 * 日志操作时间
	 */
	private Date operateDate;
	/**
	 * 方法是否成功
	 */
	private Boolean success;
	/**
	 * 日志内容
	 */
	private String msg;
	/**
	 * 日志标签
	 */
	private String tag;
	/**
	 * 方法结果
	 */
	private String returnStr;
	/**
	 * 方法执行时间（单位：毫秒）
	 */
	private Long executionTime;
	/**
	 * 额外信息
	 */
	private String extra;

	/**
	 * 操作人ID
	 */
	private Long operatorId;
	/**
	 * 操作人编号
	 */
	private String operatorCode;
	/**
	 * 操作人姓名
	 */
	private String operatorName;
	/**
	 * ip地址
	 */
	private String ipAddress;
	/**
	 * 操作系统
	 */
	private String os;
	/**
	 * 浏览器
	 */
	private String browser;
	/**
	 * 实体DIFF列表
	 */
	private List<DiffDTO> diffDTOList;

}