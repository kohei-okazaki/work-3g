package jp.co.ha.business.calc;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 四則演算オペレータインターフェース
 *
 */
@FunctionalInterface
public interface CalcOperator {

	/**
	 * 四則演算を定義
	 *
	 * @param target1
	 *     対象1
	 * @param target2
	 *     対象2
	 * @param degit
	 *     区切り
	 * @param roundingMode
	 *     丸め方法
	 * @return 計算結果
	 */
	BigDecimal apply(BigDecimal target1, BigDecimal target2, int degit, RoundingMode roundingMode);
}
