package com.pandama.top.user.controller;

import com.pandama.top.user.pojo.dto.DeptCreateDTO;
import com.pandama.top.user.pojo.dto.DeptSearchDTO;
import com.pandama.top.user.pojo.dto.DeptUpdateDTO;
import com.pandama.top.user.pojo.vo.DeptDetailResultVO;
import com.pandama.top.user.pojo.vo.DeptSearchResultVO;
import com.pandama.top.user.pojo.vo.DeptTreeVO;
import com.pandama.top.user.service.DeptService;
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
 * 部门信息控制层
 *
 * @author 王强
 * @date 2023-07-08 15:41:56
 */
@RestController
@RequestMapping("/dept")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Api(tags = "部门信息相关接口")
@Validated
@Slf4j
public class DeptController {

    private final DeptService deptService;

    /**
     * 获取编号
     *
     * @return com.pandama.top.core.global.response.Response<java.lang.String>
     * @author 王强
     */
    @ApiOperation("获取部门编号")
    @GetMapping("/getNo")
    public Response<String> getNo() {
        return Response.success(deptService.getNo());
    }

    /**
     * 创建
     *
     * @param dto 创建部门传参
     * @return com.pandama.top.core.global.response.Response<?>
     * @author 王强
     */
    @ApiOperation("创建部门")
    @PostMapping
    public Response<?> create(@Valid @RequestBody DeptCreateDTO dto) {
        deptService.create(dto);
        return Response.success();
    }

    /**
     * 详情
     *
     * @param deptId 部门id
     * @return com.pandama.top.core.global.response.Response<com.pandama.top.user.pojo.vo.DeptDetailResultVO>
     * @author 王强
     */
    @ApiOperation("部门详情")
    @GetMapping
    public Response<DeptDetailResultVO> detail(@ApiParam("部门id，必填项") @NotNull(message = "部门id，deptId不能为null") @RequestParam("deptId") Long deptId) {
        return Response.success(deptService.detail(deptId));
    }

    /**
     * 更新
     *
     * @param dto 部门信息更新传参
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("部门信息更新")
    @PutMapping
    public Response<Void> update(@Valid @RequestBody DeptUpdateDTO dto) {
        deptService.update(dto);
        return Response.success();
    }

    /**
     * 删除
     *
     * @param deptIds 部门id列表
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("删除部门信息")
    @DeleteMapping
    public Response<Void> delete(@ApiParam("部门id列表，必填项") @NotEmpty(message = "部门id列表，deptIds不能为null") @RequestParam List<Long> deptIds) {
        deptService.delete(deptIds);
        return Response.success();
    }

    /**
     * 列表
     *
     * @param dto 查询部门信息传参
     * @return com.pandama.top.core.global.response.Response<java.util.List < com.pandama.top.user.pojo.vo.DeptSearchResultVO>>
     * @author 王强
     */
    @ApiOperation("查询部门信息列表")
    @PostMapping("/list")
    public Response<List<DeptSearchResultVO>> list(@Valid @RequestBody DeptSearchDTO dto) {
        return Response.success(deptService.list(dto));
    }

    /**
     * 树
     *
     * @param dto 查询部门信息传参
     * @return com.pandama.top.core.global.response.Response<java.util.List < com.pandama.top.user.pojo.vo.DeptTreeVO>>
     * @author 王强
     */
    @ApiOperation("查询部门信息树")
    @PostMapping("/tree")
    public Response<List<DeptTreeVO>> tree(@Valid @RequestBody DeptSearchDTO dto) {
        return Response.success(deptService.tree(dto));
    }

    /**
     * 获取树通过部门id
     *
     * @param deptId 部门id
     * @return com.pandama.top.core.global.response.Response<java.util.List < com.pandama.top.user.pojo.vo.DeptTreeVO>>
     * @author 王强
     */
    @ApiOperation("根据父级部门id获取部门树")
    @GetMapping("/getTreeByDeptId")
    public Response<List<DeptTreeVO>> getTreeByDeptId(@ApiParam("部门id，必填项") @NotNull(message = "部门id，deptId不能为null") @RequestParam("deptId") Long deptId) {
        return Response.success(deptService.getTreeByDeptId(deptId));
    }
}
