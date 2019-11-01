package jp.co.ha.business.calc;

/**
 * 四則演算メソッド定義
 * 
 * @since 1.0
 */
public enum CalcMethod {

	/** 和 */
	ADD((target1, target2, digit, roudingMode) -> target1.add(target2).setScale(digit, roudingMode)),
	/** 差 */
	SUBTRACT((target1, target2, digit, roudingMode) -> target1.subtract(target2).setScale(digit, roudingMode)),
	/** 積 */
	MULTIPLY((target1, target2, digit, roudingMode) -> target1.multiply(target2).setScale(digit, roudingMode)),
	/** 商 */
	DIVIDE((target1, target2, digit, roudingMode) -> target1.divide(target2, digit, roudingMode));

	/** 四則演算関数 */
	private CalcOperatorFunction function;

	/**
	 * コンストラクタ
	 *
	 * @param function
	 *     四則演算関数
	 */
	private CalcMethod(CalcOperatorFunction function) {
		this.function = function;
	}

	/**
	 * 四則演算関数を返す
	 *
	 * @return function
	 */
	public CalcOperatorFunction getCalcOperatorFunction() {
		return this.function;
	}

}
