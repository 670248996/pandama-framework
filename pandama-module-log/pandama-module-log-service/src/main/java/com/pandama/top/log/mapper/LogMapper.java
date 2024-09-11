package com.pandama.top.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandama.top.log.entity.SysLog;
import com.pandama.top.log.pojo.dto.LogSearchDTO;
import com.pandama.top.log.pojo.vo.LogSearchResultVO;
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
}
