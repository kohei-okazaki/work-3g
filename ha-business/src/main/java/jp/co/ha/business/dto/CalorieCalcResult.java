package jp.co.ha.business.dto;

import java.math.BigDecimal;

import jp.co.ha.business.healthInfo.type.GenderType;

/**
 * カロリー計算結果
 * 
 * @param age
 *     年齢
 * @param gender
 *     性別
 * @param height
 *     身長
 * @param weight
 *     体重
 * @param lifeWorkMetabolism
 *     生活活動代謝
 * @param baseMetabolism
 *     基礎代謝量
 * @param lostCaloriePerDay
 *     1日の消費カロリー
 * @version 1.0.0
 */
public record CalorieCalcResult(
        Integer age,
        GenderType gender,
        BigDecimal height,
        BigDecimal weight,
        BigDecimal lifeWorkMetabolism,
        BigDecimal baseMetabolism,
        BigDecimal lostCaloriePerDay) {

}
