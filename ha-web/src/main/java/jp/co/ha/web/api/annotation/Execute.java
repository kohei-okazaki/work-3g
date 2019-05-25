package jp.co.ha.web.api.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * API実行のマーカーアノテーション
 *
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface Execute {

}