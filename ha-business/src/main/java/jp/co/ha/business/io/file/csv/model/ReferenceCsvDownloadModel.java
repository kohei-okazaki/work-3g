package jp.co.ha.business.io.file.csv.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jp.co.ha.common.io.file.csv.annotation.CsvDownloadModel;
import jp.co.ha.common.io.file.csv.model.BaseCsvModel;
import jp.co.ha.common.log.annotation.Mask;

/**
 * 結果照会CSVダウンロードモデル<br>
 * CSV出力したい項目のみを持つ
 *
 * @version 1.0.0
 */
@CsvDownloadModel(headerNames = { "ユーザID", "身長", "体重", "BMI", "標準体重",
        "健康情報作成日時" }, footerNames = { "ユーザID", "身長", "体重", "BMI", "標準体重", "健康情報作成日時" })
public class ReferenceCsvDownloadModel implements BaseCsvModel {

    /** ユーザID */
    private Long seqUserId;
    /** 身長 */
    @Mask
    private BigDecimal height;
    /** 体重 */
    @Mask
    private BigDecimal weight;
    /** BMI */
    @Mask
    private BigDecimal bmi;
    /** 標準体重 */
    @Mask
    private BigDecimal standardWeight;
    /** 健康情報作成日時 */
    private LocalDateTime healthInfoRegDate;

    /**
     * seqUserIdを返す
     *
     * @return seqUserId
     */
    public Long getSeqUserId() {
        return seqUserId;
    }

    /**
     * seqUserIdを設定する
     *
     * @param seqUserId
     *     ユーザID
     */
    public void setSeqUserId(Long seqUserId) {
        this.seqUserId = seqUserId;
    }

    /**
     * heightを返す
     *
     * @return height
     */
    public BigDecimal getHeight() {
        return height;
    }

    /**
     * heightを設定する
     *
     * @param height
     *     身長
     */
    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    /**
     * weightを返す
     *
     * @return weight
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * weightを設定する
     *
     * @param weight
     *     体重
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * bmiを返す
     *
     * @return bmi
     */
    public BigDecimal getBmi() {
        return bmi;
    }

    /**
     * bmiを設定する
     *
     * @param bmi
     *     BMI
     */
    public void setBmi(BigDecimal bmi) {
        this.bmi = bmi;
    }

    /**
     * standardWeightを返す
     *
     * @return standardWeight
     */
    public BigDecimal getStandardWeight() {
        return standardWeight;
    }

    /**
     * standardWeightを設定する
     *
     * @param standardWeight
     *     標準体重
     */
    public void setStandardWeight(BigDecimal standardWeight) {
        this.standardWeight = standardWeight;
    }

    /**
     * healthInfoRegDateを返す
     *
     * @return healthInfoRegDate
     */
    public LocalDateTime getHealthInfoRegDate() {
        return healthInfoRegDate;
    }

    /**
     * healthInfoRegDateを設定する
     *
     * @param healthInfoRegDate
     *     健康情報作成日時
     */
    public void setHealthInfoRegDate(LocalDateTime healthInfoRegDate) {
        this.healthInfoRegDate = healthInfoRegDate;
    }

}
