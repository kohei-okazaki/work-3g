package jp.co.ha.common.validator.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.validator.DateValidator;

/**
 * 日付の妥当性チェックのアノテーション
 *
 * @see jp.co.ha.common.validator.DateValidator
 * @version 1.0.0
 */
@Inherited
@Documented
@Retention(RUNTIME)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Constraint(validatedBy = DateValidator.class)
public @interface Date {

    /** 日付フォーマット */
    DateFormatType formatType();

    /** message */
    String message() default "";

    /** groups */
    Class<?>[] groups() default {};

    /** payload */
    Class<? extends Payload>[] payload() default {};

}
