package com.pandama.top.logRecord.function;


import com.pandama.top.logRecord.annotation.LogRecordDiff;
import com.pandama.top.logRecord.annotation.LogRecordFunc;
import com.pandama.top.logRecord.bean.DiffDTO;
import com.pandama.top.logRecord.bean.DiffFieldDTO;
import com.pandama.top.logRecord.configuration.LogRecordProperties;
import com.pandama.top.logRecord.context.LogRecordContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 自定义函数对象差异
 *
 * @author 王强
 * @date 2023-07-08 15:23:33
 */
@Slf4j
@LogRecordFunc
@EnableConfigurationProperties(value = LogRecordProperties.class)
public class CustomFunctionObjectDiff {

    public static final String DEFAULT_DIFF_MSG_FORMAT = "编辑前【${_beforeMsg}】编辑后【${_afterMsg}】";
    public static final String DEFAULT_ADD_MSG_FORMAT = "【${_afterMsg}】";
    public static final String DEFAULT_MSG_FORMAT = "${_fieldName}: {${_value}}";
    public static final String DEFAULT_MSG_SEPARATOR = ", ";

    private static String DIFF_MSG_FORMAT;
    private static String ADD_MSG_FORMAT;
    private static String MSG_FORMAT;
    private static String MSG_SEPARATOR;

