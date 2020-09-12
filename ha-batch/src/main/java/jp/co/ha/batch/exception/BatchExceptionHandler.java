package jp.co.ha.batch.exception;

import org.springframework.stereotype.Component;

import jp.co.ha.common.exception.BaseAppError;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.BaseExceptionHandler;

/**
 * Batch処理の例外ハンドラークラス
 *
 * @version 1.0.0
 */
@Component("batchExceptionHandler")
public class BatchExceptionHandler extends BaseExceptionHandler {

    /**
     * 例外処理を行う
     *
     * @param e
     *     例外
     */
    public void handleException(Exception e) {

        BaseAppError error = getAppError(e);

        String errorMessage = new StringBuilder()
                .append("(")
                .append(error.getErrorCode().getOuterErrorCode())
                .append(")")
                .append(error.getDetail()).toString();

        outLog(errorMessage, (BaseException) error);
    }
}
