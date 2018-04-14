package jp.co.ha.common.web;

public interface BaseWebController {

	/**
	 * viewを返す<br>
	 * @return
	 */
	default String getView(BaseView view) {
		return view.getName();
	}
}
