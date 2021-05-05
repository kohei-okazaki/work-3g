package jp.co.ha.common.io.encodeanddecode.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Qualifier;

import jp.co.ha.common.io.encodeanddecode.Base64EncodeAndDecoder;

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
 * @version 1.0.0
 * @see jp.co.ha.common.io.encodeanddecode.Base64EncodeAndDecoder
 */
@Inherited
@Documented
@Qualifier(Base64EncodeAndDecoder.ID)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Base64 {

}
