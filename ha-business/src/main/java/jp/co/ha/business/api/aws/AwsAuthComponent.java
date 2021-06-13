package jp.co.ha.business.api.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemRuntimeException;
import jp.co.ha.common.system.SystemConfig;
import jp.co.ha.common.util.BeanUtil;

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
     * システムプロパティ.環境より、以下の{@linkplain AWSCredentialsProvider}インスタンスを返す<br>
     * <ul>
     * <li>ローカル環境の場合、{@linkplain ProfileCredentialsProvider}</li>
     * <li>EC2環境の場合、{@linkplain InstanceProfileCredentialsProvider}</li>
     * </ul>
     *
     * @return AWSCredentialsProvider
     */
    public AWSCredentialsProvider getAWSCredentialsProvider() {

        if (BeanUtil.notNull(systemConfig.getEnvironment())) {
            switch (systemConfig.getEnvironment()) {
            case LOCAL:
                return getProfileCredentialsProvider();
            case EC2:
                return getInstanceProfileCredentialsProvider();
            }
        }

        // システムプロパティの環境がNullや実行可能環境でない場合
        throw new SystemRuntimeException(CommonErrorCode.UNEXPECTED_ERROR,
                "環境情報の設定が不正です。env=" + systemConfig.getEnvironment());
    }

    /**
     * {@linkplain ProfileCredentialsProvider}を返す
     *
     * @return ProfileCredentialsProvider
     */
    public ProfileCredentialsProvider getProfileCredentialsProvider() {
        return new ProfileCredentialsProvider();
    }

    /**
     * {@linkplain InstanceProfileCredentialsProvider}を返す
     *
     * @return InstanceProfileCredentialsProvider
     */
    public InstanceProfileCredentialsProvider getInstanceProfileCredentialsProvider() {
        return new InstanceProfileCredentialsProvider(false);
    }

}
