package jp.co.ha.common.system.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * パスワード暗号化アノテーション<br>
 * ex)<br>
 * 
 * <pre>
 * &#64;Bcript
 * &#64;Autowired
 * private PasswordEncoder passwordEncoder;
 * </pre>
 *
 * @since 1.0
 */
@Inherited
@Documented
@Qualifier("bcryptHashEncoder")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Bcrypt {

}
