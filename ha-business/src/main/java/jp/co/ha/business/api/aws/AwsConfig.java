package jp.co.ha.business.api.aws;

import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;

/**
 * AWSの設定情報保持クラス<br>
 * <ul>
 * <li>リージョン：設定ファイルで東京を設定</li>
 * <li>バケット名：環境毎にバケットを設定</li>
 * <li>S3タイムアウト：設定ファイルで10秒を設定</li>
 * <li>SESメールアドレス：アプリの送信元メールアドレス</li>
 * <li>SESタイムアウト：設定ファイルで10秒を設定</li>
 * <li>SESスタブフラグ：true:メールを送信しない、false:送信する</li>
 * </ul>
 *
 * @version 1.0.0
 */
@Component
public class AwsConfig {

    /** リージョン */
    private Regions region;
    /** バケット名 */
    private String backet;
    /** S3タイムアウト */
    private int s3Timeout;
    /** SESメールアドレス */
    private String mailAddress;
    /** SESタイムアウト */
    private int sesTimeout;
    /** SESスタブフラグ(true:メールを送信しない、false:送信する) */
    private boolean sesStubFlag;

    /**
     * {@linkplain Regions}を返す
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

    /**
     * backetを返す
     *
     * @return backet
     */
    public String getBacket() {
        return backet;
    }

    /**
     * backetを設定する
     *
     * @param backet
     *     バケット
     */
    public void setBacket(String backet) {
        this.backet = backet;
    }

    /**
     * s3Timeoutを返す
     *
     * @return s3Timeout
     */
    public int getS3Timeout() {
        return s3Timeout;
    }

    /**
     * s3Timeoutを設定する
     *
     * @param s3Timeout
     *     S3のタイムアウト
     */
    public void setS3Timeout(int s3Timeout) {
        this.s3Timeout = s3Timeout;
    }

    /**
     * mailAddressを返す
     *
     * @return mailAddress
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * mailAddressを設定する
     *
     * @param mailAddress
     *     SESメールアドレス
     */
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    /**
     * sesTimeoutを返す
     *
     * @return sesTimeout
     */
    public int getSesTimeout() {
        return sesTimeout;
    }

    /**
     * sesTimeoutを設定する
     *
     * @param sesTimeout
     *     SESタイムアウト
     */
    public void setSesTimeout(int sesTimeout) {
        this.sesTimeout = sesTimeout;
    }

    /**
     * sesStubFlagを返す
     *
     * @return sesStubFlag
     */
    public boolean isSesStubFlag() {
        return sesStubFlag;
    }

    /**
     * sesStubFlagを設定する
     *
     * @param sesStubFlag
     *     SESスタブフラグ(true:メールを送信しない、false:送信する)
     */
    public void setSesStubFlag(String sesStubFlag) {
        this.sesStubFlag = Boolean.valueOf(sesStubFlag);
    }

    public void setSesStubFlag(boolean sesStubFlag) {
        this.sesStubFlag = sesStubFlag;
    }

}
