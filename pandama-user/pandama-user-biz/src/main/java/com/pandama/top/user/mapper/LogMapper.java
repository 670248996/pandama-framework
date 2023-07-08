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
 * 日志mapper
 *
 * @author 王强
 * @date 2023-07-08 15:48:17
 */
@Repository
public interface LogMapper extends BaseMapper<SysLog> {

    /**
     * 页面
     *
     * @param page 分页参数
     * @param dto  系统日志搜索传参
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.pandama.top.user.pojo.vo.LogSearchResultVO>
     * @author 王强
     */
    IPage<LogSearchResultVO> page(IPage<LogSearchResultVO> page, @Param("dto") LogSearchDTO dto);

    /**
     * 在线用户页面
     *
     * @param page 分页参数
     * @param dto  在线用户搜索传参
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.pandama.top.user.pojo.vo.OnlineSearchResultVO>
     * @author 王强
     */
    IPage<OnlineSearchResultVO> onlineUserPage(IPage<OnlineSearchResultVO> page, @Param("dto") OnlineSearchDTO dto);
}
