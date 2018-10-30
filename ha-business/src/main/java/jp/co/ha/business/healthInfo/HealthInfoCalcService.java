package jp.co.ha.business.healthInfo;

import java.math.BigDecimal;

import jp.co.ha.business.healthInfo.type.HealthStatus;

/**
 * 健康情報サービス<br>
 *
 */
public interface HealthInfoCalcService {

	/**
	 * 入力体重と前の体重を比較し、健康ステータスを返す<br>
	 *
	 * @param inputWeight
	 *     入力体重
	 * @param beforeWeight
	 *     前の体重
	 * @return
	 */
	HealthStatus getHealthStatus(BigDecimal inputWeight, BigDecimal beforeWeight);

	/**
	 * 単位を以下に変換する</br>
	 * cm → m</br>
	 *
	 * @param target
	 *     対象の値
	 * @return メートル
	 */
	BigDecimal convertMeterFromCentiMeter(BigDecimal target);

	/**
	 * 単位を以下に変換する</br>
	 * m → cm
	 *
	 * @param target
	 *     対象の値
	 * @return センチメートル
	 */
	BigDecimal convertCentiMeterFromMeter(BigDecimal target);

	/**
	 * BMIを計算</br>
	 *
	 * @param height
	 *     身長
	 * @param weight
	 *     体重
	 * @param digit
	 *     四捨五入桁数
	 * @return BMIを計算
	 */
	BigDecimal calcBmi(BigDecimal height, BigDecimal weight, int digit);

	/**
	 * 標準体重を計算</br>
	 *
	 * @param height
	 *     身長
	 * @param digit
	 *     四捨五入桁数
	 * @return 標準体重を計算
	 */
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
}
