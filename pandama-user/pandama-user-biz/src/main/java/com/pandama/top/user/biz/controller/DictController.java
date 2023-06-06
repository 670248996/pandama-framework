package com.pandama.top.user.biz.controller;

import com.pandama.top.user.api.pojo.dto.DictCreateDTO;
import com.pandama.top.user.api.pojo.dto.DictSearchDTO;
import com.pandama.top.user.api.pojo.dto.DictUpdateDTO;
import com.pandama.top.user.api.pojo.vo.DictCreateResultVO;
import com.pandama.top.user.api.pojo.vo.DictSearchResultVO;
import com.pandama.top.user.api.pojo.vo.DictTreeVO;
import com.pandama.top.user.biz.entity.SysDict;
import com.pandama.top.user.biz.service.DictService;
import com.pandama.top.global.response.Response;
import com.pandama.top.pojo.vo.PageResultVO;
import com.pandama.top.utils.BeanConvertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 数据字典控制层
 * @author: 白剑民
 * @dateTime: 2023/5/2 20:39
 */
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Api(tags = "部门信息相关接口")
@Validated
@Slf4j
public class DictController {

    private final DictService dictionaryService;

    /**
     * @param dto 创建数据字典传参
     * @description: 创建dict类型
     * @author: 王强
     * @date: 2023-06-06 13:06:06
     * @return: Response<DictCreateResultVO>
     * @version: 1.0
     */
    @ApiOperation("创建数据字典")
    @PostMapping
    public Response<DictCreateResultVO> createDict(@Valid @RequestBody DictCreateDTO dto) {
        return Response.success(dictionaryService.create(dto));
    }

    /**
     * @param dictIds 数据字典主键id
     * @description: 删除dict通过id
     * @author: 王强
     * @date: 2023-06-06 13:06:08
     * @return: Response<Void>
     * @version: 1.0
     */
    @ApiOperation("根据主键id删除字典信息")
    @DeleteMapping
    public Response<Void> deleteDictById(
            @ApiParam("数据字典id列表，必填项")
            @NotEmpty(message = "数据字典id列表，dictIds不能为null")
            @RequestParam List<Long> dictIds) {
        dictionaryService.delete(dictIds);
        return Response.success();
    }

    /**
     * @param dto 数据字典更新传参
     * @description: 更新dict类型
     * @author: 王强
     * @date: 2023-06-06 13:06:12
     * @return: Response<Void>
     * @version: 1.0
     */
    @ApiOperation("更新数据字典")
    @PutMapping
    public Response<Void> update(@Valid @RequestBody DictUpdateDTO dto) {
        dictionaryService.update(dto);
        return Response.success();
    }

    /**
     * @param dictId 数据字典主键id
     * @description: 获取dict通过id
     * @author: 王强
     * @date: 2023-06-06 13:06:17
     * @return: Response<DictInfoVO>
     * @version: 1.0
     */
    @ApiOperation("根据主键id获取字典信息")
    @GetMapping
    public Response<DictSearchResultVO> getDictById(
            @ApiParam("数据字典id，必填项")
            @NotNull(message = "数据字典id，dictId不能为null")
            @RequestParam Long dictId) {
        SysDict dict = dictionaryService.getDictById(dictId);
        DictSearchResultVO dictInfoVO =
                BeanConvertUtils.convert(dict, DictSearchResultVO::new, (s, t) -> {
                            t.setId(s.getId());
                            t.setParentId(s.getParentId());
                        })
                        .orElse(new DictSearchResultVO());
        return Response.success(dictInfoVO);
    }

    /**
     * @param dto 搜索传参
     * @description: 搜索
     * @author: 王强
     * @date: 2023-06-06 13:06:20
     * @return: Response<PageResultVO < DictTreeVO>>
     * @version: 1.0
     */
    @ApiOperation("数据字典搜索")
    @PostMapping("/page")
    public Response<PageResultVO<DictTreeVO>> page(@Valid @RequestBody DictSearchDTO dto) {
        return Response.success(dictionaryService.page(dto));
    }

    /**
     * @description: 获取所有数据字典
     * @author: 白剑民
     * @date: 2023-05-24 16:48:38
     * @return: com.pandama.top.global.response.Response<
            * * java.util.List < com.gientech.iot.user.api.pojo.vo.DictInfoVO>>
     * @version: 1.0
     */
    @ApiOperation("获取所有数据字典")
    @GetMapping("/list")
    public Response<List<DictSearchResultVO>> list(@RequestBody DictSearchDTO dto) {
        return Response.success(dictionaryService.list(dto));
    }

    /**
     * @description: 获取所有数据字典类型
     * @author: 白剑民
     * @date: 2023-05-25 10:40:03
     * @return: com.pandama.top.global.response.Response<
            * * java.util.List < com.gientech.iot.user.api.pojo.vo.DictInfoVO>>
     * @version: 1.0
     */
    @ApiOperation("获取所有数据字典类型")
    @GetMapping("/listType")
    public Response<List<DictSearchResultVO>> listType() {
        return Response.success(dictionaryService.listType());
    }

    /**
     * @param dictType 数据字典类型
     * @description: 获取dict通过类型
     * @author: 王强
     * @date: 2023-06-06 13:06:40
     * @return: Response<List < DictTreeVO>>
     * @version: 1.0
     */
    @ApiOperation("根据数据字典类型获取字典列表")
    @GetMapping("/listByType")
    public Response<List<DictTreeVO>> listByType(
            @ApiParam("数据字典类型，必填项")
            @NotBlank(message = "数据字典类型，dictType不能为null")
            @RequestParam String dictType) {
        return Response.success(dictionaryService.getDictByType(dictType));
    }

    /**
     * @param dictId 数据字典主键id
     * @description: 启用禁用数据字典项
     * @author: 王强
     * @date: 2023-06-06 13:06:48
     * @return: Response<Void>
     * @version: 1.0
     */
    @ApiOperation("启用禁用数据字典项")
    @PutMapping("/changeStatus")
    public Response<Void> changeStatus(
            @ApiParam("数据字典id，必填项")
            @NotNull(message = "数据字典id，dictId不能为null")
            @RequestParam Long dictId) {
        dictionaryService.changeStatus(dictId);
        return Response.success();
    }

}
