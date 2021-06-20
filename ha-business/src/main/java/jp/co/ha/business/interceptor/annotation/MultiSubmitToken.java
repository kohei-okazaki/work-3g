package jp.co.ha.business.interceptor.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.co.ha.business.interceptor.DashboardAuthInterceptor;

/**
 * 多重送信トークンをチェックすることを示すアノテーション
 * <ul>
 * <li>トークンを生成したいメソッドに<code>@MultiSubmitToken(factocy = true)</code>を付与</li>
 * <li>トークンチェックを行いたいメソッドに<code>@MultiSubmitToken(check = true)</code>を付与</li>
 * </ul>
 *
 * @see DashboardAuthInterceptor
 * @version 1.0.0
 */
@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MultiSubmitToken {

    /** トークン名 */
    public static final String TOKEN_NAME = "multiSubmitToken";

    /**
     * トークンを生成する
     *
     * @return 生成する場合true、それ以外の場合false
     */
    boolean factory() default false;

    /**
     * トークンをチェックする
     *
     * @return チェックする場合true、それ以外の場合false
     */
    boolean check() default false;
}