    public CustomFunctionObjectDiff(LogRecordProperties properties) {
        DIFF_MSG_FORMAT = properties.getDiffMsgFormat().equals(DEFAULT_DIFF_MSG_FORMAT)
                ? DEFAULT_DIFF_MSG_FORMAT
                : new String(properties.getDiffMsgFormat().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        ADD_MSG_FORMAT = properties.getAddMsgFormat().equals(DEFAULT_ADD_MSG_FORMAT)
                ? DEFAULT_ADD_MSG_FORMAT
                : new String(properties.getAddMsgFormat().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        MSG_FORMAT = properties.getMsgFormat().equals(DEFAULT_MSG_FORMAT)
                ? DEFAULT_MSG_FORMAT
                : new String(properties.getMsgFormat().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        MSG_SEPARATOR = properties.getMsgSeparator().equals(DEFAULT_MSG_SEPARATOR)
                ? DEFAULT_MSG_SEPARATOR
                : new String(properties.getMsgSeparator().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    /**
     * 默认的DIFF方法实现
     *
     * @param oldObject 旧对象
     * @param newObject 新对象
     * @return java.lang.String
     * @author 王强
     */
    @LogRecordFunc("_DIFF")
    public static String objectDiff(Object oldObject, Object newObject) {
        StringBuilder msg = new StringBuilder();
        // 若包含null对象，直接返回
        if (oldObject == null || newObject == null) {
            log.warn("null object found [{}] [{}]", oldObject, newObject);
            return msg.toString();
        }
        ValueDTO valueDTO = getValueDTO(oldObject, newObject);
        List<String> beforeList = valueDTO.getBeforeMapList().stream().map(item -> {
            StringSubstitutor sub = new StringSubstitutor(item);
            return sub.replace(MSG_FORMAT);
        }).collect(Collectors.toList());
        List<String> afterList = valueDTO.getAfterMapList().stream().map(item -> {
            StringSubstitutor sub = new StringSubstitutor(item);
            return sub.replace(MSG_FORMAT);
        }).collect(Collectors.toList());
        if (beforeList.size() == 0 && afterList.size() == 0) {
            throw new RuntimeException("操作日志: 编辑前后数据相同, 不处理");
        }
        Map<String, String> msgMap = new LinkedHashMap<>();
        msgMap.put("_beforeMsg", String.join(MSG_SEPARATOR, beforeList));
        msgMap.put("_afterMsg", String.join(MSG_SEPARATOR, afterList));
        StringSubstitutor sub = new StringSubstitutor(msgMap);
        msg.append(sub.replace(DIFF_MSG_FORMAT));
        LogRecordContext.addDiffDTO(valueDTO.getDiffDTO());
        return msg.toString();
    }

    /**
     * 默认的ADD方法实现
     *
     * @param newObject 新对象
     * @return java.lang.String
     * @author 王强
     */
    @LogRecordFunc("_ADD")
    public static String objectAdd(Object newObject) throws InstantiationException, IllegalAccessException {
        StringBuilder msg = new StringBuilder();
        // 若包含null对象，直接返回
        if (newObject == null) {
            log.warn("objectAdd object found [{}]", (Object) null);
            return msg.toString();
        }
        Object oldObject = newObject.getClass().newInstance();
        ValueDTO valueDTO = getValueDTO(oldObject, newObject);
        List<String> addMsgList = valueDTO.getAfterMapList().stream().map(item -> {
            StringSubstitutor sub = new StringSubstitutor(item);
            return sub.replace(MSG_FORMAT);
        }).collect(Collectors.toList());
        Map<String, String> msgMap = new LinkedHashMap<>();
        if (addMsgList.size() == 0) {
            throw new RuntimeException("操作日志: 编辑前后数据相同, 不处理");
        }
        msgMap.put("_afterMsg", String.join(MSG_SEPARATOR, addMsgList));
        StringSubstitutor sub = new StringSubstitutor(msgMap);
        msg.append(sub.replace(ADD_MSG_FORMAT));
        LogRecordContext.addDiffDTO(valueDTO.getDiffDTO());
        return msg.toString();
    }

    /**
     * 获得ValueDTO对象
     *
     * @param oldObject 旧对象
     * @param newObject 新对象
     * @return com.pandama.top.logRecord.function.CustomFunctionObjectDiff.ValueDTO
     * @author 王强
     */
    public static ValueDTO getValueDTO(Object oldObject, Object newObject) {
        // 获取对象类名
        String oldClassName = oldObject.getClass().getName();
        String newClassName = newObject.getClass().getName();
        // 获取类上的注解
        LogRecordDiff oldClassLogRecordDiff = oldObject.getClass().getDeclaredAnnotation(LogRecordDiff.class);
        LogRecordDiff newClassLogRecordDiff = newObject.getClass().getDeclaredAnnotation(LogRecordDiff.class);
        // 获取类上注解的别名
        String oldClassAlias = oldClassLogRecordDiff != null && StringUtils.isNotBlank(oldClassLogRecordDiff.alias())
                ? oldClassLogRecordDiff.alias() : null;
        String newClassAlias = newClassLogRecordDiff != null && StringUtils.isNotBlank(newClassLogRecordDiff.alias())
                ? newClassLogRecordDiff.alias() : null;
        log.debug("oldClassName [{}] oldClassAlias [{}] newClassName [{}] newClassAlias [{}]",
                oldClassName, oldClassAlias, newClassName, newClassAlias);
        // 新旧字段名Map<字段名，字段别名>
        Map<String, String> oldFieldAliasMap = new LinkedHashMap<>();
        Map<String, String> newFieldAliasMap = new LinkedHashMap<>();
        // 新旧字段值Map<字段名，字段值>
        Map<String, Object> oldValueMap = new LinkedHashMap<>();
        Map<String, Object> newValueMap = new LinkedHashMap<>();
        // 获取类的字段
        Field[] fields = oldObject.getClass().getDeclaredFields();
        // 遍历字段加了@LogRecordDiff注解的字段
        for (Field oldField : fields) {
            try {
                // 获取字段的@LogRecordDiff注解信息
                LogRecordDiff oldObjectLogRecordDiff = oldField.getDeclaredAnnotation(LogRecordDiff.class);
                // 若没有LogRecordDiff注解，跳过
                if (oldObjectLogRecordDiff == null) {
                    continue;
                }
                try {
                    // 在新对象中寻找同名字段，若找不到则跳过本次循环
                    Field newField = newObject.getClass().getDeclaredField(oldField.getName());
                    // 获取新对象该字段的@LogRecordDiff注解信息
                    LogRecordDiff newObjectLogRecordDiff = newField.getDeclaredAnnotation(LogRecordDiff.class);
                    // 获取旧对象该字段的别名
                    String oldFieldAlias = StringUtils.isNotBlank(oldObjectLogRecordDiff.alias())
                            ? oldObjectLogRecordDiff.alias() : null;
                    // 获取新对象该字段的别名
                    String newFieldAlias = StringUtils.isNotBlank(newObjectLogRecordDiff.alias())
                            ? newObjectLogRecordDiff.alias() : null;
                    log.debug("field [{}] has annotation oldField alias [{}] newField alias [{}]",
                            oldField.getName(), oldFieldAlias, newFieldAlias);
                    // 字段关闭安全检查，提升反射效率
                    oldField.setAccessible(true);
                    newField.setAccessible(true);
                    // 获取旧对象字段值
                    Object oldValue = oldField.get(oldObject);
                    // 获取新对象字段值
                    Object newValue = newField.get(newObject);
                    // 若新旧值不同，则将数据放入Map
                    if (!newValue.equals(oldValue)) {
                        log.debug("field [{}] is different between oldObject [{}] newObject [{}]",
                                oldField.getName(), oldValue, newValue);
                        oldValueMap.put(oldField.getName(), oldValue);
                        newValueMap.put(newField.getName(), newValue);
                        oldFieldAliasMap.put(oldField.getName(), oldFieldAlias);
                        newFieldAliasMap.put(newField.getName(), newFieldAlias);
                    }
                } catch (NoSuchFieldException e) {
                    log.info("no field named [{}] in newObject, skip", oldField.getName());
                }
            } catch (Exception e) {
                log.error("objectDiff error", e);
            }
        }
        // 字段遍历赋值Map，设置新旧值完成
        log.debug("oldFieldAliasMap [{}]", oldFieldAliasMap);
        log.debug("newFieldAliasMap [{}]", newFieldAliasMap);
        log.debug("oldValueMap [{}]", oldValueMap);
        log.debug("newValueMap [{}]", newValueMap);
        // 初始化ValueDTO对象
        ValueDTO valueDTO = new ValueDTO();
        // 初始化DiffDTO对象
        DiffDTO diffDTO = new DiffDTO();
        // 设置旧对象的class类名，别名
        diffDTO.setOldClassName(oldClassName);
        diffDTO.setOldClassAlias(oldClassAlias);
        // 设置新对象的class类名，别名
        diffDTO.setNewClassName(newClassName);
        diffDTO.setNewClassAlias(newClassAlias);
        // 定义DiffFieldDTO集合，每个DTO存放的是 字段新旧名称，字段新旧值
        List<DiffFieldDTO> diffFieldDTOList = new ArrayList<>();
        diffDTO.setDiffFieldDTOList(diffFieldDTOList);
        // 定义Map集合，每个Map存放的是 字段名称，新值，旧值
        List<Map<String, Object>> mapList = new ArrayList<>();
        // 定义Map集合，每个Map存放的是 字段名称，旧值
        List<Map<String, Object>> beforeMapList = new ArrayList<>();
        // 定义Map集合，每个Map存放的是 字段名称，新值
        List<Map<String, Object>> afterMapList = new ArrayList<>();
        // 遍历旧字段值Map
        for (Map.Entry<String, Object> entry : oldValueMap.entrySet()) {
            // 字段名
            String fieldName = entry.getKey();
            // 字段旧值
            Object oldValue = entry.getValue();
            // 字段新值
            Object newValue = newValueMap.getOrDefault(entry.getKey(), null);
            // 字段旧别名
            String oldFieldAlias = oldFieldAliasMap.getOrDefault(entry.getKey(), null);
            // 字段新别名
            String newFieldAlias = newFieldAliasMap.getOrDefault(entry.getKey(), null);
            // 初始化DiffFieldDTO对象，放入 字段新旧名称，字段新旧值
            DiffFieldDTO diffFieldDTO = new DiffFieldDTO();
            diffFieldDTO.setFieldName(fieldName);
            diffFieldDTO.setOldFieldAlias(oldFieldAlias);
            diffFieldDTO.setNewFieldAlias(newFieldAlias);
            diffFieldDTO.setOldValue(oldValue);
            diffFieldDTO.setNewValue(newValue);
            // DTO对象放入DTO集合
            diffFieldDTOList.add(diffFieldDTO);
            // 初始化Map对象，放入 字段名，字段新旧值
            Map<String, Object> valuesMap = new HashMap<>(3);
            valuesMap.put("_fieldName", StringUtils.isNotBlank(oldFieldAlias) ? oldFieldAlias : fieldName);
            valuesMap.put("_oldValue", oldValue);
            valuesMap.put("_newValue", newValue);
            // Map对象放入Map集合
            mapList.add(valuesMap);
            // 初始化Map对象，放入 字段名，字段旧值
            Map<String, Object> beforeValuesMap = new HashMap<>(2);
            beforeValuesMap.put("_fieldName", StringUtils.isNotBlank(oldFieldAlias) ? oldFieldAlias : fieldName);
            beforeValuesMap.put("_value", oldValue);
            beforeMapList.add(beforeValuesMap);
            // 初始化Map对象，放入 字段名，字段新值
            Map<String, Object> afterValuesMap = new HashMap<>(2);
            afterValuesMap.put("_fieldName", StringUtils.isNotBlank(oldFieldAlias) ? oldFieldAlias : fieldName);
            afterValuesMap.put("_value", newValue);
            afterMapList.add(afterValuesMap);
        }
        valueDTO.setMapList(mapList);
        valueDTO.setBeforeMapList(beforeMapList);
        valueDTO.setAfterMapList(afterMapList);
        valueDTO.setDiffDTO(diffDTO);
        return valueDTO;
    }

    /**
     * valueDTO对象
     *
     * @author 王强
     * @date 2023-07-08 15:23:54
     */
    @Data
    static class ValueDTO {
        /**
         * 字段新旧值的Map集合
         */
        private List<Map<String, Object>> mapList;
        /**
         * 字段修改前的Map集合
         */
        private List<Map<String, Object>> beforeMapList;
        /**
         * 字段修改后的Map集合
         */
        private List<Map<String, Object>> afterMapList;
        /**
         * 发生变化的数据
         */
        private DiffDTO diffDTO;

    }
}
