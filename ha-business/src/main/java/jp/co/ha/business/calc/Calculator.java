package jp.co.ha.business.calc;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jp.co.ha.common.other.CalcMethod;

/**
 * 計算機クラス<br>
 *
 */
public class Calculator {

	/**
	 * 計算処理を行う<br>
	 * @param target1
	 * @param method
	 * @param target2
	 * @param degit
	 * @param roudingMode
	 * @return
	 */
	public BigDecimal execute(BigDecimal target1, CalcMethod method, BigDecimal target2, int degit, RoundingMode roudingMode) {
		return method.getOperator().apply(target1, target2, degit, roudingMode);
	}

}
