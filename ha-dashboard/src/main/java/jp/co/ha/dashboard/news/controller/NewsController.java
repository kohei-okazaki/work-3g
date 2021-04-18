package jp.co.ha.dashboard.news.controller;

import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.aws.AwsS3Key;
import jp.co.ha.business.dto.NewsListDto;
import jp.co.ha.business.dto.NewsListDto.NewsDto;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.system.SystemConfig;
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

    /** System設定情報 */
    @Autowired
    private SystemConfig systemConfig;
    /** S3コンポーネント */
    @Autowired
    private AwsS3Component awsS3Component;

    /**
     * お知らせ一覧
     *
     * @param model
     *     {@linkplain Model}
     * @return お知らせ一覧画面
     * @throws BaseException
     *     JSONファイルの読込に失敗した場合
     */
    @GetMapping("/list")
    public String list(Model model) throws BaseException {

        // S3からお知らせJSONを取得
        InputStream is = awsS3Component.getS3ObjectByKey(AwsS3Key.NEWS_JSON);
        List<NewsDto> newsList = new JsonReader().read(is, NewsListDto.class)
                .getNewsDtoList();

        // Indexの降順でソート
        newsList = newsList.stream()
                .sorted(Comparator.comparing(NewsDto::getIndex).reversed())
                .collect(Collectors.toList());

        model.addAttribute("newsList", newsList);
        model.addAttribute("systemConfig", systemConfig);

        return getView(model, DashboardView.NEWS_LIST);
    }

}
