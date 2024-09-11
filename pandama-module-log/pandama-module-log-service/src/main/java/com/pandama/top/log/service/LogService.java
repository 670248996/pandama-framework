package com.pandama.top.log.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pandama.top.core.pojo.vo.PageVO;
import com.pandama.top.log.entity.SysLog;
import com.pandama.top.log.pojo.dto.LogSearchDTO;
import com.pandama.top.log.pojo.vo.LogSearchResultVO;
import com.pandama.top.logRecord.enums.LogTypeEnum;

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
     * @author 王强
     */
    void delete(List<Long> ids);

    /**
     * 导出excel
     *
     * @param dto 系统操作日志搜索传参
     * @author 王强
     */
    void exportExcel(LogSearchDTO dto);

    /**
     * 清除
     *
     * @param logType 日志类型
     * @author 王强
     */
    void clean(LogTypeEnum logType);

}
