package jp.co.ha.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

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
			Exception ex) {

		ModelAndView modelView = new ModelAndView();
		modelView.setViewName(ManageWebView.ERROR.getName());

		return modelView;
	}

}
