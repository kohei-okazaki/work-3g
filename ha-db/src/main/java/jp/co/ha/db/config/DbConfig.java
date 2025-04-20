package jp.co.ha.db.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * dbプロジェクトのConfigクラス
 * 
 * @version 1.0.0
 */
@Configuration
@EnableTransactionManagement
@EnableCaching
@PropertySource("classpath:jdbc.properties")
@ComponentScan(basePackages = "jp.co.ha.db.aspect")
@MapperScan("jp.co.ha.db.mapper")
public class DbConfig {

    /** driverClassName */
    @Value("${jdbc.driverClassName}")
    private String driverClassName;
    /** url */
    @Value("${jdbc.url}")
    private String url;
    /** username */
    @Value("${jdbc.username}")
    private String username;
    /** password */
    @Value("${jdbc.password}")
    private String password;

    /**
     * @return BasicDataSource
     */
    @Bean(destroyMethod = "close")
    BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * @return PlatformTransactionManager
     */
    @Bean
    PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * @return SqlSessionFactoryBean
     */
    @Bean
    SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());
        sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return sqlSessionFactory;
    }

    /**
     * @return DefaultTransactionDefinition
     */
    @Bean
    DefaultTransactionDefinition transactionDefinition() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(
                DefaultTransactionDefinition.PROPAGATION_REQUIRED);
        definition
                .setIsolationLevel(DefaultTransactionDefinition.ISOLATION_READ_COMMITTED);
        definition.setReadOnly(false);
        return definition;
    }

    /**
     * @return CacheManager
     */
    @Bean
    CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<Cache> caches = new ArrayList<>();
        caches.add(new ConcurrentMapCache("bmiRangeMt"));
        cacheManager.setCaches(caches);
        return cacheManager;
    }

}
