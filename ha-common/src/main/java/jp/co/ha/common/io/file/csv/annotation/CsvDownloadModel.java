package jp.co.ha.common.io.file.csv.annotation;

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
@Target(ElementType.TYPE)
public @interface CsvDownloadModel {

	String[] headerNames() default "";

	String[] footerNames() default "";
}
