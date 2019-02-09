package jp.co.ha.common.function;

import jp.co.ha.common.exception.BaseException;

/**
 * {@link java.util.function.Predicate} で例外処理ができないのでthrowできるようにしたクラス<br>
 * throwできる例外はBaseExceptionを継承したクラスのみとする
 *
 * @param <T>
 *     t型
 */
@FunctionalInterface
public interface ThrowablePredicate<T> {

	/**
	 * 関数を実行する
	 *
	 * @param t
	 *     T
	 * @return
	 * @throws BaseException
	 *     基底例外
	 */
	boolean test(T t) throws BaseException;
}