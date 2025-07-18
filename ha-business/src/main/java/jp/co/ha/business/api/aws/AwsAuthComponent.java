package jp.co.ha.business.api.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemRuntimeException;
import jp.co.ha.common.system.SystemConfig;
import jp.co.ha.common.util.BeanUtil;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;

/**
 * AWS認証情報のComponent<br>
 * ※事前にAWS-CLIでローカルPCに対し、CLI用のIAMユーザを設定しておくこと
 *
 * @version 1.0.0
 */
@Component
public class AwsAuthComponent {

    /** {@linkplain SystemConfig} */
    @Autowired
    private SystemConfig systemConfig;

    /**
     * システムプロパティ.環境より、以下の{@linkplain AwsCredentialsProvider}インスタンスを返す<br>
     * <ul>
     * <li>ローカル環境の場合、{@linkplain ProfileCredentialsProvider}</li>
     * <li>dev1環境の場合、{@linkplain InstanceProfileCredentialsProvider}</li>
     * </ul>
     *
     * @return AwsCredentialsProvider
     */
    public AwsCredentialsProvider getAWSCredentialsProvider() {

        if (BeanUtil.notNull(systemConfig.getEnvironment())) {
            switch (systemConfig.getEnvironment()) {
            case LOCAL:
                return ProfileCredentialsProvider.create();
            case DEV1:
                return InstanceProfileCredentialsProvider.create();
            }
        }

        // システムプロパティの環境がNullや実行可能環境でない場合
        throw new SystemRuntimeException(CommonErrorCode.UNEXPECTED_ERROR,
                "環境情報の設定が不正です。env=" + systemConfig.getEnvironment());
    }

}
