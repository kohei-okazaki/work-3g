package jp.co.ha.common.function;

import jp.co.ha.common.exception.BaseException;

/**
 * {@link java.util.function.BiPredicate}で例外処理ができないのでthrowできるようにしたクラス<br>
 * throwできる例外はBaseExceptionを継承したクラスのみとする
 *
 * @param <T>
 *     T型
 * @param <U>
 *     U型
 * @version 1.0.0
 */
@FunctionalInterface
public interface ThrowableBiPredicate<T, U> {

    /**
     * 関数を実行する
     *
     * @param t
     *     T
     * @param u
     *     U
     * @return 関数実行結果
     * @throws BaseException
     *     基底例外
     */
    boolean test(T t, U u) throws BaseException;
}
