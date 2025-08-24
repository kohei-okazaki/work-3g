package jp.co.ha.business.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jp.co.ha.business.api.aws.AwsProperties;
import jp.co.ha.business.io.file.json.conf.JsonConfig;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;

/**
 * businessプロジェクトのConfigクラス
 * 
 * @version 1.0.0
 */
@Configuration(proxyBeanMethods = false)
// application-$env.ymlで読み込んでいるため不要
// @PropertySource({
// "classpath:crypt.properties",
// "classpath:jdbc.properties",
// "classpath:system.properties",
// "classpath:aws.properties",
// "classpath:healthInfo.properties"
// })
@ComponentScan(basePackages = {
        "jp.co.ha.business.db.crud",
        "jp.co.ha.business.api",
        "jp.co.ha.business.component",
        "jp.co.ha.business.cache",
        "jp.co.ha.business.healthInfo.service",
        "jp.co.ha.business.interceptor"
})
// @PropertySource("classpath:mail.properties")
public class BusinessConfig implements WebMvcConfigurer {

    /**
     * <pre>
     * ※※※※※application.ymlのspring.config.importや
     * 本クラスでの@PropertySource("classpath:mail.properties")では読み込めないため
     * 各親プロジェクトで
     * freemarker:
     *   template-loader-path: classpath:/templates/mail-templates/
     *   default-encoding: UTF-8
     *   suffix: .ftl 
     *   charset: UTF-8 
     *   cache: false
     * </pre>
     */

    // /** Freemarkerテンプレートパス */
    // @Value("${freemarker.template-loader-path}")
    // private String templateLoaderPath;
    //
    // /** Freemarkerデフォルトエンコード */
    // @Value("${freemarker.default-encoding}")
    // private String defaultEncoding;
    //
    // /**
    // * @return FreeMarkerConfigurer
    // */
    // @Bean
    // FreeMarkerConfigurer freeMarkerConfigurer() {
    // FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
    // configurer.setTemplateLoaderPath(templateLoaderPath);
    // configurer.setDefaultEncoding(defaultEncoding);
    // return configurer;
    // }

    /**
     * @return PropertySourcesPlaceholderConfigurer
     */
    @Bean
    PropertySourcesPlaceholderConfigurer properties() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * @param referenceFilePath
     * @param registBatchFilePath
     * @param healthinfoNodeApiUrl
     * @param healthinfoNodeApiMigrateFlg
     * @param healthInfoDashboardUrl
     * @param healthInfoApiUrl
     * @param rootApiUrl
     * @param trackApiUrl
     * @param monthlySummaryBatchFilePath
     * @return HealthInfoProperties
     */
    @Bean
    HealthInfoProperties healthInfoProperties(
            @Value("${reference.file.path}") String referenceFilePath,
            @Value("${healthinfo.regist.batch.file.path}") String registBatchFilePath,
            @Value("${healthinfo.node.api.url}") String healthinfoNodeApiUrl,
            @Value("${healthinfo.node.api.migrate.flg}") boolean healthinfoNodeApiMigrateFlg,
            @Value("${healthinfo.dashboard.url}") String healthInfoDashboardUrl,
            @Value("${healthinfo.api.url}") String healthInfoApiUrl,
            @Value("${root.api.url}") String rootApiUrl,
            @Value("${healthinfo.track.api.url}") String trackApiUrl,
            @Value("${healthinfo.monthly.summary.batch.file.path}") String monthlySummaryBatchFilePath) {

        HealthInfoProperties props = new HealthInfoProperties();
        props.setReferenceFilePath(referenceFilePath);
        props.setRegistBatchFilePath(registBatchFilePath);
        props.setHealthinfoNodeApiUrl(healthinfoNodeApiUrl);
        props.setHealthinfoNodeApiMigrateFlg(healthinfoNodeApiMigrateFlg);
        props.setHealthInfoDashboardUrl(healthInfoDashboardUrl);
        props.setHealthInfoApiUrl(healthInfoApiUrl);
        props.setRootApiUrl(rootApiUrl);
        props.setTrackApiUrl(trackApiUrl);
        props.setMonthlySummaryBatchFilePath(monthlySummaryBatchFilePath);

        return props;
    }

    /**
     * @param region
     * @param backet
     * @param s3Timeout
     * @param sesTimeout
     * @param sesStubFlag
     * @return AwsConfig
     */
    @Bean
    AwsProperties awsProperties(
            @Value("${aws.region}") String region,
            @Value("${aws.s3.backet}") String backet,
            @Value("${aws.s3.timeout}") int s3Timeout,
            @Value("${aws.ses.timeout}") int sesTimeout,
            @Value("${aws.ses.stubflag}") boolean sesStubFlag) {

        AwsProperties props = new AwsProperties();
        props.setRegion(region);
        props.setBacket(backet);
        props.setS3Timeout(s3Timeout);
        props.setSesTimeout(sesTimeout);
        props.setSesStubFlag(sesStubFlag);

        return props;
    }

    /**
     * @return JsonConfig
     */
    @Bean
    JsonConfig jsonConfig() {
        return new JsonConfig();
    }
}
