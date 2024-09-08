package jp.co.ha.dashboard.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.common.exception.BaseAppError;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.BaseExceptionHandler;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.Charset;
import jp.co.ha.dashboard.view.DashboardView;

/**
 * ダッシュボード例外ハンドラー
 *
 * @version 1.0.0
 */
@Component
public class DashboardExceptionHandler extends BaseExceptionHandler
        implements HandlerExceptionResolver {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(DashboardExceptionHandler.class);
    /** {@linkplain SlackApiComponent} */
    @Autowired
    private SlackApiComponent slackApiComponent;

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception e) {

        ModelAndView modelView = new ModelAndView(DashboardView.ERROR.getName());

        String logErrorMessage = getLogErrorMessage(e);
        // log出力
        outLog(logErrorMessage, e);

        try (StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);) {
            e.printStackTrace(pw);
            slackApiComponent.sendFile(ContentType.DASHBOARD,
                    sw.toString().getBytes(Charset.UTF_8.getValue()), "エラー情報",
                    "stack-trace", logErrorMessage);
        } catch (BaseException | IOException be) {
            LOG.error("slack通知に失敗しました", be);
        }
        request.setAttribute("errorMessage", getDispErrorMessage(e));

        return modelView;
    }

    /**
     * 指定した例外の画面エラーメッセージを返す
     *
     * @param e
     *     例外
     * @return エラーメッセージ
     */
    private String getDispErrorMessage(Exception e) {

        BaseAppError error = getAppError(e);
        // 設定ファイルからエラーコードを元にエラーメッセージを取得
        String errorMessage = messageSource.getMessage(
                error.getErrorCode().getOuterErrorCode(), null, Locale.getDefault());
        return new StringBuilder()
                .append(errorMessage)
                .append("(")
                .append(error.getErrorCode().getOuterErrorCode())
                .append(")")
                .toString();
    }

}
