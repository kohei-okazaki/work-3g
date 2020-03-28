package jp.co.ha.web.controller;

import jp.co.ha.web.view.BaseView;

/**
 * 基底画面コントローラ<br>
 * すべての画面のコントローラはこのクラスを継承すること
 *
 * @version 1.0.0
 */
public interface BaseWebController extends BaseController {

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

    /**
     * viewにリダイレクトする
     *
     * @param view
     *     BaseView
     * @return View名
     */
    default String redirectView(BaseView view) {
        return "redirect:" + view.getName();
    }
}
