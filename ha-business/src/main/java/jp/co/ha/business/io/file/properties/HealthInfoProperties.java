package jp.co.ha.business.io.file.properties;

import org.springframework.stereotype.Component;

/**
 * 健康情報設定ファイルクラス<br>
 * 設定ファイル名:healthinfo.properties<br>
 *
 * @version 1.0.0
 */
@Component
public class HealthInfoProperties {

    /** 照会ファイル格納パス */
    private String referenceFilePath;
    /** NodeAPIの基底URL */
    private String healthinfoNodeApiUrl;
    /** 健康情報ダッシュボードの基底URL */
    private String healthInfoDashboardUrl;
    /** 健康情報APIの基底URL */
    private String healthInfoApiUrl;
    /** NodeAPI 移行フラグ */
    private boolean healthinfoNodeApiMigrateFlg;
    /** 管理者用APIの基底URL */
    private String rootApiUrl;
    /** 健康情報蓄積APIの基底URL */
    private String trackApiUrl;
    /** 健康情報蓄積API DB移行フラグ */
    private boolean trackApiDbMigrateFlg;

    /**
     * 照会ファイル格納パスを返す
     *
     * @return referenceFilePath
     */
    public String getReferenceFilePath() {
        return referenceFilePath;
    }

    /**
     * 照会ファイル格納パスを設定する
     *
     * @param referenceFilePath
     *     照会ファイル格納パス
     */
    public void setReferenceFilePath(String referenceFilePath) {
        this.referenceFilePath = referenceFilePath;
    }

    /**
     * NodeAPIの基底URLを返す
     *
     * @return healthinfoNodeApiUrl
     */
    public String getHealthinfoNodeApiUrl() {
        return healthinfoNodeApiUrl;
    }

    /**
     * NodeAPIの基底URLを設定する
     *
     * @param healthinfoNodeApiUrl
     *     NodeAPIの基底URL
     */
    public void setHealthinfoNodeApiUrl(String healthinfoNodeApiUrl) {
        this.healthinfoNodeApiUrl = healthinfoNodeApiUrl;
    }

    /**
     * 健康情報ダッシュボードの基底URLを返す
     *
     * @return healthInfoDashboardUrl
     */
    public String getHealthInfoDashboardUrl() {
        return healthInfoDashboardUrl;
    }

    /**
     * 健康情報ダッシュボードの基底URLを設定する
     *
     * @param healthInfoDashboardUrl
     *     健康情報ダッシュボードの基底URL
     */
    public void setHealthInfoDashboardUrl(String healthInfoDashboardUrl) {
        this.healthInfoDashboardUrl = healthInfoDashboardUrl;
    }

    /**
     * 健康情報APIの基底URLを返す
     *
     * @return healthInfoApiUrl
     */
    public String getHealthInfoApiUrl() {
        return healthInfoApiUrl;
    }

    /**
     * 健康情報APIの基底URLを設定する
     *
     * @param healthInfoApiUrl
     *     健康情報APIの基底URL
     */
    public void setHealthInfoApiUrl(String healthInfoApiUrl) {
        this.healthInfoApiUrl = healthInfoApiUrl;
    }

    /**
     * NodeAPI 移行フラグを返す
     * 
     * @return healthinfoNodeApiMigrateFlg
     */
    public boolean isHealthinfoNodeApiMigrateFlg() {
        return healthinfoNodeApiMigrateFlg;
    }

    /**
     * NodeAPI 移行フラグを設定する
     * 
     * @param healthinfoNodeApiMigrateFlg
     *     NodeAPI 移行フラグ
     */
    public void setHealthinfoNodeApiMigrateFlg(String healthinfoNodeApiMigrateFlg) {
        this.healthinfoNodeApiMigrateFlg = Boolean.valueOf(healthinfoNodeApiMigrateFlg);
    }

    /**
     * NodeAPI 移行フラグを設定する
     * 
     * @param healthinfoNodeApiMigrateFlg
     *     NodeAPI 移行フラグ
     */
    public void setHealthinfoNodeApiMigrateFlg(boolean healthinfoNodeApiMigrateFlg) {
        this.healthinfoNodeApiMigrateFlg = healthinfoNodeApiMigrateFlg;
    }

    /**
     * 管理者用APIの基底URLを返す
     *
     * @return rootApiUrl
     */
    public String getRootApiUrl() {
        return rootApiUrl;
    }

    /**
     * 管理者用APIの基底URLを設定する
     *
     * @param rootApiUrl
     *     管理者用APIの基底URL
     */
    public void setRootApiUrl(String rootApiUrl) {
        this.rootApiUrl = rootApiUrl;
    }

    /**
     * 健康情報蓄積APIの基底URLを返す
     * 
     * @return trackApiUrl
     */
    public String getTrackApiUrl() {
        return trackApiUrl;
    }

    /**
     * 健康情報蓄積APIの基底URLを設定する
     * 
     * @param trackApiUrl
     *     健康情報蓄積APIの基底URL
     */
    public void setTrackApiUrl(String trackApiUrl) {
        this.trackApiUrl = trackApiUrl;
    }

    /**
     * 健康情報蓄積APIDB移行フラグを返す
     * 
     * @return trackApiDbMigrateFlg
     */
    public boolean isTrackApiDbMigrateFlg() {
        return trackApiDbMigrateFlg;
    }

    /**
     * 健康情報蓄積APIDB移行フラグを設定する
     * 
     * @param trackApiDbMigrateFlg
     *     健康情報蓄積APIDB移行フラグ
     */
    public void setTrackApiDbMigrateFlg(boolean trackApiDbMigrateFlg) {
        this.trackApiDbMigrateFlg = trackApiDbMigrateFlg;
    }

    /**
     * 健康情報蓄積APIDB移行フラグを設定する
     * 
     * @param trackApiDbMigrateFlg
     *     健康情報蓄積APIDB移行フラグ
     */
    public void setTrackApiDbMigrateFlg(String trackApiDbMigrateFlg) {
        this.trackApiDbMigrateFlg = Boolean.valueOf(trackApiDbMigrateFlg);
    }

}
