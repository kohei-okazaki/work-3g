package jp.co.ha.common.file.csv.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CSVアノテーション<br>
 * headerNamesでヘッダー名を設定<br>
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface CsvHeader {

	/**
	 * ヘッダ名 セットした順にファイルにつめられる<br>
	 *
	 * @return ヘッダ名
	 */
	String[] names() default "";

}
