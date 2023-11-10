package com.pandama.top.dubbo.filter;

import com.alibaba.fastjson.JSON;
import com.pandama.top.core.global.Global;
import com.pandama.top.core.pojo.vo.CurrentUserInfo;
import com.pandama.top.core.utils.UserInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

@Slf4j
@Activate(group = "provider")
public class UserInfoProviderFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String userInfo = (String) invocation.getObjectAttachment(Global.USER_INFO);
        if (null != userInfo) {
            UserInfoUtils.setUserInfo(JSON.parseObject(userInfo, CurrentUserInfo.class));
        }
        return invoker.invoke(invocation);
    }
}
