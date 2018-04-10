package jp.co.ha.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jp.co.ha.common.other.CalcMethod;

/**
 * 計算処理のUtilクラス<br>
 * インスタンス生成を制限<br>
 *
 */
public class CalcUtil {

	private CalcUtil() {
	}

	/** マイナス */
	public static final String MINUS = "-";

	/**
	 * 四則演算を行う<br>
	 * ex) target1 = 100, method = CalcMethod.SUBTRACT, target2 = 30, result = 70<br>
	 * @param target1
	 * @param method
	 * @param target2
	 * @param degit
	 * @param roudingMode
	 * @return
	 */
	public static BigDecimal execute(BigDecimal target1, CalcMethod method, BigDecimal target2, int degit, RoundingMode roudingMode) {
		return method.getOperator().apply(target1, target2, degit, roudingMode);
	}

}
