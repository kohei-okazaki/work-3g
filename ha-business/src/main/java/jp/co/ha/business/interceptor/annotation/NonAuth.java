package jp.co.ha.business.interceptor.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.co.ha.business.interceptor.AuthInterceptor;

/**
 * ログイン情報のチェックを<b>行わない</b>ことを示すマーカーアノテーション
 *
 * @see AuthInterceptor
 */
@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NonAuth {

}
