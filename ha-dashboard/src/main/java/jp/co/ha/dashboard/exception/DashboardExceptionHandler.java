package jp.co.ha.dashboard.exception;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

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

    /** {@linkplain MessageSource} */
    @Autowired
    private MessageSource messageSource;

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception e) {

        ModelAndView modelView = new ModelAndView();
        // error画面を設定
        modelView.setViewName(DashboardView.ERROR.getName());
        // log出力
        outLog(getLogErrorMessage(e), e);
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

    /**
     * ログエラーメッセージを返す
     *
     * @param e
     *     例外
     * @return エラーメッセージ
     */
    private String getLogErrorMessage(Exception e) {

        BaseAppError error = getAppError(e);
        return new StringBuilder()
                .append("(")
                .append(error.getErrorCode().getOuterErrorCode())
                .append(")")
                .append(error.getDetail())
                .toString();
    }
}
