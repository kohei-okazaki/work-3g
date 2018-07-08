package jp.co.ha.business.calc;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 計算機クラス<br>
 *
 */
public class Calculator {

	/**
	 * 計算処理を行う<br>
	 *
	 * @param target1
	 *     対象1
	 * @param method
	 *     四則演算メソッド
	 * @param target2
	 *     対象1
	 * @param degit
	 *     区切り
	 * @param roudingMode
	 *     丸め方法
	 * @return
	 */
	public BigDecimal execute(BigDecimal target1, CalcMethod method, BigDecimal target2, int degit,
			RoundingMode roudingMode) {
		return method.getOperator().apply(target1, target2, degit, roudingMode);
	}

}
