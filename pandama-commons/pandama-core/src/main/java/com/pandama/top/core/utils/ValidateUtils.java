package com.pandama.top.core.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * 验证工具 手动对加了javax.validation参数校验注解的字段进行数据校验
 *
 * @author 王强
 * @date 2023-07-08 15:18:22
 */
public class ValidateUtils {

    /**
     * 有效
     *
     * @param t 校验对象
     * @author 王强
     */
    public static void valid(Object t) {
        Validator validatorFactory = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> errors = validatorFactory.validate(t);
        if (errors.stream().findFirst().isPresent()) {
            throw new ValidationException(errors.stream().findFirst().get().getMessage());
        }
    }
}
