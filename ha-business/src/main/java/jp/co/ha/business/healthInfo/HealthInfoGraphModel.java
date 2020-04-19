package jp.co.ha.business.healthInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.DateUtil.DateFormatType;

/**
 * 健康情報グラフ描画モデル
 *
 * @version 1.0.0
 */
public class HealthInfoGraphModel {

    /** 健康情報登録日時リスト */
    private List<String> healthInfoRegDateList = new ArrayList<>();

    /** 体重リスト */
    private List<BigDecimal> weightList = new ArrayList<>();

    /** BMIリスト */
    private List<BigDecimal> bmiList = new ArrayList<>();

    /** 標準体重リスト */
    private List<BigDecimal> standardWeightList = new ArrayList<>();

    /**
     * healthInfoRegDateListを返す
     *
     * @return healthInfoRegDateList
     */
    public List<String> getHealthInfoRegDateList() {
        return healthInfoRegDateList;
    }

    /**
     * weightListを返す
     *
     * @return weightList
     */
    public List<BigDecimal> getWeightList() {
        return weightList;
    }

    /**
     * bmiListを返す
     *
     * @return bmiList
     */
    public List<BigDecimal> getBmiList() {
        return bmiList;
    }

    /**
     * standardWeightListを返す
     *
     * @return standardWeightList
     */
    public List<BigDecimal> getStandardWeightList() {
        return standardWeightList;
    }

    /**
     * 健康情報登録日時を追加する
     *
     * @param healthInfoRegDate
     *     健康情報登録日時リスト
     */
    public void addHealthInfoRegDate(String healthInfoRegDate) {
        this.healthInfoRegDateList.add(healthInfoRegDate);
    }

    /**
     * 健康情報登録日時を追加する
     *
     * @param healthInfoRegDate
     *     健康情報登録日時リスト
     * @param type
     *     日付フォーマット
     */
    public void addHealthInfoRegDate(Date healthInfoRegDate, DateFormatType type) {
        this.healthInfoRegDateList.add(DateUtil.toString(healthInfoRegDate, type));
    }

    /**
     * 体重を追加する
     *
     * @param weight
     *     体重
     */
    public void addWeight(BigDecimal weight) {
        this.weightList.add(weight);
    }

    /**
     * BMIを追加する
     *
     * @param bmi
     *     BMI
     */
    public void addBmi(BigDecimal bmi) {
        this.bmiList.add(bmi);
    }

    /**
     * 標準体重を追加する
     *
     * @param standardWeight
     *     標準体重
     */
    public void addStandardWeight(BigDecimal standardWeight) {
        this.standardWeightList.add(standardWeight);
    }
}
