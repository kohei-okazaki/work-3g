package jp.co.ha.common.system.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * SHA-256エンコードアノテーション<br>
 * ex)<br>
 * <code>
 * &#64;Sha256
 * &#64;Autowired
 * private PasswordEncoder passwordEncoder;
 * </code>
 * 
 * @since 1.0
 */
@Inherited
@Documented
@Qualifier("sha256HashEncoder")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Sha256 {

}
