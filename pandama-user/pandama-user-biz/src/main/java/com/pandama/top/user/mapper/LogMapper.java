package com.pandama.top.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandama.top.user.entity.SysLog;
import com.pandama.top.user.pojo.dto.LogSearchDTO;
import com.pandama.top.user.pojo.dto.OnlineSearchDTO;
import com.pandama.top.user.pojo.vo.LogSearchResultVO;
import com.pandama.top.user.pojo.vo.OnlineSearchResultVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @description: 日志Mapper接口
 * @author: 白剑民
 * @dateTime: 2022-11-21 09:49:30
 */
@Repository
public interface LogMapper extends BaseMapper<SysLog> {

    /**
     * @param page 分页参数
     * @param dto  系统日志搜索传参
     * @description: 系统日志搜索
     * @author: 白剑民
     * @date: 2022-11-21 09:49:34
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.gientech.iot.user.api.entity.vo.LogSearchResultVO>
     * @version: 1.0
     */
    IPage<LogSearchResultVO> page(IPage<LogSearchResultVO> page, @Param("dto") LogSearchDTO dto);

    /**
     * @param page 分页参数
     * @param dto  在线用户搜索传参
     * @description: 在线用户页面
     * @author: 王强
     * @date: 2023-05-31 11:47:04
     * @return: IPage<OnlineSearchResultVO>
     * @version: 1.0
     */
    IPage<OnlineSearchResultVO> onlineUserPage(IPage<OnlineSearchResultVO> page, @Param("dto") OnlineSearchDTO dto);
}
