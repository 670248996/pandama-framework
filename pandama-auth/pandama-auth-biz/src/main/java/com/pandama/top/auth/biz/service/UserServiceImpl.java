package com.pandama.top.auth.biz.service;

import com.pandama.top.auth.api.pojo.SecurityUser;
import com.pandama.top.auth.api.constant.MessageConstant;
import com.pandama.top.core.pojo.vo.CurrentUserInfo;
import com.pandama.top.core.utils.BeanConvertUtils;
import com.pandama.top.core.utils.UserInfoUtils;
import com.pandama.top.logRecord.context.LogRecordContext;
import com.pandama.top.user.api.pojo.vo.UserLoginVO;
import com.pandama.top.user.api.service.UserFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户管理业务类
 *
 * @author 王强
 * @date 2023-07-08 11:53:57
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserDetailsService {

    private final UserFeignService userFeignService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLoginVO user = userFeignService.findUserByUsername(username);
        LogRecordContext.putVariables("user", user);
        UserInfoUtils.setUserInfo(BeanConvertUtils.convert(user, CurrentUserInfo::new)
                .orElse(new CurrentUserInfo()));
        SecurityUser securityUser = new SecurityUser(user);
        if (!securityUser.isEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }
        return securityUser;
    }

}
