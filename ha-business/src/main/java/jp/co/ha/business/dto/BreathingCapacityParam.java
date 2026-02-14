package jp.co.ha.business.dto;

import java.math.BigDecimal;

import jp.co.ha.business.healthInfo.type.GenderType;

/**
 * 肺活量計算画面入力パラメータ
 * 
 * @param age
 *     年齢
 * @param gender
 *     性別
 * @param height
 *     身長
 * @version 1.0.0
 */
public record BreathingCapacityParam(
        Integer age,
        GenderType gender,
        BigDecimal height) {
}
