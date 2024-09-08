package jp.co.ha.common.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import jp.co.ha.common.validator.RequiredValidator;

/**
 * 必須妥当性チェックアノテーション
 *
 * @see jp.co.ha.common.validator.RequiredValidator
 * @version 1.0.0
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = RequiredValidator.class)
public @interface Required {

    /** message */
    String message() default "";

    /** groups */
    Class<?>[] groups() default {};

    /** payload */
    Class<? extends Payload>[] payload() default {};
}
