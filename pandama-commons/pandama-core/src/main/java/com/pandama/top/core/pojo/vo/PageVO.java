package com.pandama.top.core.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面出参
 *
 * @author 王强
 * @date 2023-07-08 15:13:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVO<T> {
    /**
     * 总条数
     */
    private Long total;
    /**
     * 每页显示条数
     */
    private Long size;
    /**
     * 当前页
     */
    private Long current;
    /**
     * 分页总页数
     */
    private Long pages;
    /**
     * 数据列表
     */
    private List<T> records;


    // 以下避免controller层自动将包装类型转换为String类型

    public long getTotal() {
        return this.total == null ? 0 : this.total;
    }

    public long getSize() {
        return this.size == null ? 0 : this.size;
    }

    public long getCurrent() {
        return this.current == null ? 0 : this.current;
    }

    public long getPages() {
        return this.pages == null ? 0 : this.pages;
    }

    public List<T> getRecords() {
        return this.records == null ? new ArrayList<>() : this.records;
    }
}
