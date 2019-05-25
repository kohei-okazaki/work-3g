package jp.co.ha.common.io.file.property.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Inherited
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface Property {

	/**
	 * 名前
	 *
	 * @return name
	 */
	String name() default "";
}
