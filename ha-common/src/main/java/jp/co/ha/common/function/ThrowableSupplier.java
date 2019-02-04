package jp.co.ha.common.function;

import jp.co.ha.common.exception.BaseException;

@FunctionalInterface
public interface ThrowableSupplier<T> {

	T get() throws BaseException;
}
