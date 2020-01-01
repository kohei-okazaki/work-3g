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
 * 
 * <pre>
 * &#64;Base64
 * &#64;Autowired
 * private BaseEncodeAndDecoder encoder;
 * </pre>
 *
 * @since 1.0
 */
@Inherited
@Documented
@Qualifier("base64EncodeAndDecoder")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Base64 {

}
