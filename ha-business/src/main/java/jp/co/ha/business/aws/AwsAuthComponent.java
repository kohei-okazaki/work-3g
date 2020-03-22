package jp.co.ha.business.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;

/**
 * AWS認証情報のComponent
 *
 * @version 1.0
 */
@Component
public class AwsAuthComponent {

    /** AWS個別設定情報 */
    @Autowired
    private AwsConfig awsConfig;

    /**
     * AWSCredentialsを返す
     *
     * @return AWSCredentials
     */
    public AWSCredentials getCredentials() {
        return new BasicAWSCredentials(awsConfig.getAccesskey(),
                awsConfig.getSecretAccesskey());
    }
}
