package jp.co.ha.common.validator.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import jp.co.ha.common.validator.DecimalValidator;

/**
 * 浮動小数妥当性チェックアノテーション
 *
 * @see jp.co.ha.common.validator.DecimalValidator
 * @version 1.0.0
 */
@Inherited
@Documented
@Retention(RUNTIME)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Constraint(validatedBy = DecimalValidator.class)
public @interface Decimal {

    /** 最小桁数 */
    int min() default 1;

    /** 最大桁数 */
    int max() default 99999;

    /** 最小桁数を含むかどうか */
    boolean minEqual() default true;

    /** 最大桁数を含むかどうか */
    boolean maxEqual() default true;

    /** message */
    String message() default "";

    /** groups */
    Class<?>[] groups() default {};

    /** payload */
    Class<? extends Payload>[] payload() default {};
}
