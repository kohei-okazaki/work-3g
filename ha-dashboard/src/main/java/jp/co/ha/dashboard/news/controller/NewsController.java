package jp.co.ha.dashboard.news.controller;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ha.business.dto.NewsListDto;
import jp.co.ha.business.dto.NewsListDto.NewsDto;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.system.SystemConfig;
import jp.co.ha.common.util.FileUtil.FileSeparator;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.web.controller.BaseWebController;

/**
 * お知らせ情報画面コントローラ
 *
 * @since 1.0
 */
@Controller
@RequestMapping("news")
public class NewsController implements BaseWebController {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(NewsController.class);
    /** System設定情報 */
    @Autowired
    private SystemConfig systemConfig;

    /**
     * お知らせ一覧
     *
     * @param model
     *     Model
     * @return お知らせ一覧画面
     * @throws BaseException
     *     JSONファイルの読み込みに失敗した場合
     */
    @GetMapping("/list")
    public String list(Model model) throws BaseException {

        String path = new StringJoiner(FileSeparator.SYSTEM.getValue())
                .add(this.getClass().getClassLoader().getResource("").getPath())
                .add("META-INF")
                .add("news.json")
                .toString();
        LOG.info(path);

        List<NewsDto> newsList = new JsonReader().read(new File(path), NewsListDto.class)
                .getNewsDtoList();
        // Indexの降順ソート
        newsList = newsList.stream()
                .sorted(Comparator.comparing(NewsDto::getIndex).reversed())
                .collect(Collectors.toList());

        model.addAttribute("newsList", newsList);
        model.addAttribute("systemConfig", systemConfig);

        return getView(DashboardView.NEWS_LIST);
    }

}
