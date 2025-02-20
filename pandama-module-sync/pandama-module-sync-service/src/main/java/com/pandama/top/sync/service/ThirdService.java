package com.pandama.top.sync.service;


import java.time.LocalDate;

/**
 * @author: hyj
 * @date 2021/3/24
 */
public interface ThirdService {

    /**
     * 同步亚信的设备数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @author 王强
     */
    void syncYXDeviceDataToDB(LocalDate startDate, LocalDate endDate);
}
