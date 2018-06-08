package jp.co.ha.business.calc;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 四則演算オペレータインターフェース<br>
 *
 */
@FunctionalInterface
public interface CalcOperator {

	/**
	 * 四則演算を定義<br>
	 *
	 * @param target1
	 *            対象1
	 * @param target2
	 *            対象1
	 * @param degit
	 *            区切り
	 * @param roudingMode
	 *            丸め方法
	 * @return
	 */
	BigDecimal apply(BigDecimal target1, BigDecimal target2, int degit, RoundingMode rougingMode);
}
