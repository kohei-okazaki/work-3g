package jp.co.ha.common.exception;

/**
 * アプリの基底例外インターフェース
 *
 * @version 1.0.0
 */
public interface BaseAppError {

    /**
     * {@linkplain BaseErrorCode}を返す
     *
     * @return {@linkplain BaseErrorCode}
     */
    BaseErrorCode getErrorCode();

    /**
     * 詳細を返す
     *
     * @return 詳細
     */
    String getDetail();

}
