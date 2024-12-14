package jp.co.ha.common.config;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jp.co.ha.common.db.CryptConfig;
import jp.co.ha.common.db.JdbcConfig;
import jp.co.ha.common.system.SystemConfig;
import jp.co.ha.common.validator.BeanValidator;
import jp.co.ha.common.web.interceptor.RequestInterceptor;

/**
 * commonプロジェクトのConfigクラス
 * 
 * @version 1.0.0
 */
@Configuration
@PropertySource({ "classpath:crypt.properties", "classpath:jdbc.properties",
        "classpath:system.properties" })
@ComponentScan(basePackages = {
        "jp.co.ha.common.system",
        "jp.co.ha.common.crypt",
        "jp.co.ha.common.db",
        "jp.co.ha.common.io",
        "jp.co.ha.common.web.api.aspect"
})
public class CommonConfig implements WebMvcConfigurer {

    /**
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor()).addPathPatterns("/**");
    }

    /**
     * @return BeanValidator
     */
    @Bean
    public BeanValidator beanValidator() {
        return new BeanValidator();
    }

    /**
     * @return PropertySourcesPlaceholderConfigurer
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * @param driverClassName
     * @param url
     * @param username
     * @param password
     * @return JdbcConfig
     */
    @Bean
    public JdbcConfig jdbcConfig(
            @Value("${jdbc.driverClassName}") String driverClassName,
            @Value("${jdbc.url}") String url,
            @Value("${jdbc.username}") String username,
            @Value("${jdbc.password}") String password) {

        JdbcConfig jdbcConfig = new JdbcConfig();
        jdbcConfig.setDriverClassName(driverClassName);
        jdbcConfig.setUrl(url);
        jdbcConfig.setUsername(username);
        jdbcConfig.setPassword(password);
        return jdbcConfig;
    }

    /**
     * @param paging
     * @param environment
     * @return SystemConfig
     */
    @Bean
    public SystemConfig systemConfig(
            @Value("${system.paging}") String paging,
            @Value("${system.env}") String environment) {

        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setPaging(paging);
        systemConfig.setEnvironment(environment);
        return systemConfig;
    }

    /**
     * @param mode
     * @param key
     * @param shift
     * @return CryptConfig
     */
    @Bean
    public CryptConfig cryptConfig(
            @Value("${crypt.mode}") String mode,
            @Value("${crypt.key}") String key,
            @Value("${crypt.shift}") String shift) {

        CryptConfig cryptConfig = new CryptConfig();
        cryptConfig.setMode(mode);
        cryptConfig.setKey(key);
        cryptConfig.setShift(shift);
        return cryptConfig;
    }
}
