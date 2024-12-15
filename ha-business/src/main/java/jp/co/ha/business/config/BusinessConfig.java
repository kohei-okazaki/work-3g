package jp.co.ha.business.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jp.co.ha.business.api.aws.AwsConfig;
import jp.co.ha.business.io.file.json.conf.JsonConfig;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;

/**
 * businessプロジェクトのConfigクラス
 * 
 * @version 1.0.0
 */
@Configuration
@PropertySource({
        "classpath:crypt.properties",
        "classpath:jdbc.properties",
        "classpath:system.properties",
        "classpath:aws.properties",
        "classpath:healthInfo.properties"
})
@ComponentScan(basePackages = {
        "jp.co.ha.business.db.crud",
        "jp.co.ha.business.api",
        "jp.co.ha.business.component",
        "jp.co.ha.business.cache",
        "jp.co.ha.business.healthInfo.service",
        "jp.co.ha.business.interceptor"
})
public class BusinessConfig implements WebMvcConfigurer {

    /**
     * @return PropertySourcesPlaceholderConfigurer
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * @param referenceFilePath
     * @param registBatchFilePath
     * @param healthinfoNodeApiUrl
     * @param healthInfoDashboardUrl
     * @param healthInfoApiUrl
     * @param rootApiUrl
     * @param trackApiUrl
     * @param monthlySummaryBatchFilePath
     * @return HealthInfoProperties
     */
    @Bean
    public HealthInfoProperties healthInfoProperties(
            @Value("${reference.file.path}") String referenceFilePath,
            @Value("${healthinfo.regist.batch.file.path}") String registBatchFilePath,
            @Value("${healthinfo.node.api.url}") String healthinfoNodeApiUrl,
            @Value("${healthinfo.dashboard.url}") String healthInfoDashboardUrl,
            @Value("${healthinfo.api.url}") String healthInfoApiUrl,
            @Value("${root.api.url}") String rootApiUrl,
            @Value("${healthinfo.track.api.url}") String trackApiUrl,
            @Value("${healthinfo.monthly.summary.batch.file.path}") String monthlySummaryBatchFilePath) {
        HealthInfoProperties properties = new HealthInfoProperties();
        properties.setReferenceFilePath(referenceFilePath);
        properties.setRegistBatchFilePath(registBatchFilePath);
        properties.setHealthinfoNodeApiUrl(healthinfoNodeApiUrl);
        properties.setHealthInfoDashboardUrl(healthInfoDashboardUrl);
        properties.setHealthInfoApiUrl(healthInfoApiUrl);
        properties.setRootApiUrl(rootApiUrl);
        properties.setTrackApiUrl(trackApiUrl);
        properties.setMonthlySummaryBatchFilePath(monthlySummaryBatchFilePath);
        return properties;
    }

    /**
     * @param region
     * @param backet
     * @param s3Timeout
     * @param mailAddress
     * @param sesTimeout
     * @param sesStubFlag
     * @return AwsConfig
     */
    @Bean
    public AwsConfig awsConfig(
            @Value("${aws.region}") String region,
            @Value("${aws.s3.backet}") String backet,
            @Value("${aws.s3.timeout}") int s3Timeout,
            @Value("${aws.ses.mailaddress}") String mailAddress,
            @Value("${aws.ses.timeout}") int sesTimeout,
            @Value("${aws.ses.stubflag}") boolean sesStubFlag) {
        AwsConfig awsConfig = new AwsConfig();
        awsConfig.setRegion(region);
        awsConfig.setBacket(backet);
        awsConfig.setS3Timeout(s3Timeout);
        awsConfig.setMailAddress(mailAddress);
        awsConfig.setSesTimeout(sesTimeout);
        awsConfig.setSesStubFlag(sesStubFlag);
        return awsConfig;
    }

    /**
     * @return JsonConfig
     */
    @Bean
    public JsonConfig jsonConfig() {
        return new JsonConfig();
    }
}
