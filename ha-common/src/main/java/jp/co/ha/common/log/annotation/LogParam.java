package jp.co.ha.common.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ログ出力項目名につけるアノテーション<br>
 * ログ出力時に指定したい名前をnameに指定する<br>
 *
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LogParam {

	/**
	 * 名前
	 *
	 * @return 名前
	 */
	String name();
}
