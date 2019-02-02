package jp.co.ha.common.function;

import jp.co.ha.common.exception.BaseException;

/**
 * java.util.function.Functionで例外処理ができないのでthrowできるようにしたクラス<br>
 * throwできる例外はBaseExceptionを継承したクラスのみとする
 *
 * @param <T>
 *     t型
 * @param <R>
 *     r型
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
