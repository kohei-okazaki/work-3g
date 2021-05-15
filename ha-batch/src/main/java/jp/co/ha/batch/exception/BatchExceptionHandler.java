package jp.co.ha.batch.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.BaseExceptionHandler;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.Charset;

/**
 * Batch処理の例外ハンドラークラス
 *
 * @version 1.0.0
 */
@Component("batchExceptionHandler")
public class BatchExceptionHandler extends BaseExceptionHandler {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(BatchExceptionHandler.class);
    /** {@linkplain SlackApiComponent} */
    @Autowired
    private SlackApiComponent slackApiComponent;

    /**
     * 例外処理を行う
     *
     * @param e
     *     例外
     */
    public void handleException(Exception e) {

        String logErrorMessage = getLogErrorMessage(e);
        outLog(logErrorMessage, e);

        try (StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);) {
            e.printStackTrace(pw);
            slackApiComponent.sendFile(ContentType.BATCH,
                    sw.toString().getBytes(Charset.UTF_8.getValue()), "エラー情報",
                    "stack-trace", logErrorMessage);
        } catch (BaseException | IOException be) {
            LOG.error("slack通知に失敗しました", be);
        }
    }
}
