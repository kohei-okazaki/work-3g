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

import jp.co.ha.common.validator.LengthMode;
import jp.co.ha.common.validator.LengthValidator;

/**
 * 桁数妥当性チェックアノテーション
 *
 * @see jp.co.ha.common.validator.LengthValidator
 * @version 1.0.0
 */
@Inherited
@Documented
@Retention(RUNTIME)
@Repeatable(Lengths.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Constraint(validatedBy = LengthValidator.class)
public @interface Length {

    /** length */
    int length();

    /** mode:デフォルト一致 */
    LengthMode mode() default LengthMode.EQUAL;

    /** message */
    String message() default "";

    /** groups */
    Class<?>[] groups() default {};

    /** payload */
    Class<? extends Payload>[] payload() default {};
}
