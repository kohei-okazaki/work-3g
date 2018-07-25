package jp.co.ha.common.validate.annotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.ha.common.validate.RequiredValidator;

/**
 * 入力チェックアノテーション<br>
 * 必須チェック<br>
 *
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Constraint(validatedBy = RequiredValidator.class)
public @interface Required {

    String message() default "validate.message.NotEmpty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
