package com.pandama.top.thread.vo;

import lombok.Data;

/**
 * @description: 线程执行任务对象
 * @author: 白剑民
 * @dateTime: 2022/6/29 09:54
 */
@Data
public class TaskVo {
    /**
     * 对象索引
     */
    private Integer index;
    /**
     * 数据实体
     */
    private Object data;

    /**
     * @description: 判断对象为空，作为线程终止的信号量
     * @author: 白剑民
     * @date: 2022-07-03 15:03:32
     * @return: boolean
     * @version: 1.0
     */
    public boolean isEmpty() {
        return index == null && data == null;
    }
}
