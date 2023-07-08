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
 * 日志服务
 *
 * @author 王强
 * @date 2023-07-08 15:55:35
 */
public interface LogService extends IService<SysLog> {

    /**
     * 页面
     *
     * @param dto 系统操作日志搜索传参
     * @return com.pandama.top.core.pojo.vo.PageVO<com.pandama.top.user.pojo.vo.LogSearchResultVO>
     * @author 王强
     */
    PageVO<LogSearchResultVO> page(LogSearchDTO dto);

    /**
     * 删除
     *
     * @param ids id
     * @return void
     * @author 王强
     */
    void delete(List<Long> ids);

    /**
     * 导出excel
     *
     * @param dto 系统操作日志搜索传参
     * @return void
     * @author 王强
     */
    void exportExcel(LogSearchDTO dto);

    /**
     * 清除
     *
     * @param logType 日志类型
     * @return void
     * @author 王强
     */
    void clean(LogTypeEnum logType);

    /**
     * 在线用户页面
     *
     * @param dto 入参
     * @return com.pandama.top.core.pojo.vo.PageVO<com.pandama.top.user.pojo.vo.OnlineSearchResultVO>
     * @author 王强
     */
    PageVO<OnlineSearchResultVO> onlineUserPage(OnlineSearchDTO dto);

}
