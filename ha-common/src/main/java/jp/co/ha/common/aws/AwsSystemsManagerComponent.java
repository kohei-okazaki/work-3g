package jp.co.ha.common.aws;

import static jp.co.ha.common.exception.CommonErrorCode.*;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.system.SystemProperties;
import jp.co.ha.common.util.StringUtil;
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

    /** キー：Slack Token */
    public static final String KEY_SLACK_TOKEN = "SLACK_TOKEN";
    /** キー：システムメールアドレス */
    public static final String KEY_SYSTEM_MAILADDRESS = "SYSTEM_MAILADDRESS";
    /** キー：暗号化モード */
    public static final String KEY_CRYPT_MODE = "CRYPT_MODE";
    /** キー：暗号化キー */
    public static final String KEY_CRYPT_KEY = "CRYPT_KEY";
    /** キー：シフト数 */
    public static final String KEY_CRYPT_SHIFT = "CRYPT_SHIFT";
    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(AwsSystemsManagerComponent.class);

    /** AWS設定ファイル情報 */
    @Autowired
    private AwsProperties awsProps;
    /** AWS認証情報Component */
    @Autowired
    private AwsAuthComponent auth;
    /** システム設定ファイル情報 */
    @Autowired
    private SystemProperties systemProps;

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

        String envKey = StringUtil.THRASH
                + systemProps.getEnvironment().getValue().toUpperCase()
                + StringUtil.THRASH + key;
        LOG.debug("key=" + envKey + ", decrypt=" + decrypt);

        GetParameterResponse res = getSsmClient()
                .getParameter(GetParameterRequest.builder()
                        .name(envKey)
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
                    .credentialsProvider(auth.getProvider())
                    .httpClient(httpClient)
                    .build();

        } catch (Exception e) {
            throw new SystemException(AWS_CLIENT_CONNECT_ERROR, e);
        }
    }
}
