package jp.co.ha.common.function;

/**
 * Builderパターンの関数インターフェース
 *
 * @param <T>
 *     生成後のクラス型
 * @version 1.0.0
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
