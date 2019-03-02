package jp.co.ha.web.service.annotation;

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
 * @Sha256
 * @Autowired
 * private PasswordEncoder passwordEncoder;
 * </code>
 *
 */
@Inherited
@Documented
@Qualifier("sha256HashEncoder")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Sha256 {

}
