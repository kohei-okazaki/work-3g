package jp.co.ha.common.io.file.csv.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * CSVダウンロードモデルアノテーション<br>
 * headerNamesでヘッダ名を設定<br>
 * footerNamesでフッタ名を設定
 *
 * @since 1.0
 */
@Inherited
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface CsvDownloadModel {

	/**
	 * ヘッダ名
	 *
	 * @return ヘッダ名
	 */
	String[] headerNames() default "";

	/**
	 * フッタ名
	 *
	 * @return フッタ名
	 */
	String[] footerNames() default "";
}
