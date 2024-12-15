package jp.co.ha.dashboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jp.co.ha.business.config.BusinessConfig;
import jp.co.ha.business.interceptor.DashboardAuthInterceptor;
import jp.co.ha.common.config.CommonConfig;
import jp.co.ha.db.config.DbConfig;

/**
 * ダッシュボードアプリケーションの設定クラス
 * 
 * @version 1.0.0
 */
@Configuration
@ComponentScan(basePackages = {
        "jp.co.ha.dashboard.*.controller",
        "jp.co.ha.dashboard.*.service.impl",
        "jp.co.ha.dashboard.exception"
})
// commonプロジェクトなどのbean定義を読込
@Import({
        CommonConfig.class,
        DbConfig.class,
        BusinessConfig.class
})
public class DashboardConfig implements WebMvcConfigurer {

    /** 認証Interceptor */
    @Autowired
    private DashboardAuthInterceptor authInterceptor;

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
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // 全リクエストを許可
                .csrf(csrf -> csrf.disable())
                .formLogin(e -> e.disable());

        return http.build();
    }

    /**
     * @return StandardServletMultipartResolver
     */
    @Bean
    StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    /**
     * @return ResourceBundleMessageSource
     */
    @Bean
    ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages", "apiErrorCode", "dashboardErrorCode",
                "businessErrorCode", "commonErrorCode", "validateErrorCode");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error").setViewName("index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
    }

}
