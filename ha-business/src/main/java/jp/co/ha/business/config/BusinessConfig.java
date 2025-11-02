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
        "jp.co.ha.business.*.service",
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
     * HealthInfoPropertiesの設定
     * 
     * @param referenceFilePath
     *     照会ファイル格納パス
     * @param registBatchFilePath
     *     健康情報登録バッチファイルパス
     * @param healthinfoNodeApiUrl
     *     NodeAPIの基底URL
     * @param healthinfoNodeApiMigrateFlg
     *     NodeAPI 移行フラグ
     * @param healthInfoDashboardUrl
     *     健康情報ダッシュボードの基底URL
     * @param healthInfoApiUrl
     *     健康情報APIの基底URL
     * @param rootApiUrl
     *     管理者用APIの基底URL
     * @param trackApiUrl
     *     健康情報蓄積APIの基底URL
     * @param monthlySummaryBatchFilePath
     *     月次健康情報集計バッチCSV格納パス
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
     * AwsPropertiesの設定
     * 
     * @param region
     *     リージョン
     * @param backet
     *     バケット名
     * @param s3ConnnectionTimeout
     *     S3コネクションタイムアウト
     * @param s3SocketTimeout
     *     S3ソケットタイムアウト
     * @param sesConnnectionTimeout
     *     SESコネクションタイムアウト
     * @param sesSocketTimeout
     *     SESソケットタイムアウト
     * @param sesStubFlag
     *     SESスタブフラグ(true:メールを送信しない、false:送信する)
     * @param apiCommunicationDataQueueName
     *     API通信情報キュー名
     * @param sqsConnnectionTimeout
     *     SQSコネクションタイムアウト
     * @param sqsSocketTimeout
     *     SQSソケットタイムアウト
     * @return AwsProperties
     */
    @Bean
    AwsProperties awsProperties(
            @Value("${aws.region}") String region,
            @Value("${aws.s3.backet}") String backet,
            @Value("${aws.s3.connection.timeout}") int s3ConnnectionTimeout,
            @Value("${aws.s3.socket.timeout}") int s3SocketTimeout,
            @Value("${aws.ses.connection.timeout}") int sesConnnectionTimeout,
            @Value("${aws.ses.socket.timeout}") int sesSocketTimeout,
            @Value("${aws.ses.stubflag}") boolean sesStubFlag,
            @Value("${aws.sqs.queue.api_communication_data}") String apiCommunicationDataQueueName,
            @Value("${aws.sqs.connection.timeout}") int sqsConnnectionTimeout,
            @Value("${aws.sqs.socket.timeout}") int sqsSocketTimeout) {

        AwsProperties props = new AwsProperties();
        props.setRegion(region);
        props.setBacket(backet);
        props.setS3ConnnectionTimeout(s3ConnnectionTimeout);
        props.setS3SocketTimeout(s3SocketTimeout);
        props.setSesConnnectionTimeout(sesConnnectionTimeout);
        props.setSesSocketTimeout(sesSocketTimeout);
        props.setSesStubFlag(sesStubFlag);
        props.setApiCommunicationDataQueueName(apiCommunicationDataQueueName);
        props.setSqsConnnectionTimeout(sqsConnnectionTimeout);
        props.setSqsSocketTimeout(sqsSocketTimeout);

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
