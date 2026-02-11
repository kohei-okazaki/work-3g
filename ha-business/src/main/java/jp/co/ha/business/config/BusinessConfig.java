package jp.co.ha.business.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jp.co.ha.business.io.file.json.conf.JsonConfig;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.aws.AwsProperties;
import software.amazon.awssdk.regions.Region;

/**
 * businessプロジェクトのConfigクラス
 * 
 * @version 1.0.0
 */
@Configuration(proxyBeanMethods = false)
// application-$env.ymlで読み込んでいるため不要
// @PropertySource({
// "classpath:jdbc.properties",
// "classpath:system.properties",
// "classpath:aws.properties",
// "classpath:healthInfo.properties"
// })
@ComponentScan(basePackages = {
        "jp.co.ha.business.api",
        "jp.co.ha.business.cache",
        "jp.co.ha.business.component",
        "jp.co.ha.business.db.crud",
        "jp.co.ha.business.*.service"
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
     * @param trackApiDbMigrateFlg
     *     健康情報蓄積API DB移行フラグ
     * @return HealthInfoProperties
     */
    @Bean
    HealthInfoProperties healthInfoProperties(
            @Value("${reference.file.path}") String referenceFilePath,
            @Value("${healthinfo.node.api.url}") String healthinfoNodeApiUrl,
            @Value("${healthinfo.node.api.migrate.flg}") boolean healthinfoNodeApiMigrateFlg,
            @Value("${healthinfo.dashboard.url}") String healthInfoDashboardUrl,
            @Value("${healthinfo.api.url}") String healthInfoApiUrl,
            @Value("${root.api.url}") String rootApiUrl,
            @Value("${healthinfo.track.api.url}") String trackApiUrl,
            @Value("${healthinfo.track.api.db.migrate.flg}") boolean trackApiDbMigrateFlg) {
        return new HealthInfoProperties(referenceFilePath, healthinfoNodeApiUrl,
                healthinfoNodeApiMigrateFlg, healthInfoDashboardUrl, healthInfoApiUrl,
                rootApiUrl, trackApiUrl, trackApiDbMigrateFlg);
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
     * @param apiLogQueueName
     *     キュー名:API通信ログ
     * @param sqsConnnectionTimeout
     *     SQSコネクションタイムアウト
     * @param sqsSocketTimeout
     *     API通信情報キュー名
     * @param ssmConnnectionTimeout
     *     SSMコネクションタイムアウト
     * @param ssmSocketTimeout
     *     SSMソケットタイムアウト
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
            @Value("${aws.sqs.queue.api_log}") String apiLogQueueName,
            @Value("${aws.sqs.connection.timeout}") int sqsConnnectionTimeout,
            @Value("${aws.sqs.socket.timeout}") int sqsSocketTimeout,
            @Value("${aws.ssm.connection.timeout}") int ssmConnnectionTimeout,
            @Value("${aws.ssm.socket.timeout}") int ssmSocketTimeout) {
        return new AwsProperties(
                Region.of(region),
                backet,
                s3ConnnectionTimeout,
                s3SocketTimeout,
                sesConnnectionTimeout,
                sesSocketTimeout,
                sesStubFlag,
                apiLogQueueName,
                sqsConnnectionTimeout,
                sqsSocketTimeout,
                ssmConnnectionTimeout,
                ssmSocketTimeout);
    }

    /**
     * @return JsonConfig
     */
    @Bean
    JsonConfig jsonConfig() {
        return new JsonConfig();
    }
}
