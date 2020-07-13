package jp.co.ha.business.healthInfo.service;

import java.math.BigDecimal;

import jp.co.ha.business.dto.CalorieCalcDto;
import jp.co.ha.business.healthInfo.type.HealthInfoStatus;

/**
 * 健康情報計算サービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoCalcService {

    /**
     * 健康情報ステータスを返す
     *
     * @param inputWeight
     *     入力値
     * @param beforeWeight
     *     以前の体重
     * @return 健康情報ステータス
     */
    HealthInfoStatus getHealthInfoStatus(BigDecimal inputWeight, BigDecimal beforeWeight);

    /**
     * 単位を以下に変換する<br>
     * cm → m
     *
     * @param centiMeter
     *     センチメートル
     * @return メートル
     */
    BigDecimal convertMeterFromCentiMeter(BigDecimal centiMeter);

    /**
     * BMIを計算
     *
     * @param height
     *     身長
     * @param weight
     *     体重
     * @param digit
     *     四捨五入桁数
     * @return BMI
     */
    @Deprecated
    BigDecimal calcBmi(BigDecimal height, BigDecimal weight, int digit);

    /**
     * 標準体重を計算
     *
     * @param height
     *     身長
     * @param digit
     *     四捨五入桁数
     * @return 標準体重
     */
    @Deprecated
    BigDecimal calcStandardWeight(BigDecimal height, int digit);

    /**
     * 最後に入力した体重と今の体重の差を計算
     *
     * @param before
     *     前の体重
     * @param now
     *     今の体重
     * @return 体重の差(小数第2位を四捨五入する)
     */
    BigDecimal calcDiffWeight(BigDecimal before, BigDecimal now);

    /**
     * 基礎代謝量を計算
     *
     * @param calorieCalcDto
     *     カロリー計算DTO
     * @return 基礎代謝量
     */
    @Deprecated
    BigDecimal calcBaseMetabolism(CalorieCalcDto calorieCalcDto);

    /**
     * 1日の消費カロリーを計算
     *
     * @param calorieCalcDto
     *     カロリー計算DTO
     * @return 1日の消費カロリー
     */
    @Deprecated
    BigDecimal calcLostCaloriePerDay(CalorieCalcDto calorieCalcDto);

}
