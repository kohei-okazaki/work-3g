package jp.co.ha.common.validator.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import jp.co.ha.common.validator.DecimalValidator;

/**
 * 数値の最小/最大値妥当性チェックアノテーション
 *
 * @see jp.co.ha.common.validator.DecimalValidator
 * @version 1.0.0
 */
@Inherited
@Documented
@Retention(RUNTIME)
@Repeatable(Decimals.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Constraint(validatedBy = DecimalValidator.class)
public @interface Decimal {

    /** 最小値（BigDecimalとして解釈） */
    String min() default "-999999999";

    /** 最大値（BigDecimalとして解釈） */
    String max() default "999999999";

    /** 最小値と等しい値を許容するか */
    boolean minEqual() default true;

    /** 最大値と等しい値を許容するか */
    boolean maxEqual() default true;

    /** message */
    String message() default "";

    /** groups */
    Class<?>[] groups() default {};

    /** payload */
    Class<? extends Payload>[] payload() default {};
}
