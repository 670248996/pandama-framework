package com.pandama.top.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandama.top.core.enums.LoginTypeEnum;
import com.pandama.top.core.pojo.vo.PageVO;
import com.pandama.top.core.utils.BeanConvertUtils;
import com.pandama.top.logRecord.enums.LogTypeEnum;
import com.pandama.top.user.entity.SysLog;
import com.pandama.top.user.mapper.LogMapper;
import com.pandama.top.user.pojo.dto.LogSearchDTO;
import com.pandama.top.user.pojo.dto.OnlineSearchDTO;
import com.pandama.top.user.pojo.vo.LogSearchResultVO;
import com.pandama.top.user.pojo.vo.LoginLogExportResultVO;
import com.pandama.top.user.pojo.vo.OnlineSearchResultVO;
import com.pandama.top.user.pojo.vo.OperateLogExportResultVO;
import com.pandama.top.user.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 日志服务impl
 *
 * @author 王强
 * @date 2023-07-08 15:53:55
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LogServiceImpl extends ServiceImpl<LogMapper, SysLog> implements LogService {

    private final LogMapper logMapper;

    @Override
    public PageVO<LogSearchResultVO> page(LogSearchDTO dto) {
        IPage<LogSearchResultVO> search = logMapper.page(new Page<>(dto.getCurrent(), dto.getSize()), dto);
        return BeanConvertUtils.convert(search, PageVO<LogSearchResultVO>::new).orElse(new PageVO<>());
    }

    @Override
    public void delete(List<Long> ids) {
        logMapper.deleteBatchIds(ids);
    }

    @Override
    public void exportExcel(LogSearchDTO dto) {
        PageVO<LogSearchResultVO> page = page(dto);
        // 序号
        AtomicInteger serialNo = new AtomicInteger(1);
        // 登陆日志导出
        if (LogTypeEnum.LOGIN_LOG == dto.getLogType()) {
            Collection<LoginLogExportResultVO> records = BeanConvertUtils.convertCollection(page.getRecords(), LoginLogExportResultVO::new, (s, t) -> {
                t.setEvent(LoginTypeEnum.getName(s.getEvent()));
                t.setSerialNo(serialNo.getAndIncrement());
            }).orElse(new ArrayList<>());
        }
        // 操作日志导出
        else if (LogTypeEnum.OPERATE_LOG == dto.getLogType()) {
            Collection<OperateLogExportResultVO> records = BeanConvertUtils.convertCollection(page.getRecords(), OperateLogExportResultVO::new, (s, t) -> {
                t.setEvent(LoginTypeEnum.getName(s.getEvent()));
                t.setSerialNo(serialNo.getAndIncrement());
            }).orElse(new ArrayList<>());
        }
    }

    @Override
    public void clean(LogTypeEnum logType) {
        logMapper.delete(new LambdaQueryWrapper<SysLog>().eq(SysLog::getType, logType.getCode()));
    }

    @Override
    public PageVO<OnlineSearchResultVO> onlineUserPage(OnlineSearchDTO dto) {
        dto.setEvent(LoginTypeEnum.LOGIN.getCode());
        dto.setStartCreateTime(LocalDateTime.now().plusHours(-8));
        IPage<OnlineSearchResultVO> search = logMapper.onlineUserPage(new Page<>(dto.getCurrent(), dto.getSize()), dto);
        return BeanConvertUtils.convert(search, PageVO<OnlineSearchResultVO>::new).orElse(new PageVO<>());
    }
}
