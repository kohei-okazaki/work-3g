package jp.co.ha.common.aws;

import static jp.co.ha.common.exception.CommonErrorCode.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.common.exception.SystemRuntimeException;
import jp.co.ha.common.system.SystemProperties;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;

/**
 * AWS認証情報のComponent<br>
 * ※ローカルPCの場合、事前にCLI用のIAMユーザを設定しておくこと
 *
 * @version 1.0.0
 */
@Component
public class AwsAuthComponent {

    /** システム設定ファイル情報 */
    @Autowired
    private SystemProperties systemProps;

    /**
     * システム設定ファイル情報.環境より、以下の{@linkplain AwsCredentialsProvider}インスタンスを返す<br>
     * <ul>
     * <li>ローカル環境の場合、{@linkplain ProfileCredentialsProvider}</li>
     * <li>dev1環境の場合、{@linkplain InstanceProfileCredentialsProvider}</li>
     * </ul>
     *
     * @return AwsCredentialsProvider
     */
    public AwsCredentialsProvider getProvider() {
        switch (systemProps.getEnvironment()) {
        case LOCAL:
            return ProfileCredentialsProvider.create();
        case DEV1:
            return InstanceProfileCredentialsProvider.create();
        default:
            // システムプロパティの環境がNullや実行可能環境でない場合
            throw new SystemRuntimeException(UNEXPECTED_ERROR,
                    "環境情報の設定が不正です。env=" + systemProps.getEnvironment());
        }
    }

}
