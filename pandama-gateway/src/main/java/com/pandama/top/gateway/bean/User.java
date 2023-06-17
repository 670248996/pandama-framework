package com.pandama.top.gateway.bean;

import com.pandama.top.core.pojo.vo.UserLoginVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 用户信息
 * @author: 王强
 * @dateTime: 2022-10-17 16:02:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends UserLoginVO implements Serializable, UserDetails {
    private static final long serialVersionUID = 4586107564176031016L;

    @Override
    public List<UserGrantedAuthority> getAuthorities() {
        return super.getRoleCodeList().stream().map(UserGrantedAuthority::new).collect(Collectors.toList());
    }

    /**
     * @description: 账号是否未过期
     * @author: 王强
     * @date: 2022-10-18 12:54:11
     * @return: boolean
     * @version: 1.0
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @description: 账号是否未被锁定
     * @author: 王强
     * @date: 2022-10-18 12:54:13
     * @return: boolean
     * @version: 1.0
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @description: 用户账号凭证(密码)是否未过期
     * @author: 王强
     * @date: 2022-10-18 12:54:15
     * @return: boolean
     * @version: 1.0
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @description: 账户是否被启用
     * @author: 王强
     * @date: 2022-10-18 12:54:17
     * @return: boolean
     * @version: 1.0
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
