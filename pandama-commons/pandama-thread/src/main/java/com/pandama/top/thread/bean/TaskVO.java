package com.pandama.top.thread.bean;

import lombok.Data;

/**
 * 线程执行任务对象
 *
 * @author 王强
 * @date 2023-07-08 15:35:47
 */
@Data
public class TaskVO {
    /**
     * 对象索引
     */
    private Integer index;
    /**
     * 数据实体
     */
    private Object data;

    /**
     * 判断对象为空，作为线程终止的信号量
     *
     * @return boolean
     * @author 王强
     */
    public boolean isEmpty() {
        return index == null && data == null;
    }
}
