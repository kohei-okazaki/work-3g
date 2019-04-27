package jp.co.ha.common.system.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * エンコードアノテーション<br>
 * ex)<br>
 * <code>
 * @Url
 * @Autowired
 * private BaseEncodeAndDecoder encoder;
 * </code>
 *
 */
@Inherited
@Documented
@Qualifier("urlEncodeAndDecoder")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Url {

}
