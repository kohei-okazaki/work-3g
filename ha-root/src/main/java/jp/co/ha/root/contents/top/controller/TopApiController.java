package jp.co.ha.root.contents.top.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.top.request.TopApiRequest;
import jp.co.ha.root.contents.top.response.TopApiResponse;
import jp.co.ha.root.type.RootApiResult;

/**
 * Top情報取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class TopApiController
        extends BaseRootApiController<TopApiRequest, TopApiResponse> {

    /** アカウント情報検索サービス */
    @Autowired
    private AccountSearchService accountSearchService;
    /** 健康情報検索サービス */
    @Autowired
    private HealthInfoSearchService healthInfoSearchService;

    /**
     * Top情報取得
     *
     * @param request
     *     Top情報取得APIリクエスト
     * @return Top情報取得APIレスポンス
     */
    @GetMapping(value = "top", produces = { MediaType.APPLICATION_JSON_VALUE })
    public TopApiResponse top(@RequestParam Map<String, String> request) {

        TopApiRequest apiRequest = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .convertValue(request, TopApiRequest.class);

        LocalDate targetDate = DateTimeUtil.toLocalDate(DateTimeUtil.getSysDate());
        LocalDateTime from = LocalDateTime.of(targetDate.getYear(),
                targetDate.getMonthValue(), 1, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(targetDate.getYear(),
                targetDate.getMonthValue(),
                DateTimeUtil.getLastDayOfMonth(targetDate), 23, 59, 59);

        TopApiResponse response = new TopApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);
        response.setAccountRegGraphList(accountSearchService.findMonthly(from, to)
                .stream()
                .map(e -> {
                    TopApiResponse.RegGraph graph = new TopApiResponse.RegGraph();
                    BeanUtil.copy(e, graph);
                    return graph;
                }).collect(Collectors.toList()));
        response.setHealthInfoRegGraphList(healthInfoSearchService.findMonthly(from, to)
                .stream()
                .map(e -> {
                    TopApiResponse.RegGraph graph = new TopApiResponse.RegGraph();
                    BeanUtil.copy(e, graph);
                    return graph;
                }).collect(Collectors.toList()));

        return response;
    }

    /**
     * Top情報(健康情報)取得
     *
     * @param request
     *     Top情報取得APIリクエスト
     * @return Top情報取得APIレスポンス
     */
    @GetMapping(value = "top/healthinfo", produces = { MediaType.APPLICATION_JSON_VALUE })
    public TopApiResponse healthinfo(@RequestParam Map<String, String> request) {

        LocalDate targetDate = DateTimeUtil.toLocalDate(DateTimeUtil.getSysDate());
        LocalDateTime from = LocalDateTime.of(targetDate.getYear(),
                targetDate.getMonthValue(), 1, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(targetDate.getYear(),
                targetDate.getMonthValue(),
                DateTimeUtil.getLastDayOfMonth(targetDate), 23, 59, 59);

        TopApiResponse response = new TopApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);
        response.setHealthInfoRegGraphList(healthInfoSearchService.findMonthly(from, to)
                .stream()
                .map(e -> {
                    TopApiResponse.RegGraph graph = new TopApiResponse.RegGraph();
                    BeanUtil.copy(e, graph);
                    return graph;
                }).collect(Collectors.toList()));

        return response;
    }

    /**
     * Top情報(アカウント情報)取得
     *
     * @param request
     *     Top情報取得APIリクエスト
     * @return Top情報取得APIレスポンス
     */
    @GetMapping(value = "top/account", produces = { MediaType.APPLICATION_JSON_VALUE })
    public TopApiResponse account(@RequestParam Map<String, String> request) {

        LocalDate targetDate = DateTimeUtil.toLocalDate(DateTimeUtil.getSysDate());
        LocalDateTime from = LocalDateTime.of(targetDate.getYear(),
                targetDate.getMonthValue(), 1, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(targetDate.getYear(),
                targetDate.getMonthValue(),
                DateTimeUtil.getLastDayOfMonth(targetDate), 23, 59, 59);

        TopApiResponse response = new TopApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);
        response.setAccountRegGraphList(accountSearchService.findMonthly(from, to)
                .stream()
                .map(e -> {
                    TopApiResponse.RegGraph graph = new TopApiResponse.RegGraph();
                    BeanUtil.copy(e, graph);
                    return graph;
                }).collect(Collectors.toList()));

        return response;
    }
}
