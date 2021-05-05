package jp.co.ha.common.io.encodeanddecode.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Qualifier;

import jp.co.ha.common.io.encodeanddecode.Sha256HashEncoder;

/**
 * SHA-256エンコードアノテーション<br>
 * ex)<br>
 *
 * <pre>
 * &#64;Sha256
 * &#64;Autowired
 * private PasswordEncoder passwordEncoder;
 * </pre>
 *
 * @version 1.0.0
 * @see jp.co.ha.common.io.encodeanddecode.Sha256HashEncoder
 */
@Inherited
@Documented
@Qualifier(Sha256HashEncoder.ID)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Sha256 {

}
