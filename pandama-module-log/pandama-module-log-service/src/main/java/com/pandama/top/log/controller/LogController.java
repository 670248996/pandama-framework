package com.pandama.top.log.controller;

import com.pandama.top.core.global.response.Response;
import com.pandama.top.core.pojo.vo.PageVO;
import com.pandama.top.logRecord.enums.LogTypeEnum;
import com.pandama.top.log.pojo.dto.LogSearchDTO;
import com.pandama.top.log.service.LogService;
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
 * 日志控制器
 *
 * @author 王强
 * @date 2023-07-08 15:43:08
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
     * 页面
     *
     * @param logType 日志类型
     * @param dto     日志搜索传参
     * @return com.pandama.top.core.global.response.Response<com.pandama.top.core.pojo.vo.PageVO < ?>>
     * @author 王强
     */
    @ApiOperation("日志搜索")
    @PostMapping("/page")
    public Response<PageVO<?>> page(@PathVariable("logType") LogTypeEnum logType, @Valid @RequestBody LogSearchDTO dto) {
        dto.setLogType(logType);
        return Response.success(logService.page(dto));
    }

    /**
     * 删除
     *
     * @param logType 日志类型
     * @param ids  日志id
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("日志删除")
    @DeleteMapping
    public Response<Void> delete(@PathVariable("logType") LogTypeEnum logType,
                                 @ApiParam("日志id列表，必填项") @NotEmpty(message = "日志id列表，ids不能为null") @RequestParam("ids") List<Long> ids) {
        logService.delete(ids);
        return Response.success();
    }

    /**
     * 导出
     *
     * @param logType 日志类型
     * @param dto     日志导出传参
     * @author 王强
     */
    @ApiOperation("日志导出")
    @PostMapping("/export")
    public void export(@PathVariable("logType") LogTypeEnum logType, @Valid @RequestBody LogSearchDTO dto) {
        dto.setLogType(logType);
        logService.exportExcel(dto);
    }

    /**
     * 清除
     *
     * @param logType 日志类型
     * @return com.pandama.top.core.global.response.Response<?>
     * @author 王强
     */
    @ApiOperation("清空日志")
    @DeleteMapping("/clean")
    public Response<?> clean(@PathVariable("logType") LogTypeEnum logType) {
        logService.clean(logType);
        return Response.success();
    }

}
