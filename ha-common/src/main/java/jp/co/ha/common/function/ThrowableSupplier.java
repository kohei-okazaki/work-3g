package jp.co.ha.common.function;

import jp.co.ha.common.exception.BaseException;

/**
 * {@link java.util.function.Supplier} で例外処理ができないのでthrowできるようにしたクラス<br>
 * throwできる例外はBaseExceptionを継承したクラスのみとする
 *
 * @param <T>
 *     t型
 */
@FunctionalInterface
public interface ThrowableSupplier<T> {

	T get() throws BaseException;
}
