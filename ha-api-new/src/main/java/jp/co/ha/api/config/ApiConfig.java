package jp.co.ha.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * APIアプリケーションの設定クラス
 * 
 * @version 1.0.0
 */
@Configuration
@ComponentScan(basePackages = {
        "jp.co.ha.api.*.controller"
})
@Import({
        jp.co.ha.common.config.CommonConfig.class,
        jp.co.ha.db.config.DbConfig.class,
        jp.co.ha.business.config.BusinessConfig.class
})
public class ApiConfig implements WebMvcConfigurer {

    /**
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder builder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        AuthenticationManager manager = builder.build();

        http
                .authenticationManager(manager)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(csrf -> csrf.disable())
                .formLogin(e -> e.disable());

        return http.build();
    }

    /**
     * @return ResourceBundleMessageSource
     */
    @Bean
    ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages", "apiErrorCode", "businessErrorCode",
                "commonErrorCode", "validateErrorCode");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
