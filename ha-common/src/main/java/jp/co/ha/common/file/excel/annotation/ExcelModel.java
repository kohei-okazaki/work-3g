package jp.co.ha.common.file.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excelモデルアノテーション
 *
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface ExcelModel {

	/**
	 * シート名<br>
	 * セットした順にファイルにつめられる<br>
	 *
	 * @return シート名
	 */
	String sheetName() default "Sheet1";

	/**
	 * ヘッダ名 セットした順にファイルにつめられる<br>
	 *
	 * @return ヘッダ名
	 */
	String[] headerNames() default "";
}
