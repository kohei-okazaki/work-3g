package jp.co.ha.business.aws;

import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;

/**
 * AWSの設定情報保持クラス
 *
 * @version 1.0.0
 */
@Component
public class AwsConfig {

    /** アクセスキー */
    private String accesskey;
    /** シークレットアクセスキー */
    private String secretAccesskey;
    /** リージョン */
    private Regions region;

    /**
     * accesskeyを返す
     *
     * @return accesskey
     */
    public String getAccesskey() {
        return accesskey;
    }

    /**
     * accesskeyを設定する
     *
     * @param accesskey
     *     アクセスキー
     */
    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    /**
     * secretAccesskeyを返す
     *
     * @return secretAccesskey
     */
    public String getSecretAccesskey() {
        return secretAccesskey;
    }

    /**
     * secretAccesskeyを設定する
     *
     * @param secretAccesskey
     *     シークレットアクセスキー
     */
    public void setSecretAccesskey(String secretAccesskey) {
        this.secretAccesskey = secretAccesskey;
    }

    /**
     * regionを返す
     *
     * @return region
     */
    public Regions getRegion() {
        return region;
    }

    /**
     * regionを設定する
     *
     * @param region
     *     リージョン
     */
    public void setRegion(String region) {
        this.region = Regions.fromName(region);
    }

}
