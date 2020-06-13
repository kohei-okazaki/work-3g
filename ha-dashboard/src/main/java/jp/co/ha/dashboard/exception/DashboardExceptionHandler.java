package jp.co.ha.dashboard.exception;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ha.common.exception.BaseErrorCode;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.BaseExceptionHandler;
import jp.co.ha.common.exception.BaseRuntimeException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.dashboard.view.DashboardView;

/**
 * ダッシュボード例外ハンドラー
 *
 * @version 1.0.0
 */
@Component
public class DashboardExceptionHandler implements BaseExceptionHandler {

    /** {@linkplain MessageSource} */
    @Autowired
    private MessageSource messageSource;

    /**
     * {@inheritDoc}
     */
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
     * 指定した例外の画面エラーメッセージを返す<br>
     * <ul>
     * <li>BaseExceptionまたはBaseRuntimeExceptionを継承した例外の場合、throw時のエラーコード</li>
     * </ul>
     *
     * @param e
     *     例外
     * @return エラーメッセージ
     */
    private String getDispErrorMessage(Exception e) {

        BaseErrorCode code = CommonErrorCode.UNEXPECTED_ERROR;
        if (e instanceof BaseException) {
            code = ((BaseException) e).getErrorCode();
        } else if (e instanceof BaseRuntimeException) {
            code = ((BaseRuntimeException) e).getErrorCode();
        }

        String errorMessage = messageSource.getMessage(code.getOuterErrorCode(), null,
                Locale.getDefault());
        String errorCode = code.getOuterErrorCode();

        return new StringBuilder().append(errorMessage).append("(").append(errorCode)
                .append(")").toString();
    }

    /**
     * ログエラーメッセージを返す
     *
     * @param e
     *     例外
     * @return エラーメッセージ
     */
    private String getLogErrorMessage(Exception e) {
        String detail;
        String errorCode;
        StringBuilder body = new StringBuilder();
        if (e instanceof BaseException) {
            BaseException be = (BaseException) e;
            detail = be.getDetail();
            errorCode = be.getErrorCode().getOuterErrorCode();
        } else {
            // 予期せぬ例外にする
            detail = messageSource.getMessage(
                    CommonErrorCode.UNEXPECTED_ERROR.getOuterErrorCode(), null,
                    Locale.getDefault());
            errorCode = CommonErrorCode.UNEXPECTED_ERROR.getOuterErrorCode();
        }
        return body.append("(").append(errorCode).append(")").append(detail).toString();
    }
}
