package jp.co.ha.tool.build.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Buildアノテーション
 * 
 * @since 1.0
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface Build {

}
