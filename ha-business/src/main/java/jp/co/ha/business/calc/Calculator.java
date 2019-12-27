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

	/**
	 * 計算用メソッドの列挙体
	 * <ul>
	 * <li>和 -> ADD</li>
	 * <li>差 -> SUBTRACT</li>
	 * <li>積 -> MULTIPLY</li>
	 * <li>商 -> DIVIDE</li>
	 * </ul>
	 *
	 * @since 1.0
	 */
	public static enum CalcMethod {

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

}
