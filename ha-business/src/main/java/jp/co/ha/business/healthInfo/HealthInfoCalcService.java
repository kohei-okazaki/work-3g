package jp.co.ha.business.healthInfo;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.Function;

import jp.co.ha.business.healthInfo.type.HealthInfoStatus;

/**
 * 健康情報計算サービスインターフェース
 *
 */
public interface HealthInfoCalcService {

	/**
	 * 健康情報ステータスを返す
	 *
	 * @return 健康情報ステータス
	 */
	BiFunction<BigDecimal, BigDecimal, HealthInfoStatus> getHealthInfoStatus();

	/**
	 * 単位を以下に変換する<br>
	 * cm → m
	 *
	 * @return メートル
	 */
	Function<BigDecimal, BigDecimal> convertMeterFromCentiMeter();

	/**
	 * BMIを計算
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
	 * 標準体重を計算
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
