package jp.co.ha.common.function;

import jp.co.ha.common.exception.BaseException;

/**
 * 3つの引数を受け付けるクラス<br>
 * throwできる例外はBaseExceptionを継承したクラスのみとする
 *
 * @param <T>
 *     T型
 * @param <U>
 *     U型
 * @param <V>
 *     V型
 * @version 1.0.0
 */
@FunctionalInterface
public interface ThrowableTriConsumer<T, U, V> {

    /**
     * 関数を実行する
     *
     * @param t
     *     T
     * @param u
     *     U
     * @param v
     *     V
     * @throws BaseException
     *     基底例外
     */
    void accept(T t, U u, V v) throws BaseException;
}
