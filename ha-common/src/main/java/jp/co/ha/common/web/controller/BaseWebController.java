package jp.co.ha.common.web.controller;

import org.springframework.ui.Model;

import jp.co.ha.common.web.view.BaseView;

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
     * viewを返す
     *
     * @param model
     *     {@linkplain Model}
     * @param view
     *     BaseView
     * @return View名
     */
    default String getView(Model model, BaseView view) {
        model.addAttribute("breadcrumbList",
                view.getBreadcrumbView().getBreadcrumbList());
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
        return "redirect:" + view.getRedirectPath();
    }
}
