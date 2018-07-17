package jp.co.ha.common.file.csv.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CSVダウンロードモデルアノテーション<br>
 * headerNamesでヘッダ名を設定<br>
 * footerNamesでフッタ名を設定<br>
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface CsvDownloadModel {

	/**
	 * ヘッダ名 セットした順にファイルにつめられる<br>
	 *
	 * @return ヘッダ名
	 */
	String[] headerNames() default "";

	/**
	 * フッタ名 セットした順にファイルにつめられる<br>
	 *
	 * @return フッタ名
	 */
	String[] footerNames() default "";
}
