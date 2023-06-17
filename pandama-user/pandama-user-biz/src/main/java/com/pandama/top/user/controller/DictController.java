package com.pandama.top.user.controller;

import com.pandama.top.user.pojo.dto.DictCreateDTO;
import com.pandama.top.user.pojo.dto.DictSearchDTO;
import com.pandama.top.user.pojo.dto.DictUpdateDTO;
import com.pandama.top.user.pojo.vo.DictDetailResultVO;
import com.pandama.top.user.pojo.vo.DictSearchResultVO;
import com.pandama.top.user.service.DictService;
import com.pandama.top.core.global.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 字典控制层
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

    private final DictService dictService;

    /**
     * @param dto 创建字典传参
     * @description: 创建dict类型
     * @author: 王强
     * @date: 2023-06-06 13:06:06
     * @return: Response<DictCreateResultVO>
     * @version: 1.0
     */
    @ApiOperation("创建字典")
    @PostMapping
    public Response<?> create(@Valid @RequestBody DictCreateDTO dto) {
        dictService.create(dto);
        return Response.success();
    }

    /**
     * @param dictIds 字典主键id
     * @description: 删除dict通过id
     * @author: 王强
     * @date: 2023-06-06 13:06:08
     * @return: Response<Void>
     * @version: 1.0
     */
    @ApiOperation("根据主键id删除字典信息")
    @DeleteMapping
    public Response<Void> delete(@ApiParam("字典id列表，必填项") @NotEmpty(message = "字典id列表，dictIds不能为null") @RequestParam List<Long> dictIds) {
        dictService.delete(dictIds);
        return Response.success();
    }

    /**
     * @param dto 字典更新传参
     * @description: 更新dict类型
     * @author: 王强
     * @date: 2023-06-06 13:06:12
     * @return: Response<Void>
     * @version: 1.0
     */
    @ApiOperation("更新字典")
    @PutMapping
    public Response<Void> update(@Valid @RequestBody DictUpdateDTO dto) {
        dictService.update(dto);
        return Response.success();
    }

    /**
     * @param dictId 字典主键id
     * @description: 获取dict通过id
     * @author: 王强
     * @date: 2023-06-06 13:06:17
     * @return: Response<DictInfoVO>
     * @version: 1.0
     */
    @ApiOperation("根据主键id获取字典信息")
    @GetMapping
    public Response<DictDetailResultVO> detail(@ApiParam("字典id，必填项") @NotNull(message = "字典id，dictId不能为null") @RequestParam Long dictId) {
        DictDetailResultVO detail = dictService.detail(dictId);
        return Response.success(detail);
    }

    /**
     * @param dto 入参
     * @description: 获取字典列表
     * @author: 王强
     * @date: 2023-06-16 15:15:17
     * @return: Response<List < DictSearchResultVO>>
     * @version: 1.0
     */
    @ApiOperation("获取所有字典")
    @PostMapping("/list")
    public Response<List<DictSearchResultVO>> list(@RequestBody DictSearchDTO dto) {
        return Response.success(dictService.list(dto));
    }

    /**
     * @param dictId 字典主键id
     * @description: 启用禁用字典项
     * @author: 王强
     * @date: 2023-06-06 13:06:48
     * @return: Response<Void>
     * @version: 1.0
     */
    @ApiOperation("启用禁用字典项")
    @PutMapping("/changeStatus")
    public Response<Void> changeStatus(@ApiParam("字典id，必填项") @NotNull(message = "字典id，dictId不能为null") @RequestParam Long dictId, @ApiParam("启用(true)或禁用(false)，必填项") @NotNull(message = "启用或禁用，status不能为null") @RequestParam("status") Boolean status) {
        dictService.changeStatus(dictId, status);
        return Response.success();
    }
}
