package jp.co.ha.business.dto;

import java.math.BigDecimal;

import jp.co.ha.business.healthInfo.type.GenderType;

/**
 * 肺活量計算結果
 * 
 * @param age
 *     年齢
 * @param genderType
 *     性別
 * @param height
 *     身長
 * @param predictBreathingCapacity
 *     予測肺活量
 * @param breathingCapacityPercentage
 *     肺活量%
 * @version 1.0.0
 */
public record BreathingCapacityResult(
        Integer age,
        GenderType genderType,
        BigDecimal height,
        BigDecimal predictBreathingCapacity,
        BigDecimal breathingCapacityPercentage) {
}
