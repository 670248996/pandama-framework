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
 * 字典控制器
 *
 * @author 王强
 * @date 2023-07-08 15:42:53
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
     * 创建
     *
     * @param dto 创建字典传参
     * @return com.pandama.top.core.global.response.Response<?>
     * @author 王强
     */
    @ApiOperation("创建字典")
    @PostMapping
    public Response<?> create(@Valid @RequestBody DictCreateDTO dto) {
        dictService.create(dto);
        return Response.success();
    }

    /**
     * 删除
     *
     * @param ids 字典主键id
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("根据主键id删除字典信息")
    @DeleteMapping
    public Response<Void> delete(@ApiParam("字典id列表，必填项") @NotEmpty(message = "字典id列表，ids不能为null") @RequestParam List<Long> ids) {
        dictService.delete(ids);
        return Response.success();
    }

    /**
     * 更新
     *
     * @param dto 字典更新传参
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("更新字典")
    @PutMapping
    public Response<Void> update(@Valid @RequestBody DictUpdateDTO dto) {
        dictService.update(dto);
        return Response.success();
    }

    /**
     * 详情
     *
     * @param id 字典主键id
     * @return com.pandama.top.core.global.response.Response<com.pandama.top.user.pojo.vo.DictDetailResultVO>
     * @author 王强
     */
    @ApiOperation("根据主键id获取字典信息")
    @GetMapping
    public Response<DictDetailResultVO> detail(@ApiParam("字典id，必填项") @NotNull(message = "字典id，id不能为null") @RequestParam Long id) {
        DictDetailResultVO detail = dictService.detail(id);
        return Response.success(detail);
    }

    /**
     * 列表
     *
     * @param dto 入参
     * @return com.pandama.top.core.global.response.Response<java.util.List < com.pandama.top.user.pojo.vo.DictSearchResultVO>>
     * @author 王强
     */
    @ApiOperation("获取所有字典")
    @PostMapping("/list")
    public Response<List<DictSearchResultVO>> list(@RequestBody DictSearchDTO dto) {
        return Response.success(dictService.list(dto));
    }

    /**
     * 改变状态
     *
     * @param id 字典主键id
     * @param status 状态
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("启用禁用字典项")
    @PutMapping("/changeStatus")
    public Response<Void> changeStatus(@ApiParam("字典id，必填项") @NotNull(message = "字典id，id不能为null") @RequestParam Long id,
                                       @ApiParam("启用(true)或禁用(false)，必填项") @NotNull(message = "启用或禁用，status不能为null") @RequestParam("status") Boolean status) {
        dictService.changeStatus(id, status);
        return Response.success();
    }
}
