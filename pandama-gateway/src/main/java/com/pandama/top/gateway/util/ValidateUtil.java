package com.pandama.top.gateway.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * @description: 参数验证工具
 * 手动对加了javax.validation参数校验注解的字段进行数据校验
 * @author: 王强
 * @dateTime: 2022-10-27 11:51:32
 */
public class ValidateUtil {

    /**
     * @param t 校验对象
     * @description: 校验器
     * @author: 王强
     * @date: 2022-10-27 11:44:58
     * @return: void
     * @version: 1.0
     */
    public static void valid(Object t) {
        Validator validatorFactory = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> errors = validatorFactory.validate(t);
        if (errors.stream().findFirst().isPresent()) {
            throw new ValidationException(errors.stream().findFirst().get().getMessage());
        }
    }
}
