package com.pandama.top.app.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.pandama.top.app.pojo.AlgorithmGisVO;
import com.pandama.top.app.pojo.Attribute;
import com.pandama.top.app.pojo.Geometry;
import com.pandama.top.core.global.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * 设备控制器
 *
 * @author 王强
 * @date 2023-07-08 11:55:46
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/coordinate")
public class CoordinateController {

    @RequestMapping(value = "/start2", method = RequestMethod.POST)
    public Response<?> start2(@RequestBody List<AlgorithmGisVO> list) {
        GeometryFactory geometryFactory = new GeometryFactory();
        list.forEach(json -> {
            String idStr = IdWorker.getIdStr();
            Attribute att = json.getAttributes();
            String sqlFormat = "insert into jx_sys_dma_management (dma_id, superior_region, partition_level, partition_number," +
                    " partition_name, color_fill, transparency, border_show, area_id, pids, form_company, water_mode) " +
                    "values (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s);";
            String sql1 = String.format(sqlFormat, idStr, "428738420446609408", 2, "\"" + att.get编号() + "\"", "\"" + att.get名称() + "\"", "\"rgba(73, 150, 217, 0.36)\"", null, null, "408472943594061824", "\"0,428738420446609408\"", "91", "\"水厂\"");
            Geometry geometry = json.getGeometry();
            List<List<BigDecimal>> floats = geometry.getRings().get(0);
            Coordinate[] coordinates = floats.stream().filter(p -> p.size() >= 2).map(p -> new Coordinate(p.get(0).doubleValue(), p.get(1).doubleValue())).toArray(Coordinate[]::new);
            LinearRing linearRing = geometryFactory.createLinearRing(coordinates);
            // 创建Polygon
            Polygon polygon = geometryFactory.createPolygon(linearRing, null);
            String sqlFormat1 = "insert into jx_sys_dma_coordinate (dma_id, coordinate) values (%s,\"%s\");";
            String sql2 = String.format(sqlFormat1, idStr, polygon.toText());
            System.out.println(sql1);
            System.out.println(sql2);
        });
        return Response.success();
    }

    @RequestMapping(value = "/start3", method = RequestMethod.POST)
    public Response<?> start3(@RequestBody List<AlgorithmGisVO> list) {
        GeometryFactory geometryFactory = new GeometryFactory();
        list.forEach(json -> {
            String idStr = IdWorker.getIdStr();
            Attribute att = json.getAttributes();
            String sqlFormat = "insert into jx_sys_dma_management (dma_id, superior_region, partition_level, partition_number," +
                    " partition_name, color_fill, transparency, border_show, area_id, pids, form_company, water_mode) " +
                    "values (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s);";
            String sql1 = String.format(sqlFormat, idStr, null, 3, "\"" + att.get编号() + "\"", "\"" + att.get名称() + "\"", "\"rgba(46, 146, 158, 0.66)\"", 0.80, 1, "408472943594061824", "\"0,428738420446609408,1722855546886483970\"", "91", "\"水厂\"");
            Geometry geometry = json.getGeometry();
            List<List<BigDecimal>> floats = geometry.getRings().get(0);
            Coordinate[] coordinates = floats.stream().filter(p -> p.size() >= 2).map(p -> new Coordinate(p.get(0).doubleValue(), p.get(1).doubleValue())).toArray(Coordinate[]::new);
            LinearRing linearRing = geometryFactory.createLinearRing(coordinates);
            // 创建Polygon
            Polygon polygon = geometryFactory.createPolygon(linearRing, null);
            String sqlFormat1 = "insert into jx_sys_dma_coordinate (dma_id, coordinate) values (%s,\"%s\");";
            String sql2 = String.format(sqlFormat1, idStr, polygon.toText());
            System.out.println(sql1);
            System.out.println(sql2);
        });
        return Response.success();
    }
}
