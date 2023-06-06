package com.pandama.top.user.biz.controller;

import com.pandama.top.user.api.pojo.dto.LogSearchDTO;
import com.pandama.top.user.biz.service.LogService;
import com.pandama.top.global.response.Response;
import com.pandama.top.logRecord.enums.LogTypeEnum;
import com.pandama.top.pojo.vo.PageResultVO;
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
import java.util.List;

/**
 * @description: 日志信息控制层
 * @author: 白剑民
 * @dateTime: 2022/11/18 15:06
 */
@RestController
@RequestMapping("log/{logType}")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Validated
@Slf4j
@Api(tags = "系统登陆志相关接口")
public class LogController {

    private final LogService logService;

    /**
     * @param logType 日志类型
     * @param dto     日志搜索传参
     * @description: 日志搜索
     * @author: 王强
     * @date: 2023-05-29 17:11:23
     * @return: Response<PageResultVO < LogSearchResultVO>>
     * @version: 1.0
     */
    @ApiOperation("日志搜索")
    @PostMapping("/page")
    public Response<PageResultVO<?>> page(@PathVariable("logType") LogTypeEnum logType, @Valid @RequestBody LogSearchDTO dto) {
        dto.setLogType(logType);
        return Response.success(logService.page(dto));
    }

    /**
     * @param logIds  日志id
     * @description: 日志删除
     * @author: 王强
     * @date: 2023-05-29 17:12:24
     * @return: Response<Void>
     * @version: 1.0
     */
    @ApiOperation("日志删除")
    @DeleteMapping
    public Response<Void> delete(
            @ApiParam("日志id列表，必填项")
            @NotEmpty(message = "日志id列表，logIds不能为null")
            @RequestParam("logIds") List<Long> logIds) {
        logService.delete(logIds);
        return Response.success();
    }

    /**
     * @param logType 日志类型
     * @param dto     日志导出传参
     * @description: 日志导出
     * @author: 王强
     * @date: 2023-05-29 17:12:32
     * @return: Response<Void>
     * @version: 1.0
     */
    @ApiOperation("日志导出")
    @PostMapping("/export")
    public void export(@PathVariable("logType") LogTypeEnum logType, @Valid @RequestBody LogSearchDTO dto) {
        dto.setLogType(logType);
        logService.exportExcel(dto);
    }

    /**
     * @param logType 日志类型
     * @description: 清空日志
     * @author: 王强
     * @date: 2023-05-29 17:12:51
     * @return: Response<?>
     * @version: 1.0
     */
    @ApiOperation("清空日志")
    @DeleteMapping("/clean")
    public Response<?> clean(@PathVariable("logType") LogTypeEnum logType) {
        logService.clean(logType);
        return Response.success();
    }

}
