package jp.co.ha.dashboard.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.ha.business.component.NewsComponent;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.SystemProperties;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.common.web.controller.BaseWebController;
import jp.co.ha.dashboard.view.DashboardView;

/**
 * お知らせ情報画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("news")
public class NewsController implements BaseWebController {

    /** お知らせ情報Component */
    @Autowired
    private NewsComponent component;
    /** システム設定ファイル情報 */
    @Autowired
    private SystemProperties systemConfig;

    /**
     * お知らせ一覧
     *
     * @param model
     *     {@linkplain Model}
     * @param page
     *     ページ数
     * @return お知らせ一覧画面
     * @throws BaseException
     *     JSONファイルの読込に失敗した場合
     */
    @GetMapping("/list")
    public String list(Model model,
            @RequestParam(name = "page", defaultValue = "0", required = false) String page)
            throws BaseException {

        // ページング情報を取得(1ページあたりの表示件数は設定ファイルより取得)
        Pageable pageable = PagingViewFactory.getPageable(page, systemConfig.getPaging());

        model.addAttribute("newsList", component.getNewsList(pageable));
        // ページング情報を設定
        model.addAttribute("paging", PagingViewFactory.getPageView(pageable,
                "/news/list?page", component.count()));

        return getView(model, DashboardView.NEWS_LIST);
    }

}
