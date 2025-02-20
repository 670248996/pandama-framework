package com.pandama.top.app.pojo;

import lombok.Data;

@Data
public class Attribute {
    private String 名称;
    private String 编号;
    private String 进水管;

    public String get编号() {
        return "YX001" + 编号;
    }
}
