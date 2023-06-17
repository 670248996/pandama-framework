package com.pandama.top.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandama.top.core.pojo.vo.PageVO;
import com.pandama.top.user.service.LogService;
import com.pandama.top.core.utils.BeanConvertUtils;
import com.pandama.top.logRecord.bean.LogDTO;
import com.pandama.top.logRecord.enums.LogTypeEnum;
import com.pandama.top.logRecord.service.ILogRecordService;
import com.pandama.top.user.pojo.dto.LogSearchDTO;
import com.pandama.top.user.pojo.dto.OnlineSearchDTO;
import com.pandama.top.user.pojo.vo.LogSearchResultVO;
import com.pandama.top.user.pojo.vo.LoginLogExportResultVO;
import com.pandama.top.user.pojo.vo.OnlineSearchResultVO;
import com.pandama.top.user.pojo.vo.OperateLogExportResultVO;
import com.pandama.top.user.entity.SysLog;
import com.pandama.top.user.enums.LoginTypeEnum;
import com.pandama.top.user.mapper.LogMapper;
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
 * @description: 日志信息实现类
 * @author: 白剑民
 * @dateTime: 2022/11/18 15:05
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LogServiceImpl extends ServiceImpl<LogMapper, SysLog> implements LogService, ILogRecordService {

    private final LogMapper logMapper;

    @Override
    public void createLog(LogDTO dto) {
        SysLog sysLog = BeanConvertUtils.convert(dto, SysLog::new, (s, t) -> {
            t.setModule(s.getBizType());
            t.setEvent(s.getBizEvent());
            t.setMsg(dto.getMsg());
            t.setExtra(s.getExtra());
            t.setType(Integer.valueOf(s.getTag()));
            t.setCreateTime(LocalDateTime.now());
            t.setCreateUserId(Long.parseLong(s.getOperatorId() == null ? "0" : s.getOperatorId()));
        }).orElse(new SysLog());
        logMapper.insert(sysLog);
    }

    @Override
    public PageVO<LogSearchResultVO> page(LogSearchDTO dto) {
        IPage<LogSearchResultVO> search = logMapper.search(new Page<>(dto.getCurrent(), dto.getSize()), dto);
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
