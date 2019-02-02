package jp.co.ha.common.function;

import jp.co.ha.common.exception.BaseException;

/**
 * java.util.function.BiFunctionで例外処理ができないのでthrowできるようにしたクラス<br>
 * throwできる例外はBaseExceptionを継承したクラスのみとする
 *
 * @param <T>
 *     t型
 * @param <U>
 *     u型
 * @param <R>
 *     r型
 */
@FunctionalInterface
public interface ThrowableBiFunction<T, U, R> {

	/**
	 * 関数を実行する
	 *
	 * @param t
	 *     T
	 * @param u
	 *     U
	 * @return R
	 * @throws BaseException
	 *     基底例外
	 */
	R apply(T t, U u) throws BaseException;
}
