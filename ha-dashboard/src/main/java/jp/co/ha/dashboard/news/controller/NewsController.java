package jp.co.ha.dashboard.news.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.db.crud.read.NewsInfoSearchService;
import jp.co.ha.business.dto.NewsDto;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.system.SystemConfig;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.common.web.controller.BaseWebController;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.db.entity.NewsInfo;

/**
 * お知らせ情報画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("news")
public class NewsController implements BaseWebController {

    /** {@linkplain NewsInfoSearchService} */
    @Autowired
    private NewsInfoSearchService searchService;
    /** {@linkplain SystemConfig} */
    @Autowired
    private SystemConfig systemConfig;
    /** {@linkplain AwsS3Component} */
    @Autowired
    private AwsS3Component awsS3Component;

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
        Pageable pageable = PagingViewFactory.getPageable(page,
                systemConfig.getPaging());

        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("SEQ_NEWS_INFO_ID", SortType.DESC)
                .pageable(pageable)
                .build();

        List<NewsDto> newsList = new ArrayList<>();
        for (NewsInfo entity : searchService.findAll(selectOption)) {
            // S3からお知らせJSONを取得
            NewsDto dto = new JsonReader()
                    .read(awsS3Component.getS3ObjectByKey(entity.getS3Key()),
                            NewsDto.class);
            newsList.add(dto);
        }

        model.addAttribute("newsList", newsList);
        // ページング情報を設定
        model.addAttribute("paging", PagingViewFactory.getPageView(pageable,
                "/news/list?page", searchService.countBySeqNewsInfoId(null)));

        return getView(model, DashboardView.NEWS_LIST);
    }

}
