package jp.co.ha.business.aws;

import org.springframework.stereotype.Component;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;

/**
 * AWS認証情報のComponent<br>
 * ※事前にAWS-CLIでローカルPCまたは仮想マシンに対し、CLI用のIAMユーザを設定しておくこと
 *
 * @version 1.0.0
 */
@Component
public class AwsAuthComponent {

    /**
     * {@linkplain ProfileCredentialsProvider}を返す
     *
     * @return ProfileCredentialsProvider
     */
    public ProfileCredentialsProvider getProfileCredentialsProvider() {
        return new ProfileCredentialsProvider();
    }

}
