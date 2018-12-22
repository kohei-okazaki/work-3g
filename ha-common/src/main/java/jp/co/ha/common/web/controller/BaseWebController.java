package jp.co.ha.common.web.controller;

import jp.co.ha.common.web.view.BaseView;

/**
 * 基底Webコントローラ<br>
 * すべてのwebコントローラはこのインターフェースを継承すること<br>
 *
 */
public interface BaseWebController {

	/**
	 * viewを返す<br>
	 *
	 * @param view
	 *     BaseView
	 * @return View名
	 */
	default String getView(BaseView view) {
		return view.getName();
	}
}
