package jp.co.ha.common.function;

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
	 */
	T build();

}
