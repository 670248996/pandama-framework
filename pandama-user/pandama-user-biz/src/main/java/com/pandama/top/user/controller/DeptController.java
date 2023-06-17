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
 * @description: 部门信息控制层
 * @author: 白剑民
 * @dateTime: 2022/10/21 17:03
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
     * @description: 获取部门编号
     * @author: 王强
     * @date: 2023-05-23 20:38:09
     * @return: Response<String>
     * @version: 1.0
     */
    @ApiOperation("获取部门编号")
    @GetMapping("/getNo")
    public Response<String> getNo() {
        return Response.success(deptService.getNo());
    }

    /**
     * @param dto 创建部门传参
     * @description: 创建部门
     * @author: 白剑民
     * @date: 2022-10-24 17:39:34
     * @return: com.gientech.iot.global.response.Response<
            * * com.gientech.iot.user.entity.vo.DepartmentCreateResultVO>
     * @version: 1.0
     */
    @ApiOperation("创建部门")
    @PostMapping
    public Response<?> create(@Valid @RequestBody DeptCreateDTO dto) {
        deptService.create(dto);
        return Response.success();
    }

    /**
     * @param deptId 部门id
     * @description: 部门详情
     * @author: 王强
     * @date: 2023-05-23 14:05:09
     * @return: Response<PageResultVO < DepartmentSearchResultVO>>
     * @version: 1.0
     */
    @ApiOperation("部门详情")
    @GetMapping
    public Response<DeptDetailResultVO> detail(@ApiParam("部门id，必填项") @NotNull(message = "部门id，deptId不能为null") @RequestParam("deptId") Long deptId) {
        return Response.success(deptService.detail(deptId));
    }

    /**
     * @param dto 部门信息更新传参
     * @description: 部门信息更新
     * @author: 白剑民
     * @date: 2022-10-25 09:49:31
     * @return: com.gientech.iot.global.response.Response<java.lang.Void>
     * @version: 1.0
     */
    @ApiOperation("部门信息更新")
    @PutMapping
    public Response<Void> update(@Valid @RequestBody DeptUpdateDTO dto) {
        deptService.update(dto);
        return Response.success();
    }

    /**
     * @param deptIds 部门id列表
     * @description: 删除部门信息
     * @author: 白剑民
     * @date: 2022-10-26 14:27:00
     * @return: com.gientech.iot.global.response.Response<java.lang.Void>
     * @version: 1.0
     */
    @ApiOperation("删除部门信息")
    @DeleteMapping
    public Response<Void> delete(@ApiParam("部门id列表，必填项") @NotEmpty(message = "部门id列表，deptIds不能为null") @RequestParam List<Long> deptIds) {
        deptService.delete(deptIds);
        return Response.success();
    }

    /**
     * @param dto 查询部门信息传参
     * @description: 查询部门信息列表
     * @author: 王强
     * @date: 2023-05-23 14:05:09
     * @return: Response<PageResultVO < DepartmentSearchResultVO>>
     * @version: 1.0
     */
    @ApiOperation("查询部门信息列表")
    @PostMapping("/list")
    public Response<List<DeptSearchResultVO>> list(@Valid @RequestBody DeptSearchDTO dto) {
        return Response.success(deptService.list(dto));
    }

    /**
     * @param dto 查询部门信息传参
     * @description: 查询部门信息树
     * @author: 王强
     * @date: 2023-05-23 14:05:09
     * @return: Response<PageResultVO < DepartmentSearchResultVO>>
     * @version: 1.0
     */
    @ApiOperation("查询部门信息树")
    @PostMapping("/tree")
    public Response<List<DeptTreeVO>> tree(@Valid @RequestBody DeptSearchDTO dto) {
        return Response.success(deptService.tree(dto));
    }

    /**
     * @param deptId 部门id
     * @description: 根据父级部门id获取部门树
     * @author: 白剑民
     * @date: 2022-10-24 16:31:48
     * @return: com.gientech.iot.global.response.Response<
            * java.util.List < com.gientech.iot.user.entity.vo.DepartmentTreeVO>>
     * @version: 1.0
     */
    @ApiOperation("根据父级部门id获取部门树")
    @GetMapping("/getTreeByDeptId")
    public Response<List<DeptTreeVO>> getTreeByDeptId(@ApiParam("部门id，必填项") @NotNull(message = "部门id，deptId不能为null") @RequestParam("deptId") Long deptId) {
        return Response.success(deptService.getTreeByDeptId(deptId));
    }
}
