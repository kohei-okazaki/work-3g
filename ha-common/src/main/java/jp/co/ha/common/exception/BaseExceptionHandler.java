package jp.co.ha.common.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.Logger.LogLevel;
import jp.co.ha.common.log.LoggerFactory;

/**
 * Exceptionハンドラー基底インターフェース
 *
 * @version 1.0.0
 */
public interface BaseExceptionHandler extends HandlerExceptionResolver {

    /** LOG */
    static final Logger LOG = LoggerFactory.getLogger(BaseExceptionHandler.class);

    /**
     * 指定したエラーメッセージのログを出力する
     *
     * @param errorMessage
     *     エラーメッセージ
     * @param e
     *     例外クラス
     */
    default void outLog(String errorMessage, Exception e) {

        if (e instanceof BaseException) {
            BaseException be = (BaseException) e;
            LogLevel level = be.getErrorCode().getLogLevel();
            switch (level) {
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
