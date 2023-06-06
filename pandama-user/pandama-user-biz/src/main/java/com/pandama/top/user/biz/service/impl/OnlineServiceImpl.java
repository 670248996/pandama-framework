package com.pandama.top.user.biz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.pandama.top.cache.utils.RedisUtils;
import com.pandama.top.pojo.vo.PageResultVO;
import com.pandama.top.pojo.vo.UserLoginVO;
import com.pandama.top.user.biz.service.LogService;
import com.pandama.top.user.biz.service.OnlineService;
import com.pandama.top.utils.DateUtils;
import com.pandama.top.utils.UserInfoUtils;
import com.pandama.top.user.api.enums.LoginTypeEnum;
import com.pandama.top.user.api.pojo.dto.OnlineSearchDTO;
import com.pandama.top.user.api.pojo.vo.OnlineSearchResultVO;
import com.pandama.top.user.biz.entity.SysLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 在线用户信息实现类
 * @author: 白剑民
 * @dateTime: 2022/10/17 17:37
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OnlineServiceImpl implements OnlineService {

    private final LogService logService;

    private final RedisUtils redisUtils;

    @Override
    public PageResultVO<OnlineSearchResultVO> page(OnlineSearchDTO dto) {
        return logService.onlineUserPage(dto);
    }

    @Override
    public void delete(List<Long> onlineIds) {
        UserLoginVO user = UserInfoUtils.getUserInfo();
        String str = "【%s】被【%s】强制退出，退出时间【%s】";
        List<SysLog> sysLogs = logService.listByIds(onlineIds);
        for (SysLog log : sysLogs) {
            log.setId(IdWorker.getId());
            log.setEvent(LoginTypeEnum.LOGOUT.getCode());
            log.setMsg(String.format(str, log.getCreateUserName(), user.getRealName(), DateUtils.now()));
        }
        logService.saveBatch(sysLogs);
        List<String> collect = sysLogs.stream().map(p -> String.format("token:%s", p.getCreateUserId())).collect(Collectors.toList());
        redisUtils.delete(collect);
    }
}
