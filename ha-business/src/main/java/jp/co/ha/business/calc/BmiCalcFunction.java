package jp.co.ha.business.calc;

import java.math.BigDecimal;

/**
 * BMIを計算する関数インターフェース
 *
 */
@FunctionalInterface
public interface BmiCalcFunction {

	/**
	 * BMIの計算を行う
	 *
	 * @param height
	 *     身長
	 * @param weight
	 *     体重
	 * @param digit
	 *     四捨五入桁数
	 * @return
	 */
	BigDecimal apply(BigDecimal height, BigDecimal weight, int digit);
}
