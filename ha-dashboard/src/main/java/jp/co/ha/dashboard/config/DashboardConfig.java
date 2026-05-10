package jp.co.ha.dashboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jp.co.ha.business.component.DashboardAuthInterceptor;
import jp.co.ha.business.config.BusinessConfig;
import jp.co.ha.common.config.CommonConfig;
import jp.co.ha.common.type.Charset;
import jp.co.ha.dashboard.login.auth.DashboardAuthenticationFailureHandler;
import jp.co.ha.dashboard.login.auth.DashboardAuthenticationProvider;
import jp.co.ha.dashboard.login.auth.DashboardAuthenticationSuccessHandler;
import jp.co.ha.db.config.DbConfig;

/**
 * ダッシュボードアプリケーションの設定クラス
 * 
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
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

    /** authentication ignore url */
    private static final String[] AUTH_IGNORE_URL = new String[] {
            "/login",
            "/accountregist/**",
            "/accountrecovery/**",
            "/error",
            "/static/**"
    };

    /** 認証Interceptor */
    @Autowired
    private DashboardAuthInterceptor authInterceptor;
    /**
     * securityFilterChain
     * 
     * @param http
     *     HttpSecurity
     * @param authenticationProvider
     *     authentication provider
     * @param authenticationSuccessHandler
     *     authentication success handler
     * @param authenticationFailureHandler
     *     authentication failure handler
     * @return SecurityFilterChain
     * @throws Exception
     *     認証処理失敗
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
            DashboardAuthenticationProvider authenticationProvider,
            DashboardAuthenticationSuccessHandler authenticationSuccessHandler,
            DashboardAuthenticationFailureHandler authenticationFailureHandler)
            throws Exception {

        http
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_IGNORE_URL).permitAll()
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("mailAddress")
                        .passwordParameter("password")
                        .successHandler(authenticationSuccessHandler)
                        .failureHandler(authenticationFailureHandler)
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(PathPatternRequestMatcher
                                .withDefaults().matcher(HttpMethod.GET, "/logout"))
                        .logoutSuccessUrl("/login?isLogout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());

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
        messageSource.setDefaultEncoding(Charset.UTF_8.getValue());
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
