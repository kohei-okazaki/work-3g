package jp.co.ha.common.function;

import jp.co.ha.common.exception.BaseException;

/**
 * {@link java.util.function.BiConsumer} で例外処理ができないのでthrowできるようにしたクラス<br>
 * throwできる例外はBaseExceptionを継承したクラスのみとする
 *
 * @param <T>
 *     t型
 */
@FunctionalInterface
public interface ThrowableBiConsumer<T, U> {

	/**
	 * 関数を実行する
	 *
	 * @param t
	 *     T
	 * @param u
	 *     U
	 * @throws BaseException
	 *     基底例外
	 */
	void accept(T t, U u) throws BaseException;
}
