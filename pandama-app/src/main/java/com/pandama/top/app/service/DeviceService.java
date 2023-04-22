package com.pandama.top.app.service;

import com.pandama.top.app.pojo.dto.*;
import com.pandama.top.app.pojo.vo.DeviceDetailVO;
import com.pandama.top.app.pojo.vo.DeviceListVO;

import java.util.List;

/**
 * @description: 设备服务
 * @author: 王强
 * @dateTime: 2023-04-20 14:45:27
 */
public interface DeviceService {

    /**
     * @param dto 入参
     * @description: 添加
     * @author: 王强
     * @date: 2023-04-20 14:45:31
     * @return: void
     * @version: 1.0
     */
    void add(DeviceAddDTO dto);

    /**
     * @param dto 入参
     * @description: 编辑
     * @author: 王强
     * @date: 2023-04-20 14:45:32
     * @return: void
     * @version: 1.0
     */
    void edit(DeviceEditDTO dto);

    /**
     * @param id id
     * @description: 详情
     * @author: 王强
     * @date: 2023-04-20 14:45:33
     * @return: DeviceDetailVO
     * @version: 1.0
     */
    DeviceDetailVO detail(Long id);

    /**
     * @param dto 入参
     * @description: 列表
     * @author: 王强
     * @date: 2023-04-20 14:45:34
     * @return: List<DeviceListVO>
     * @version: 1.0
     */
    List<DeviceListVO> list(DeviceListDTO dto);

    /**
     * @param dto 入参
     * @description: 存储操作
     * @author: 王强
     * @date: 2023-04-22 12:17:14
     * @return: void
     * @version: 1.0
     */
    void storeOperation(DeviceStoreOperationDTO dto);

    /**
     * @param dto 入参
     * @description: 商店
     * @author: 王强
     * @date: 2023-04-20 14:45:35
     * @return: void
     * @version: 1.0
     */
    List<DeviceRecordListDTO> storeRecord(DeviceStoreRecordDTO dto);
}
