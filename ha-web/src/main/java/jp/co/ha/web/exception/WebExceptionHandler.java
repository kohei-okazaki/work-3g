package jp.co.ha.web.exception;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.BaseExceptionHandler;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.web.view.ManageWebView;

/**
 * 画面例外ハンドラー
 *
 */
public class WebExceptionHandler implements BaseExceptionHandler {

	/** MessageSource */
	@Autowired
	private MessageSource messageSource;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {

		ModelAndView modelView = new ModelAndView();
		// error画面を設定
		modelView.setViewName(ManageWebView.ERROR.getName());
		// log出力
		outLog(getLogErrorMessage(e), e);
		request.setAttribute("errorMessage", getDispErrorMessage(e));
		return modelView;
	}


	/**
	 * 指定した例外の画面エラーメッセージを作成する
	 *
	 * @param e
	 *     例外
	 * @return エラーメッセージ
	 */
	private String getDispErrorMessage(Exception e) {
		String detail;
		String errorCode;
		StringBuilder body = new StringBuilder();
		if (e instanceof BaseException) {
			BaseException be = (BaseException) e;
			detail = be.getDetail();
			errorCode = be.getErrorCode().getOuterErrorCode();
		} else {
			// 予期せぬ例外にする
			detail = messageSource.getMessage(ErrorCode.UNEXPECTED_ERROR.getValidateMessage(), null, Locale.JAPANESE);
			errorCode = ErrorCode.UNEXPECTED_ERROR.getOuterErrorCode();
		}
		body.append(detail).append("(").append(errorCode).append(")");
		return body.toString();
	}

	private String getLogErrorMessage(Exception e) {
		String detail;
		String innerErrorCode;
		String outerErrorCode;
		StringBuilder body = new StringBuilder();
		if (e instanceof BaseException) {
			BaseException be = (BaseException) e;
			detail = be.getDetail();
			outerErrorCode = be.getErrorCode().getOuterErrorCode();
			innerErrorCode = be.getErrorCode().getInternalErrorCode();
		} else {
			detail = messageSource.getMessage(ErrorCode.UNEXPECTED_ERROR.getValidateMessage(), null, Locale.JAPANESE);
			outerErrorCode = ErrorCode.UNEXPECTED_ERROR.getOuterErrorCode();
			innerErrorCode = ErrorCode.UNEXPECTED_ERROR.getInternalErrorCode();
		}
		body.append(detail).append("(").append(outerErrorCode + " " + innerErrorCode).append(")");
		return body.toString();
	}
}
