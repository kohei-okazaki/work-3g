package jp.co.ha.business.calc;

import java.math.BigDecimal;

/**
 * 標準体重を計算する関数インターフェース
 *
 */
@FunctionalInterface
public interface StandardWeightCalcFunction {

	/**
	 * 標準体重の計算を行う
	 *
	 * @param height
	 *     身長
	 * @param digit
	 *     四捨五入桁数
	 * @return
	 */
	BigDecimal apply(BigDecimal height, int digit);
}
