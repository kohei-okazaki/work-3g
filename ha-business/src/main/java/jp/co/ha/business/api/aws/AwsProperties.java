package jp.co.ha.business.api.aws;

import org.springframework.stereotype.Component;

import software.amazon.awssdk.regions.Region;

/**
 * AWSの設定情報保持クラス<br>
 * 設定ファイル名:aws.properties
 * <ul>
 * <li>リージョン：設定ファイルで東京を設定</li>
 * <li>バケット名：環境毎にバケットを設定</li>
 * <li>S3コネクションタイムアウト：設定ファイルで10秒を設定</li>
 * <li>S3ソケットタイムアウト：設定ファイルで10秒を設定</li>
 * <li>SESコネクションタイムアウト：設定ファイルで10秒を設定</li>
 * <li>SESソケットタイムアウト：設定ファイルで10秒を設定</li>
 * <li>SESスタブフラグ：true:メールを送信しない、false:送信する</li>
 * </ul>
 *
 * @version 1.0.0
 */
@Component
public class AwsProperties {

    /** リージョン */
    private Region region;
    /** バケット名 */
    private String backet;
    /** S3コネクションタイムアウト */
    private int s3ConnnectionTimeout;
    /** S3ソケットタイムアウト */
    private int s3SocketTimeout;
    /** SESコネクションタイムアウト */
    private int sesConnnectionTimeout;
    /** SESソケットタイムアウト */
    private int sesSocketTimeout;
    /** SESスタブフラグ(true:メールを送信しない、false:送信する) */
    private boolean sesStubFlag;

    /**
     * リージョンを返す
     *
     * @return region
     */
    public Region getRegion() {
        return region;
    }

    /**
     * リージョンを設定する<br>
     * (例: "ap-northeast-1")
     *
     * @param region
     *     リージョン
     */
    public void setRegion(String region) {
        this.region = Region.of(region);
    }

    /**
     * バケット名を返す
     *
     * @return backet
     */
    public String getBacket() {
        return backet;
    }

    /**
     * バケット名を設定する
     *
     * @param backet
     *     バケット名
     */
    public void setBacket(String backet) {
        this.backet = backet;
    }

    /**
     * S3コネクションタイムアウトを返す
     * 
     * @return s3ConnnectionTimeout
     */
    public int getS3ConnnectionTimeout() {
        return s3ConnnectionTimeout;
    }

    /**
     * S3コネクションタイムアウトを設定する
     * 
     * @param s3ConnnectionTimeout
     *     S3コネクションタイムアウト
     */
    public void setS3ConnnectionTimeout(int s3ConnnectionTimeout) {
        this.s3ConnnectionTimeout = s3ConnnectionTimeout;
    }

    /**
     * S3ソケットタイムアウトを返す
     * 
     * @return s3SocketTimeout
     */
    public int getS3SocketTimeout() {
        return s3SocketTimeout;
    }

    /**
     * S3ソケットタイムアウトを設定する
     * 
     * @param s3SocketTimeout
     *     S3ソケットタイムアウト
     */
    public void setS3SocketTimeout(int s3SocketTimeout) {
        this.s3SocketTimeout = s3SocketTimeout;
    }

    /**
     * SESコネクションタイムアウトを返す
     * 
     * @return sesConnnectionTimeout
     */
    public int getSesConnnectionTimeout() {
        return sesConnnectionTimeout;
    }

    /**
     * SESコネクションタイムアウトを設定する
     * 
     * @param sesConnnectionTimeout
     *     SESコネクションタイムアウト
     */
    public void setSesConnnectionTimeout(int sesConnnectionTimeout) {
        this.sesConnnectionTimeout = sesConnnectionTimeout;
    }

    /**
     * SESソケットタイムアウトを返す
     * 
     * @return sesSocketTimeout
     */
    public int getSesSocketTimeout() {
        return sesSocketTimeout;
    }

    /**
     * SESソケットタイムアウトを設定する
     * 
     * @param sesSocketTimeout
     *     SESソケットタイムアウト
     */
    public void setSesSocketTimeout(int sesSocketTimeout) {
        this.sesSocketTimeout = sesSocketTimeout;
    }

    /**
     * SESスタブフラグ(true:メールを送信しない、false:送信する)を返す
     *
     * @return sesStubFlag
     */
    public boolean isSesStubFlag() {
        return sesStubFlag;
    }

    /**
     * SESスタブフラグ(true:メールを送信しない、false:送信する)を設定する
     *
     * @param sesStubFlag
     *     SESスタブフラグ(true:メールを送信しない、false:送信する)
     */
    public void setSesStubFlag(String sesStubFlag) {
        this.sesStubFlag = Boolean.valueOf(sesStubFlag);
    }

    /**
     * SESスタブフラグ(true:メールを送信しない、false:送信する)を設定する
     *
     * @param sesStubFlag
     *     SESスタブフラグ(true:メールを送信しない、false:送信する)
     */
    public void setSesStubFlag(boolean sesStubFlag) {
        this.sesStubFlag = sesStubFlag;
    }

}
