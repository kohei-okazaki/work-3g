package jp.co.ha.common.config;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import jp.co.ha.common.db.CryptProperties;
import jp.co.ha.common.db.JdbcProperties;
import jp.co.ha.common.system.SystemProperties;
import jp.co.ha.common.validator.BeanValidator;
import jp.co.ha.common.web.interceptor.RequestInterceptor;

/**
 * commonプロジェクトのConfigクラス
 * 
 * @version 1.0.0
 */
@Configuration
// application-$env.ymlで読み込んでいるため不要
// @PropertySource({ "classpath:crypt.properties", "classpath:jdbc.properties",
// "classpath:system.properties" })
@ComponentScan(basePackages = {
        "jp.co.ha.common.system",
        "jp.co.ha.common.crypt",
        "jp.co.ha.common.db",
        "jp.co.ha.common.io",
        "jp.co.ha.common.web.api.aspect"
})
// @PropertySource("classpath:mail.properties")
public class CommonConfig implements WebMvcConfigurer {

    /** Freemarkerテンプレートパス */
    @Value("${freemarker.template-loader-path}")
    private String templateLoaderPath;

    /** Freemarkerデフォルトエンコード */
    @Value("${freemarker.default-encoding}")
    private String defaultEncoding;

    /**
     * @return FreeMarkerConfigurer
     */
    @Bean
    FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath(templateLoaderPath);
        configurer.setDefaultEncoding(defaultEncoding);
        return configurer;
    }

    /**
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
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
    @SuppressWarnings("rawtypes")
    BeanValidator beanValidator() {
        return new BeanValidator();
    }

    /**
     * @return PropertySourcesPlaceholderConfigurer
     */
    @Bean
    static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * @param driverClassName
     * @param url
     * @param username
     * @param password
     * @return JdbcProperties
     */
    @Bean
    JdbcProperties jdbcProperties(
            @Value("${jdbc.driverClassName}") String driverClassName,
            @Value("${jdbc.url}") String url,
            @Value("${jdbc.username}") String username,
            @Value("${jdbc.password}") String password) {

        JdbcProperties jdbcConfig = new JdbcProperties();
        jdbcConfig.setDriverClassName(driverClassName);
        jdbcConfig.setUrl(url);
        jdbcConfig.setUsername(username);
        jdbcConfig.setPassword(password);
        return jdbcConfig;
    }

    /**
     * @param paging
     * @param environment
     * @param systemMailAddress
     * @return SystemProperties
     */
    @Bean
    SystemProperties systemProperties(
            @Value("${system.paging}") String paging,
            @Value("${system.env}") String environment,
            @Value("${system.mailaddress}") String systemMailAddress) {

        SystemProperties systemConfig = new SystemProperties();
        systemConfig.setPaging(paging);
        systemConfig.setEnvironment(environment);
        systemConfig.setSystemMailAddress(systemMailAddress);
        return systemConfig;
    }

    /**
     * @param mode
     * @param key
     * @param shift
     * @return CryptProperties
     */
    @Bean
    CryptProperties cryptProperties(
            @Value("${crypt.mode}") String mode,
            @Value("${crypt.key}") String key,
            @Value("${crypt.shift}") String shift) {

        CryptProperties cryptConfig = new CryptProperties();
        cryptConfig.setMode(mode);
        cryptConfig.setKey(key);
        cryptConfig.setShift(shift);
        return cryptConfig;
    }
}
