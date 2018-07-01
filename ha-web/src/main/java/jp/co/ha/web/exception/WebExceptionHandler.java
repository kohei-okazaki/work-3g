package jp.co.ha.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.exception.BaseExceptionHandler;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康管理画面例外ハンドラークラス<br>
 *
 */
public class WebExceptionHandler implements BaseExceptionHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception e) {

		ModelAndView modelView = new ModelAndView();
		modelView.setViewName(ManageWebView.ERROR.getName());
		e.printStackTrace();
		if (e instanceof MultipartException) {
			return modelView;
		}
		request.setAttribute("errorMessage", buildErrorMessage((BaseAppException) e));
		return modelView;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String buildErrorMessage(BaseAppException e) {
		return e.getDetail() + "(" + e.getErrorCode() + ")";
	}
}
