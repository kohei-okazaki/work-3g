package jp.co.ha.common.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.validator.DateValidator;

/**
 * 日付の妥当性チェックのアノテーション
 *
 * @see jp.co.ha.common.validator.DateValidator
 * @since 1.0
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
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
