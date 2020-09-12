package jp.co.ha.common.exception;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * Exceptionハンドラー基底インターフェース
 *
 * @version 1.0.0
 */
public abstract class BaseExceptionHandler {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(BaseExceptionHandler.class);
    /** {@linkplain MessageSource} */
    @Autowired
    private MessageSource messageSource;

    /**
     * アプリ例外を返す
     *
     * @param e
     *     例外
     * @return アプリ例外
     */
    protected BaseAppError getAppError(Exception e) {

        BaseAppError error = null;
        if (e instanceof BaseAppError) {
            error = (BaseAppError) e;
        } else {
            BaseErrorCode errorCode = CommonErrorCode.UNEXPECTED_ERROR;
            String detail = messageSource.getMessage(errorCode.getOuterErrorCode(), null,
                    Locale.getDefault());
            error = new SystemException(errorCode, detail, e);
        }

        return error;
    }

    /**
     * 指定したエラーメッセージのログを出力する
     *
     * @param errorMessage
     *     エラーメッセージ
     * @param e
     *     例外クラス
     */
    protected void outLog(String errorMessage, Exception e) {

        if (e instanceof BaseException) {
            BaseException be = (BaseException) e;
            switch (be.getErrorCode().getLogLevel()) {
            case ERROR:
                LOG.error(errorMessage, be);
                break;
            case WARN:
                LOG.warn(errorMessage, be);
                break;
            default:
                break;
            }
        } else {
            // 予期せぬエラー
            LOG.error(errorMessage, e);
        }

    }
}
