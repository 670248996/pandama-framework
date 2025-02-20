package com.pandama.top.app.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AlgorithmGisVO implements Serializable {

    private Attribute attributes;

    private Geometry geometry;
}
