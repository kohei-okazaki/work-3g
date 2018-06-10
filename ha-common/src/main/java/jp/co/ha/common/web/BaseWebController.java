package jp.co.ha.common.web;

/**
 * 基底Webコントローラ
 *
 */
public interface BaseWebController {

	/**
	 * viewを返す<br>
	 *
	 * @param view
	 *            BaseView
	 * @return
	 */
	default String getView(BaseView view) {
		return view.getName();
	}
}
