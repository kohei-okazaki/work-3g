package jp.co.ha.business.io.file.excel.model;

import java.time.LocalDateTime;

import jp.co.ha.common.io.file.excel.annotation.ExcelDownloadModel;
import jp.co.ha.common.io.file.excel.model.BaseExcelModel;

/**
 * 健康情報Excel出力モデルクラス
 *
 * @version 1.0.0
 */
@ExcelDownloadModel(sheetName = "健康情報", headerNames = { "身長", "体重", "BMI", "標準体重",
        "健康情報登録日時" }, footerNames = { "身長",
                "体重", "BMI", "標準体重", "健康情報登録日時" })
public class HealthInfoExcelModel implements BaseExcelModel {

    /** 身長 */
    private String height;
    /** 体重 */
    private String weight;
    /** BMI */
    private String bmi;
    /** 標準体重 */
    private String standardWeight;
    /** 健康情報作成日時 */
    private LocalDateTime healthInfoRegDate;

    /**
     * 身長を返す
     *
     * @return height
     */
    public String getHeight() {
        return height;
    }

    /**
     * 身長を設定する
     *
     * @param height
     *     身長
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * 体重を返す
     *
     * @return weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     * 体重を設定する
     *
     * @param weight
     *     体重
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     * BMIを返す
     *
     * @return bmi
     */
    public String getBmi() {
        return bmi;
    }

    /**
     * BMIを設定する
     *
     * @param bmi
     *     BMI
     */
    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    /**
     * 標準体重を返す
     *
     * @return standardWeight
     */
    public String getStandardWeight() {
        return standardWeight;
    }

    /**
     * 標準体重を設定する
     *
     * @param standardWeight
     *     標準体重
     */
    public void setStandardWeight(String standardWeight) {
        this.standardWeight = standardWeight;
    }

    /**
     * 健康情報作成日時を返す
     *
     * @return healthInfoRegDate
     */
    public LocalDateTime getHealthInfoRegDate() {
        return healthInfoRegDate;
    }

    /**
     * 健康情報作成日時を設定する
     *
     * @param healthInfoRegDate
     *     健康情報作成日時
     */
    public void setHealthInfoRegDate(LocalDateTime healthInfoRegDate) {
        this.healthInfoRegDate = healthInfoRegDate;
    }

}
