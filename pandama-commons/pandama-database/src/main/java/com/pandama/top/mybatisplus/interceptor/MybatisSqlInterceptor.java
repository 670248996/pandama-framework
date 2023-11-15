package com.pandama.top.mybatisplus.interceptor;

import com.pandama.top.mybatisplus.enums.DataPermEnum;
import com.pandama.top.mybatisplus.utils.DataPermUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * mysql自定义拦截器
 *
 * @author 王强
 * @date 2023-11-13 13:30:33
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
public class MybatisSqlInterceptor implements Interceptor {

    /**
     * intercept 方法用来对拦截的sql进行具体的操作
     *
     * @param invocation
     * @return java.lang.Object
     * @author 王强
     */
    @Override
    @SuppressWarnings("all")
    public Object intercept(Invocation invocation) throws Throwable {
        log.debug("执行intercept方法：{}", invocation.toString());
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameterObject = args[1] == null ? new MapperMethod.ParamMap<>() : args[1];
        if (parameterObject instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap<Object> map = (MapperMethod.ParamMap) parameterObject;
            DataPermEnum[] values = DataPermEnum.values();
            Map<DataPermEnum, List<Long>> dataPerm = DataPermUtils.getDataPerm();
            for (DataPermEnum value : values) {
                map.put(value.getField(), Optional.ofNullable(dataPerm.get(value)).orElse(new ArrayList<>()));
            }
            long count = dataPerm.values().stream().filter(p -> CollectionUtils.isNotEmpty(p)).count();
            map.put("data_perm_enabled", count > 0);
            args[1] = map;
        }
        // sql语句类型 select、delete、insert、update
        String sqlCommandType = ms.getSqlCommandType().toString();
        // 仅拦截 select 查询
        if (!sqlCommandType.equals(SqlCommandType.SELECT.toString())) {
            return invocation.proceed();
        }
        // do something ...方法拦截前执行代码块
        Object result = invocation.proceed();
        // do something ...方法拦截后执行代码块
        return result;
    }

    @Override
    public Object plugin(Object target) {
        log.debug("plugin方法：{}", target);
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }
}
