package com.pandama.top.user.biz.controller;

import com.pandama.top.global.response.Response;
import com.pandama.top.pojo.vo.PageResultVO;
import com.pandama.top.user.api.pojo.dto.OnlineSearchDTO;
import com.pandama.top.user.api.pojo.vo.OnlineSearchResultVO;
import com.pandama.top.user.biz.service.OnlineService;
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
 * @description: 在线用户信息控制层
 * @author: 白剑民
 * @dateTime: 2022/10/17 17:32
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
     * @param dto 获取在线用户分页入参
     * @description: 获在线取用户分页
     * @author: 王强
     * @date: 2023-05-24 10:01:22
     * @return: Response<PageResultVO < UserInfoVO>>
     * @version: 1.0
     */
    @ApiOperation("获取在线用户分页")
    @PostMapping("/page")
    public Response<PageResultVO<OnlineSearchResultVO>> page(@Validated @RequestBody OnlineSearchDTO dto) {
        return Response.success(onlineService.page(dto));
    }

    /**
     * @param onlineIds 在线信息id列表
     * @description: 删除在线用户信息
     * @author: 白剑民
     * @date: 2022-10-28 15:21:33
     * @return: com.gientech.iot.global.response.Response<java.lang.Void>
     * @version: 1.0
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
