package jp.co.ha.common.io.file.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excelダウンロードモデルアノテーション<br>
 *
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExcelDownloadModel {

	String sheetName() default "Sheet1";

	String[] headerNames() default "";

	String[] footerNames() default "";
}
