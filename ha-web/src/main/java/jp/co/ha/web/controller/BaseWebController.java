package jp.co.ha.web.controller;

import jp.co.ha.web.view.BaseView;

/**
 * 基底Webコントローラ<br>
 * すべてのwebコントローラはこのインターフェースを継承すること<br>
 *
 */
public interface BaseWebController {

	/**
	 * viewを返す
	 *
	 * @param view
	 *     BaseView
	 * @return View名
	 */
	default String getView(BaseView view) {
		return view.getName();
	}
}
