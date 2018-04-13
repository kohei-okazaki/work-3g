package jp.co.ha.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jp.co.ha.common.other.CalcMethod;

/**
 * 健康情報Utilクラス<br>
 *
 */
public class HealthInfoUtil {

	/**
	 * プライベートコンストラクタ<br>
	 */
	private HealthInfoUtil() {
	}

	/**
	 * 単位を以下に変換する</br>
	 * cm → m
	 * @param target 対象の値
	 * @return メートル
	 */
	public static BigDecimal convertMeterFromCentiMeter(BigDecimal target) {
		return target.scaleByPowerOfTen(-2);
	}

	/**
	 * 単位を以下に変換する</br>
	 * m → cm
	 * @param target 対象の値
	 * @return センチメートル
	 */
	public static BigDecimal convertCentiMeterFromMeter(BigDecimal target) {
		return target.scaleByPowerOfTen(2);
	}

	/**
	 * BMIを計算</br>
	 * 体重(kg) / (身長(m) × 身長(m))</br>
	 * @param height 身長
	 * @param weight 体重
	 * @param digit 四捨五入桁数
	 * @return BMIを計算
	 */
	public static BigDecimal calcBmi(BigDecimal height, BigDecimal weight, int digit) {
		BigDecimal multiplyResult = CalcUtil.execute(height, CalcMethod.MULTIPLY, height, 2, RoundingMode.HALF_UP);
		BigDecimal result = CalcUtil.execute(weight, CalcMethod.DIVIDE, multiplyResult, 2, RoundingMode.HALF_UP);
		return result;
	}

	/**
	 * 標準体重を計算</br>
	 * (身長(m) × 身長(m)) × 22</br>
	 * @param height 身長
	 * @param digit 四捨五入桁数
	 * @return 標準体重を計算
	 */
	public static BigDecimal calcStandardWeight(BigDecimal height, int digit) {
		BigDecimal result = CalcUtil.execute(height, CalcMethod.MULTIPLY, height, digit, RoundingMode.HALF_UP);
		result = CalcUtil.execute(result, CalcMethod.MULTIPLY, new BigDecimal(22), digit, RoundingMode.HALF_UP);
		return result;
	}
}
