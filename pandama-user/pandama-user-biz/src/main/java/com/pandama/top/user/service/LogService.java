package com.pandama.top.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pandama.top.user.pojo.dto.LogSearchDTO;
import com.pandama.top.user.pojo.dto.OnlineSearchDTO;
import com.pandama.top.user.pojo.vo.LogSearchResultVO;
import com.pandama.top.user.pojo.vo.OnlineSearchResultVO;
import com.pandama.top.user.entity.SysLog;
import com.pandama.top.logRecord.enums.LogTypeEnum;
import com.pandama.top.core.pojo.vo.PageVO;

import java.util.List;

/**
 * @description: 日志信息接口类
 * @author: 白剑民
 * @dateTime: 2022/11/18 15:03
 */
public interface LogService extends IService<SysLog> {

    /**
     * @param dto 系统操作日志搜索传参
     * @description: 系统操作日志搜索
     * @author: 白剑民
     * @date: 2022-11-21 09:42:36
     * @return: com.gientech.iot.pojo.vo.PageResultVO<com.gientech.iot.user.entity.vo.LogSearchResultVO>
     * @version: 1.0
     */
    PageVO<LogSearchResultVO> page(LogSearchDTO dto);

    /**
     * @param ids id
     * @description: 删除
     * @author: 王强
     * @date: 2023-05-29 15:09:26
     * @return: void
     * @version: 1.0
     */
    void delete(List<Long> ids);

    /**
     * @param dto      系统操作日志搜索传参
     * @description: 导出系统操作日志excel
     * @author: 白剑民
     * @date: 2022-11-24 13:51:37
     * @version: 1.0
     */
    void exportExcel(LogSearchDTO dto);

    /**
     * @description: 清空日志
     * @author: 白剑民
     * @date: 2023-05-06 10:16:08
     * @version: 1.0
     */
    void clean(LogTypeEnum logType);

    /**
     * @param dto 入参
     * @description: 在线用户页面
     * @author: 王强
     * @date: 2023-05-31 12:52:07
     * @return: PageResultVO<OnlineSearchResultVO>
     * @version: 1.0
     */
    PageVO<OnlineSearchResultVO> onlineUserPage(OnlineSearchDTO dto);

}