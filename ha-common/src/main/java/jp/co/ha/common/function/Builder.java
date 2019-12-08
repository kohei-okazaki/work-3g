package jp.co.ha.common.function;

import jp.co.ha.common.exception.BaseException;

/**
 * Builderパターンの関数インターフェース
 *
 * @since 1.0
 * @param <T>
 *     生成後のクラス型
 */
@FunctionalInterface
public interface Builder<T> {

	/**
	 * ビルド後のクラスを返す
	 *
	 * @return ビルド後のクラス
	 * @throws BaseException
	 *     基底例外
	 *
	 */
	T build() throws BaseException;

}
