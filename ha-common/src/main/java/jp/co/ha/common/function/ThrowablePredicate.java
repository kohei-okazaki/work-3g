package jp.co.ha.common.function;

import jp.co.ha.common.exception.BaseException;

/**
 * {@linkplain java.util.function.Predicate} で例外処理ができないのでthrowできるようにしたクラス<br>
 * throwできる例外はBaseExceptionを継承したクラスのみとする
 *
 * @param <T>
 *     T型
 * @version 1.0.0
 */
@FunctionalInterface
public interface ThrowablePredicate<T> {

    /**
     * 関数を実行する
     *
     * @param t
     *     T
     * @return 関数実行結果
     * @throws BaseException
     *     基底例外
     */
    boolean test(T t) throws BaseException;
}
