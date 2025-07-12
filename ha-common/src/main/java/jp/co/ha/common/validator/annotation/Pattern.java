package jp.co.ha.common.validator.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.PatternValidator;

/**
 * 型チェックアノテーション
 *
 * @see jp.co.ha.common.validator.PatternValidator
 * @version 1.0.0
 */
@Inherited
@Documented
@Retention(RUNTIME)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Constraint(validatedBy = PatternValidator.class)
public @interface Pattern {

    /** 正規表現 */
    RegexType regixPattern();

    /** message */
    String message() default "";

    /** groups */
    Class<?>[] groups() default {};

    /** payload */
    Class<? extends Payload>[] payload() default {};
}
