package jp.co.ha.common.function;

import jp.co.ha.common.exception.BaseException;

/**
 * {@link java.util.function.Consumer} で例外処理ができないのでthrowできるようにしたクラス<br>
 * throwできる例外はBaseExceptionを継承したクラスのみとする
 *
 * @param <T>
 *     T型
 */
@FunctionalInterface
public interface ThrowableConsumer<T> {

	/**
	 * 関数を実行する
	 *
	 * @param t
	 *     T
	 * @throws BaseException
	 *     基底例外
	 */
	void accept(T t) throws BaseException;
}
