package jp.co.ha.web.service.annotation;

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
 * <code>
 * @Bcript
 * @Autowired
 * private PasswordEncoder passwordEncoder
 * </code>
 *
 */
@Inherited
@Documented
@Qualifier("bcryptPasswordEncoder")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Bcrypt {

}
