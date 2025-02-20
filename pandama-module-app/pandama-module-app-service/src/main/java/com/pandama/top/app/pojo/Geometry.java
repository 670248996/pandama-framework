package com.pandama.top.app.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Geometry {
    private List<List<List<BigDecimal>>> rings;
}
