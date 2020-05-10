package jp.co.ha.business.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

/**
 * AWS認証情報のComponent<br>
 * ※事前にAWS-CLIでローカルPCまたは仮想マシンに対し、CLI用のIAMユーザを設定しておくこと
 *
 * @version 1.0.0
 */
@Component
public class AwsAuthComponent {

    /** AWS個別設定情報 */
    @Autowired
    private AwsConfig awsConfig;

    /**
     * {@linkplain ProfileCredentialsProvider}を返す
     *
     * @return ProfileCredentialsProvider
     */
    public ProfileCredentialsProvider getProfileCredentialsProvider() {
        return new ProfileCredentialsProvider();
    }

    /**
     * {@linkplain AWSStaticCredentialsProvider}を返す<br>
     * TODO AWSの認証に直接AccessKeyとSecretAccessKey使うので削除すること
     *
     * @return AWSStaticCredentialsProvider
     */
    public AWSStaticCredentialsProvider getAWSStaticCredentialsProvider() {
        AWSCredentials credentials = new BasicAWSCredentials(awsConfig.getAccesskey(),
                awsConfig.getSecretAccesskey());
        return new AWSStaticCredentialsProvider(credentials);
    }

}
