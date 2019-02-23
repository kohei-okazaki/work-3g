package jp.co.ha.common.function;

import jp.co.ha.common.exception.BaseException;

/**
 * {@link java.util.function.Function} で例外処理ができないのでthrowできるようにしたクラス<br>
 * throwできる例外はBaseExceptionを継承したクラスのみとする
 *
 * @param <T>
 *     T型
 * @param <R>
 *     R型
 */
@FunctionalInterface
public interface ThrowableFunction<T, R> {

	/**
	 * 関数を実行する
	 *
	 * @param t
	 *     T
	 * @return
	 * @throws BaseException
	 *     基底例外
	 */
	R apply(T t) throws BaseException;
}
