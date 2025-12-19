package jp.co.ha.business.api.aws;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

/**
 * AWS-Systems ManagerのComponent
 * 
 * @version 1.0.0
 */
@Component
public class AwsSystemsManagerComponent {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(AwsSystemsManagerComponent.class);
    /** キー：Slack Token */
    public static final String KEY_SLACK_TOKEN = "SLACK_TOKEN";
    /** キー：システムメールアドレス */
    public static final String KEY_SYSTEM_MAILADDRESS = "SYSTEM_MAILADDRESS";

    /** AWS設定ファイル情報 */
    @Autowired
    private AwsProperties awsProps;
    /** AWS認証情報Component */
    @Autowired
    private AwsAuthComponent auth;

    /**
     * Systems Managerに登録されている値を取得
     * 
     * @param key
     *     キー
     * @return 値
     * @throws BaseException
     *     Systems Managerから値の取得に失敗した場合
     */
    public String getValue(String key) throws BaseException {
        return this.getValue(key, false);
    }

    /**
     * Systems Managerに登録されている値を取得
     * 
     * @param key
     *     キー
     * @param decrypt
     *     キーが暗号化項目(secure string)の場合、trueを指定
     * @return 値
     * @throws BaseException
     *     Systems Managerから値の取得に失敗した場合
     */
    public String getValue(String key, boolean decrypt) throws BaseException {
        LOG.debug("key=" + key + ", decrypt=" + decrypt);
        GetParameterResponse res = getSsmClient()
                .getParameter(GetParameterRequest.builder()
                        .name(key)
                        .withDecryption(decrypt)
                        .build());
        return res.parameter().value();
    }

    /**
     * {@linkplain SsmClient}を返す
     * 
     * @return SsmClient
     * @throws BaseException
     *     AWSクライアント接続エラー
     */
    private SsmClient getSsmClient() throws BaseException {

        try {
            // HttpClient にタイムアウトを設定する
            SdkHttpClient httpClient = ApacheHttpClient.builder()
                    .connectionTimeout(
                            Duration.ofMillis(awsProps.getSsmConnnectionTimeout()))
                    .socketTimeout(Duration.ofMillis(awsProps.getSsmSocketTimeout()))
                    .build();

            return SsmClient.builder()
                    .region(awsProps.getRegion())
                    .credentialsProvider(auth.getAWSCredentialsProvider())
                    .httpClient(httpClient)
                    .build();

        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.AWS_CLIENT_CONNECT_ERROR, e);
        }
    }
}
