package jp.co.ha.business.io.file.properties;

import org.springframework.stereotype.Component;

/**
 * 健康情報設定ファイル
 *
 * @version 1.0.0
 */
@Component
public class HealthInfoProperties {

    /** 照会ファイル格納パス */
    private String referenceFilePath;
    /** 健康情報登録バッチファイルパス */
    private String registBatchFilePath;
    /** NodeAPIの基底URL */
    private String healthinfoNodeApiUrl;
    /** 健康情報ダッシュボードの基底URL */
    private String healthInfoDashboardUrl;
    /** 健康情報APIの基底URL */
    private String healthInfoApiUrl;
    /** 管理者用APIの基底URL */
    private String rootApiUrl;
    /** 月次健康情報集計バッチCSV格納パス */
    private String monthlySummaryBatchFilePath;

    /**
     * referenceFilePathを返す
     *
     * @return referenceFilePath
     */
    public String getReferenceFilePath() {
        return referenceFilePath;
    }

    /**
     * referenceFilePathを設定する
     *
     * @param referenceFilePath
     *     照会ファイル格納パス
     */
    public void setReferenceFilePath(String referenceFilePath) {
        this.referenceFilePath = referenceFilePath;
    }

    /**
     * registBatchFilePathを返す
     *
     * @return registBatchFilePath
     */
    public String getRegistBatchFilePath() {
        return registBatchFilePath;
    }

    /**
     * registBatchFilePathを設定する
     *
     * @param registBatchFilePath
     *     健康情報登録バッチファイルパス
     */
    public void setRegistBatchFilePath(String registBatchFilePath) {
        this.registBatchFilePath = registBatchFilePath;
    }

    /**
     * healthinfoNodeApiUrlを返す
     *
     * @return healthinfoNodeApiUrl
     */
    public String getHealthinfoNodeApiUrl() {
        return healthinfoNodeApiUrl;
    }

    /**
     * healthinfoNodeApiUrlを設定する
     *
     * @param healthinfoNodeApiUrl
     *     NodeAPIの基底URL
     */
    public void setHealthinfoNodeApiUrl(String healthinfoNodeApiUrl) {
        this.healthinfoNodeApiUrl = healthinfoNodeApiUrl;
    }

    /**
     * healthInfoDashboardUrlを返す
     *
     * @return healthInfoDashboardUrl
     */
    public String getHealthInfoDashboardUrl() {
        return healthInfoDashboardUrl;
    }

    /**
     * healthInfoDashboardUrlを設定する
     *
     * @param healthInfoDashboardUrl
     *     健康情報ダッシュボードの基底URL
     */
    public void setHealthInfoDashboardUrl(String healthInfoDashboardUrl) {
        this.healthInfoDashboardUrl = healthInfoDashboardUrl;
    }

    /**
     * healthInfoApiUrlを返す
     *
     * @return healthInfoApiUrl
     */
    public String getHealthInfoApiUrl() {
        return healthInfoApiUrl;
    }

    /**
     * healthInfoApiUrlを設定する
     *
     * @param healthInfoApiUrl
     *     健康情報APIの基底URL
     */
    public void setHealthInfoApiUrl(String healthInfoApiUrl) {
        this.healthInfoApiUrl = healthInfoApiUrl;
    }

    /**
     * rootApiUrlを返す
     *
     * @return rootApiUrl
     */
    public String getRootApiUrl() {
        return rootApiUrl;
    }

    /**
     * rootApiUrlを設定する
     *
     * @param rootApiUrl
     *     管理者用APIの基底URL
     */
    public void setRootApiUrl(String rootApiUrl) {
        this.rootApiUrl = rootApiUrl;
    }

    /**
     * monthlySummaryBatchFilePathを返す
     *
     * @return monthlySummaryBatchFilePath
     */
    public String getMonthlySummaryBatchFilePath() {
        return monthlySummaryBatchFilePath;
    }

    /**
     * monthlySummaryBatchFilePathを設定する
     *
     * @param monthlySummaryBatchFilePath
     *     月次健康情報集計バッチCSV格納パス
     */
    public void setMonthlySummaryBatchFilePath(String monthlySummaryBatchFilePath) {
        this.monthlySummaryBatchFilePath = monthlySummaryBatchFilePath;
    }

}
