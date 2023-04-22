package com.pandama.top.app.controller;

import com.pandama.top.app.pojo.dto.*;
import com.pandama.top.app.pojo.vo.DeviceDetailVO;
import com.pandama.top.app.pojo.vo.DeviceListVO;
import com.pandama.top.app.service.DeviceService;
import com.pandama.top.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 设备控制器
 * @author: 王强
 * @dateTime: 2023-04-19 11:09:53
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/device")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DeviceController {

    private final DeviceService deviceService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response<?> add(@Validated @RequestBody DeviceAddDTO dto) {
        deviceService.add(dto);
        return Response.success();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Response<?> edit(@Validated @RequestBody DeviceEditDTO dto) {
        deviceService.edit(dto);
        return Response.success();
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Response<List<DeviceListVO>> list(@Validated @RequestBody DeviceListDTO dto) {
        return Response.success(deviceService.list(dto));
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public Response<?> store(@Validated @RequestBody DeviceStoreOperationDTO dto) {
        deviceService.storeOperation(dto);
        return Response.success();
    }

    @RequestMapping(value = "/storeRecord", method = RequestMethod.POST)
    public Response<List<DeviceRecordListDTO>> storeRecord(@Validated @RequestBody DeviceStoreRecordDTO dto) {
        return Response.success(deviceService.storeRecord(dto));
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Response<DeviceDetailVO> detail(@RequestParam Long id) {
        return Response.success(deviceService.detail(id));
    }

}
