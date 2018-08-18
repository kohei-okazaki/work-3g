package jp.co.ha.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.BaseExceptionHandler;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.web.view.ManageWebView;

/**
 * 画面例外ハンドラー<br>
 *
 */
public class WebExceptionHandler implements BaseExceptionHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {

		ModelAndView modelView = new ModelAndView();
		modelView.setViewName(ManageWebView.ERROR.getName());
		String errorMessage = buildErrorMessage(e);
		request.setAttribute("errorMessage", errorMessage);
		return modelView;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String buildErrorMessage(Exception e) {
		String detail;
		String errorCode;
		StringBuilder body = new StringBuilder();
		if (e instanceof BaseException) {
			detail = ((BaseException) e).getDetail();
			errorCode = ((BaseException) e).getErrorCode().getOuterErrorCode();
		} else {
			// 予期せぬ例外にする
			detail = ErrorCode.UNEXPECTED_ERROR.getErrorMessage();
			errorCode = ErrorCode.UNEXPECTED_ERROR.getOuterErrorCode();
		}
		body.append(detail).append("(").append(errorCode).append(")");
		return body.toString();
	}
}
