package com.pandama.top.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.pandama.top.app.enums.StoreOperationTypeEnum;
import com.pandama.top.app.mapper.DeviceMapper;
import com.pandama.top.app.mapper.DeviceRecordMapper;
import com.pandama.top.app.pojo.dto.*;
import com.pandama.top.app.pojo.entity.Device;
import com.pandama.top.app.pojo.entity.DeviceRecord;
import com.pandama.top.app.pojo.vo.DeviceDetailVO;
import com.pandama.top.app.pojo.vo.DeviceListVO;
import com.pandama.top.app.service.DeviceService;
import com.pandama.top.global.exception.CommonException;
import com.pandama.top.utils.BeanConvertUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 设备服务impl
 * @author: 王强
 * @dateTime: 2023-04-19 16:44:34
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DeviceServiceImpl implements DeviceService {

    private final DeviceMapper deviceMapper;

    private final DeviceRecordMapper deviceRecordMapper;

    @Override
    public void add(DeviceAddDTO dto) {
        List<Device> devices = deviceMapper.selectList(new LambdaQueryWrapper<Device>().eq(Device::getDeviceCode, dto.getDeviceCode()));
        if (CollectionUtils.isNotEmpty(devices)) {
            throw new CommonException("设备型号不能重复");
        }
        Device device = BeanConvertUtils.convert(dto, Device::new).orElseThrow(() -> new CommonException("设备新增失败"));
        deviceMapper.insert(device);
        DeviceRecord deviceRecord = BeanConvertUtils.convert(device, DeviceRecord::new, (s, t) -> {
            t.setDeviceId(s.getId());
            t.setOperationType(StoreOperationTypeEnum.IN.getCode());
            t.setOperationNum(dto.getStore());
            t.setCustomerName(dto.getCustomerName());
        }).orElse(new DeviceRecord());
        deviceRecord.clear();
        deviceRecordMapper.insert(deviceRecord);
    }

    @Override
    public void edit(DeviceEditDTO dto) {
        List<Device> devices = deviceMapper.selectList(new LambdaQueryWrapper<Device>()
                .ne(Device::getId, dto.getId())
                .eq(Device::getDeviceCode, dto.getDeviceCode()));
        if (CollectionUtils.isNotEmpty(devices)) {
            throw new CommonException("设备型号不能重复");
        }
        Device device = BeanConvertUtils.convert(dto, Device::new).orElseThrow(() -> new CommonException("设备编辑失败"));
        deviceMapper.updateById(device);
    }

    @Override
    public DeviceDetailVO detail(Long id) {
        Device device = deviceMapper.selectById(id);
        return BeanConvertUtils.convert(device, DeviceDetailVO::new).orElse(new DeviceDetailVO());
    }

    @Override
    public List<DeviceListVO> list(DeviceListDTO dto) {
        List<Device> devices = deviceMapper.selectList(new LambdaQueryWrapper<Device>()
                .like(StringUtils.isNotBlank(dto.getDeviceCode()), Device::getDeviceCode, "%" + dto.getDeviceCode() + "%")
                .like(StringUtils.isNotBlank(dto.getDeviceName()), Device::getDeviceName, "%" + dto.getDeviceName() + "%")
                .like(StringUtils.isNotBlank(dto.getPower()), Device::getPower, "%" + dto.getPower() + "%"));
        return (List<DeviceListVO>) BeanConvertUtils.convertCollection(devices, DeviceListVO::new).orElse(new ArrayList<>());
    }

    public void storeOperation(DeviceStoreOperationDTO dto) {
        Device device = deviceMapper.selectById(dto.getId());
        if (StoreOperationTypeEnum.IN.equals(dto.getOperation())) {
            device.setStore(device.getStore() + dto.getOperationNum());
            device.setTotalInputNum(device.getTotalInputNum() + dto.getOperationNum());
        } else if (StoreOperationTypeEnum.OUT.equals(dto.getOperation())) {
            device.setStore(device.getStore() - dto.getOperationNum());
            device.setTotalOutputNum(device.getTotalOutputNum() + dto.getOperationNum());
        }
        if (device.getStore() < 0) {
            throw new CommonException("库存不足!");
        }
        deviceMapper.updateById(device);
        DeviceRecord deviceRecord = BeanConvertUtils.convert(device, DeviceRecord::new, (s, t) -> {
            t.setDeviceId(s.getId());
            t.setOperationType(dto.getOperation().getCode());
            t.setOperationNum(dto.getOperationNum());
            t.setCustomerName(dto.getCustomerName());
        }).orElse(new DeviceRecord());
        deviceRecord.clear();
        deviceRecordMapper.insert(deviceRecord);
    }

    @Override
    public List<DeviceRecordListDTO> storeRecord(DeviceStoreRecordDTO dto) {
        List<DeviceRecord> recordList = deviceRecordMapper.selectList(new LambdaQueryWrapper<DeviceRecord>()
                .eq(DeviceRecord::getDeviceId, dto.getDeviceId())
                .eq(dto.getOperation() != null, DeviceRecord::getOperationType, dto.getOperation())
                .orderByDesc(DeviceRecord::getCreateTime));
        return (List<DeviceRecordListDTO>) BeanConvertUtils.convertCollection(recordList, DeviceRecordListDTO::new, (s, t) -> {
            t.setOperationTypeName(StoreOperationTypeEnum.getName(s.getOperationType()));
            t.setOperationTime(s.getCreateTime().toLocalDate());
        }).orElse(new ArrayList<>());
    }
}
