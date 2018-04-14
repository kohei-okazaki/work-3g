package jp.co.ha.common.file.csv.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CSVモデルのマーカーアノテーション<br>
 *
 */
@Inherited
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvModel {

}