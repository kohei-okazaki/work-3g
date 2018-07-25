package jp.co.ha.common.validate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.ha.common.validate.LengthValidator;

/**
 * 桁数チェックアノテーション<br>
 * @see jp.co.ha.common.validate.LengthValidator
 *
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Constraint(validatedBy = LengthValidator.class)
public @interface Length {

	/**
	 * 最小桁数
	 * @return
	 */
	int min() default 0;

	/**
	 * 最大桁数
	 * @return
	 */
	int max() default 999;

    /**
     * エラーメッセージ
     * @return
     */
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
