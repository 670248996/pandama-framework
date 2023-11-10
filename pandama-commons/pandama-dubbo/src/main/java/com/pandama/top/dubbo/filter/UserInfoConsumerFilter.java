package com.pandama.top.dubbo.filter;

import com.alibaba.fastjson.JSON;
import com.pandama.top.core.global.Global;
import com.pandama.top.core.pojo.vo.CurrentUserInfo;
import com.pandama.top.core.utils.UserInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

@Slf4j
@Activate(group = "consumer")
public class UserInfoConsumerFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            CurrentUserInfo userInfo = UserInfoUtils.getUserInfo();
            if (null == userInfo) {
                return invoker.invoke(invocation);
            }
            invocation.getObjectAttachments().put(Global.USER_INFO, JSON.toJSONString(userInfo));
            return invoker.invoke(invocation);
        } finally {
            UserInfoUtils.clearUserInfo();
        }
    }
}
