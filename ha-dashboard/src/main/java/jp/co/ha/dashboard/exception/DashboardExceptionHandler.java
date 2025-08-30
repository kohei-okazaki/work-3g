package jp.co.ha.dashboard.exception;

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
import jp.co.ha.common.exception.BaseExceptionHandler;
import jp.co.ha.dashboard.view.DashboardView;

/**
 * ダッシュボード例外ハンドラー
 *
 * @version 1.0.0
 */
@Component
public class DashboardExceptionHandler extends BaseExceptionHandler
        implements HandlerExceptionResolver {

    /** SlackComponent */
    @Autowired
    private SlackApiComponent slack;

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception e) {

        ModelAndView modelView = new ModelAndView(DashboardView.ERROR.getName());

        String logErrorMessage = getLogErrorMessage(e);
        // log出力
        outLog(logErrorMessage, e);

        slack.send(ContentType.DASHBOARD, logErrorMessage);

        slack.sendError(ContentType.DASHBOARD, e);

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
