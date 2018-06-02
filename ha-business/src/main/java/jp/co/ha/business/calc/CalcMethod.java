package jp.co.ha.business.calc;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 四則演算メソッド定義<br>
 *
 */
public enum CalcMethod {

	/** 和 */
	ADD((BigDecimal target1, BigDecimal target2, int degit, RoundingMode roudingMode)
			-> target1.add(target2).setScale(degit, roudingMode)),
	/** 差 */
	SUBTRACT((BigDecimal target1, BigDecimal target2, int degit, RoundingMode roudingMode)
			-> target1.subtract(target2).setScale(degit, roudingMode)),
	/** 積 */
	MULTIPLY((BigDecimal target1, BigDecimal target2, int degit, RoundingMode roudingMode)
			-> target1.multiply(target2).setScale(degit, roudingMode)),
	/** 商 */
	DIVIDE((BigDecimal target1, BigDecimal target2, int degit, RoundingMode roudingMode)
			-> target1.divide(target2, degit, roudingMode));

	/** 四則演算オペレータインターフェース */
	private CalcOperator operator;

	/**
	 * コンストラクタ
	 *
	 * @param operator
	 *            四則演算オペレータインターフェース
	 */
	private CalcMethod(CalcOperator operator) {
		this.operator = operator;
	}

	/**
	 * 四則演算オペレータを返す<br>
	 *
	 * @return operator
	 */
	public CalcOperator getOperator() {
		return this.operator;
	}

}
