package com.pandama.top.user.controller;

import com.pandama.top.core.global.response.Response;
import com.pandama.top.core.pojo.vo.PageVO;
import com.pandama.top.user.pojo.dto.OnlineSearchDTO;
import com.pandama.top.user.pojo.vo.OnlineSearchResultVO;
import com.pandama.top.user.service.OnlineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 在线用户信息控制层
 *
 * @author 王强
 * @date 2023-07-08 15:44:32
 */
@RestController
@RequestMapping("/online")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Api(tags = "在线用户信息相关接口")
@Validated
@Slf4j
public class OnlineController {

    private final OnlineService onlineService;

    /**
     * 页面
     *
     * @param dto 获取在线用户分页入参
     * @return com.pandama.top.core.global.response.Response<com.pandama.top.core.pojo.vo.PageVO < com.pandama.top.user.pojo.vo.OnlineSearchResultVO>>
     * @author 王强
     */
    @ApiOperation("获取在线用户分页")
    @PostMapping("/page")
    public Response<PageVO<OnlineSearchResultVO>> page(@Validated @RequestBody OnlineSearchDTO dto) {
        return Response.success(onlineService.page(dto));
    }

    /**
     * 删除
     *
     * @param onlineIds 在线信息id列表
     * @return com.pandama.top.core.global.response.Response<java.lang.Void>
     * @author 王强
     */
    @ApiOperation("删除在线用户信息")
    @DeleteMapping
    public Response<Void> delete(
            @ApiParam("在线信息id列表，必填项")
            @NotEmpty(message = "在线信息id列表，onlineIds不能为null")
            @RequestParam List<Long> onlineIds) {
        onlineService.delete(onlineIds);
        return Response.success();
    }
}
