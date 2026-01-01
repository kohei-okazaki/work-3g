package jp.co.ha.common.config;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
        "jp.co.ha.common.aws",
        "jp.co.ha.common.crypt",
        "jp.co.ha.common.db",
        "jp.co.ha.common.io",
        "jp.co.ha.common.system",
        "jp.co.ha.common.web.api.aspect"
})
public class CommonConfig implements WebMvcConfigurer {

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
     * JdbcPropertiesを設定する
     * 
     * @param driverClassName
     *     driverClassName
     * @param url
     *     url
     * @param username
     *     username
     * @param password
     *     password
     * @return JdbcProperties
     */
    @Bean
    JdbcProperties jdbcProperties(
            @Value("${jdbc.driverClassName}") String driverClassName,
            @Value("${jdbc.url}") String url,
            @Value("${jdbc.username}") String username,
            @Value("${jdbc.password}") String password) {

        JdbcProperties props = new JdbcProperties();
        props.setDriverClassName(driverClassName);
        props.setUrl(url);
        props.setUsername(username);
        props.setPassword(password);
        return props;
    }

    /**
     * SystemPropertiesを設定する
     * 
     * @param paging
     *     ページング数
     * @param environment
     *     環境
     * @return SystemProperties
     */
    @Bean
    SystemProperties systemProperties(
            @Value("${system.paging}") String paging,
            @Value("${system.env}") String environment) {

        SystemProperties props = new SystemProperties();
        props.setPaging(paging);
        props.setEnvironment(environment);
        return props;
    }

    /**
     * CryptPropertiesを設定する
     * 
     * @param mode
     *     mode
     * @param key
     *     key
     * @param shift
     *     shift
     * @return CryptProperties
     */
    @Bean
    CryptProperties cryptProperties(
            @Value("${crypt.mode}") String mode,
            @Value("${crypt.key}") String key,
            @Value("${crypt.shift}") String shift) {

        CryptProperties props = new CryptProperties();
        props.setMode(mode);
        props.setKey(key);
        props.setShift(shift);
        return props;
    }
}
