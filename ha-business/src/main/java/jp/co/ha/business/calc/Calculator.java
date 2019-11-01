package jp.co.ha.business.calc;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 計算機クラス
 * 
 * @since 1.0
 */
public class Calculator {

	/**
	 * プライベートコンストラクタ
	 */
	private Calculator() {
	}

	/**
	 * 計算処理を行う
	 *
	 * @param target1
	 *     対象1
	 * @param method
	 *     四則演算メソッド
	 * @param target2
	 *     対象1
	 * @param digit
	 *     区切り
	 * @param roundingMode
	 *     丸め方法
	 * @return 計算結果
	 */
	public static BigDecimal calc(BigDecimal target1, CalcMethod method, BigDecimal target2, int digit,
			RoundingMode roundingMode) {
		return method.getCalcOperatorFunction().apply(target1, target2, digit, roundingMode);
	}

}
