package jp.co.ha.common.function;

import jp.co.ha.common.exception.BaseException;

/**
 * {@link java.util.function.BiFunction} で例外処理ができないのでthrowできるようにしたクラス<br>
 * throwできる例外はBaseExceptionを継承したクラスのみとする
 *
 * @param <T>
 *     T型
 * @param <U>
 *     U型
 * @param <R>
 *     R型
 * @version 1.0.0
 */
@FunctionalInterface
public interface ThrowableBiFunction<T, U, R> {

    /**
     * 関数を実行する
     *
     * @param t
     *     T
     * @param u
     *     U
     * @return R
     * @throws BaseException
     *     基底例外
     */
    R apply(T t, U u) throws BaseException;
}
