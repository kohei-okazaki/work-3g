package jp.co.ha.common.validator.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import jp.co.ha.common.validator.MinValidator;

/**
 * 最小桁数の妥当性チェックアノテーション
 *
 * @see jp.co.ha.common.validator.MinValidator
 * @version 1.0.0
 */
@Inherited
@Documented
@Retention(RUNTIME)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Constraint(validatedBy = MinValidator.class)
public @interface Min {

    /** size */
    long size();

    /** 同じ値を含むか */
    boolean isEqual() default true;

    /** message */
    String message() default "";

    /** groups */
    Class<?>[] groups() default {};

    /** payload */
    Class<? extends Payload>[] payload() default {};
}
