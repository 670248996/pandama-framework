package com.pandama.top.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Bean转换工具
 *
 * @author 王强
 * @date 2023-07-08 15:13:23
 */
@Slf4j
public class BeanConvertUtils extends BeanUtils {

    /**
     * 转换
     *
     * @param source         转换源对象
     * @param targetSupplier 目标对象函数表达式
     * @return java.util.Optional<T>
     * @author 王强
     */
    public static <S, T> Optional<T> convert(S source, Supplier<T> targetSupplier) {
        return convert(source, targetSupplier, null);
    }

    /**
     * 转换
     *
     * @param source         转换源对象
     * @param targetSupplier 目标对象函数表达式
     * @param callBack       特殊回调方法（S，转换源对象类型；T，目标对象类型）
     * @return java.util.Optional<T>
     * @author 王强
     */
    public static <S, T> Optional<T> convert(S source, Supplier<T> targetSupplier, BiConsumer<S, T> callBack) {
        // 如果提供的转换源对象为null或未提供目标对象函数表达式，则返回空Optional
        if (null == source || null == targetSupplier) {
            return Optional.empty();
        }
        // 调用表达式方法取出转换的目标对象
        T target = targetSupplier.get();
        // 使用Spring的BeanUtils工具类的内置属性copy方法进行拷贝
        copyProperties(source, target);
        // 如果存在指定特殊回调，则进行函数调用
        if (callBack != null) {
            callBack.accept(source, target);
        }
        return Optional.of(target);
    }

    /**
     * 将集合
     *
     * @param sources        转换源对象列表
     * @param targetSupplier 目标对象函数表达式
     * @return java.util.Optional<java.util.Collection < T>>
     * @author 王强
     */
    public static <S, T> Optional<Collection<T>> convertCollection(Collection<S> sources, Supplier<T> targetSupplier) {
        return convertCollection(sources, targetSupplier, null);
    }

    /**
     * 将集合
     *
     * @param sources        转换源对象列表
     * @param targetSupplier 目标对象函数表达式
     * @param callBack       特殊回调方法（S，转换源对象类型；T，目标对象类型）
     * @return java.util.Optional<java.util.Collection < T>>
     * @author 王强
     */
    public static <S, T> Optional<Collection<T>> convertCollection(Collection<S> sources,
                                                                   Supplier<T> targetSupplier,
                                                                   BiConsumer<S, T> callBack) {
        Collection<T> c = null;
        // 判断源对象列表类型
        if (sources instanceof List) {
            // 调用表达式方法取出转换的目标对象列表并转换ArrayList
            c = new ArrayList<>(sources.size());
        } else if (sources instanceof Set) {
            // 调用表达式方法取出转换的目标对象列表并转换HashSet
            c = new HashSet<>(sources.size());
        }
        // 如果提供的转换源对象列表为null或列表内无元素或提供目标对象函数表达式，则返回空Optional
        if (null == sources || sources.size() == 0 || null == targetSupplier || null == c) {
            return Optional.empty();
        }
        // 遍历列表元素
        for (S source : sources) {
            // 取出列表对象元素
            T target = targetSupplier.get();
            // 使用Spring的BeanUtils工具类的内置属性copy方法进行拷贝
            copyProperties(source, target);
            // 如果存在指定特殊回调，则进行函数调用
            if (callBack != null) {
                callBack.accept(source, target);
            }
            c.add(target);
        }
        return Optional.of(c);
    }
}
