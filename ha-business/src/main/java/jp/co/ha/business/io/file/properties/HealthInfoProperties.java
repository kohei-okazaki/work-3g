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
    /** 健康情報計算APIのベースパス */
    private String healthInfoCalcUrl;

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
     * healthInfoCalcUrlを返す
     *
     * @return healthInfoCalcUrl
     */
    public String getHealthInfoCalcUrl() {
        return healthInfoCalcUrl;
    }

    /**
     * healthInfoCalcUrlを設定する
     *
     * @param healthInfoCalcUrl
     *     健康情報計算APIの基底URL
     */
    public void setHealthInfoCalcUrl(String healthInfoCalcUrl) {
        this.healthInfoCalcUrl = healthInfoCalcUrl;
    }

}
