package com.pandama.top.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.pandama.top.redis.utils.RedisUtils;
import com.pandama.top.core.pojo.vo.PageVO;
import com.pandama.top.core.pojo.vo.CurrentUserInfo;
import com.pandama.top.user.service.LogService;
import com.pandama.top.user.service.OnlineService;
import com.pandama.top.core.utils.DateUtils;
import com.pandama.top.core.utils.UserInfoUtils;
import com.pandama.top.core.enums.LoginTypeEnum;
import com.pandama.top.user.pojo.dto.OnlineSearchDTO;
import com.pandama.top.user.pojo.vo.OnlineSearchResultVO;
import com.pandama.top.user.entity.SysLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 在线服务impl
 *
 * @author 王强
 * @date 2023-07-08 15:54:01
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OnlineServiceImpl implements OnlineService {

    private final LogService logService;

    private final RedisUtils redisUtils;

    @Override
    public PageVO<OnlineSearchResultVO> page(OnlineSearchDTO dto) {
        return logService.onlineUserPage(dto);
    }

    @Override
    public void delete(List<Long> onlineIds) {
        CurrentUserInfo user = UserInfoUtils.getUserInfo();
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
