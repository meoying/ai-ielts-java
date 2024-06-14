package com.meoying.ai.ielts.utils;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @Author: wangmiao01
 */
@Slf4j
public class ValidateUtil {

    private static Validator validator = Validation.byProvider(HibernateValidator .class).configure().failFast(true).buildValidatorFactory().getValidator();

    public static <T> boolean validate(T object) {
        if (object == null) {
            return false;
        }
        Set<ConstraintViolation<T>> validate = validator.validate(object);
        return validate.isEmpty();
    }
}
