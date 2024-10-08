package com.pandama.top.mybatisplus.fill;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.pandama.top.core.pojo.vo.CurrentUserInfo;
import com.pandama.top.core.utils.UserInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 自动填充数据库字段
 *
 * @author 王强
 * @date 2023-07-08 15:19:07
 */
@Slf4j
public class FillMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        CurrentUserInfo userInfo = Optional.ofNullable(UserInfoUtils.getUserInfo()).orElse(new CurrentUserInfo());
        this.strictInsertFill(metaObject, "createUserId", Long.class, userInfo.getId());
        this.strictInsertFill(metaObject, "createUserCode", String.class, userInfo.getUsername());
        this.strictInsertFill(metaObject, "createUserName", String.class, userInfo.getRealName());
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateUserId", Long.class, userInfo.getId());
        this.strictInsertFill(metaObject, "updateUserCode", String.class, userInfo.getUsername());
        this.strictInsertFill(metaObject, "updateUserName", String.class, userInfo.getRealName());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "isDelete", Boolean.class, Boolean.FALSE);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        CurrentUserInfo userInfo = Optional.ofNullable(UserInfoUtils.getUserInfo()).orElse(new CurrentUserInfo());
        this.strictInsertFill(metaObject, "updateUserId", Long.class, userInfo.getId());
        this.strictInsertFill(metaObject, "updateUserCode", String.class, userInfo.getUsername());
        this.strictInsertFill(metaObject, "updateUserName", String.class, userInfo.getRealName());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}