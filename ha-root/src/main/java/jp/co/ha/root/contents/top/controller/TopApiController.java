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

import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.StringUtil;
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

    /** ObjectMapper */
    @Autowired
    private ObjectMapper mapper;
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

        TopApiRequest apiRequest = mapper.convertValue(request, TopApiRequest.class);
        LocalDateTime from = getFrom(apiRequest);
        LocalDateTime to = getTo(apiRequest);

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

        TopApiRequest apiRequest = mapper.convertValue(request, TopApiRequest.class);
        LocalDateTime from = getFrom(apiRequest);
        LocalDateTime to = getTo(apiRequest);

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

        TopApiRequest apiRequest = mapper.convertValue(request, TopApiRequest.class);
        LocalDateTime from = getFrom(apiRequest);
        LocalDateTime to = getTo(apiRequest);

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

    /**
     * 処理対象年月(開始)を返す
     *
     * @param request
     *     Top情報取得APIリクエスト
     * @return 処理対象年月(開始)
     */
    private LocalDateTime getFrom(TopApiRequest request) {
        LocalDate date = getDate(request);
        return LocalDateTime.of(date.getYear(), date.getMonthValue(), 1, 0, 0, 0);
    }

    /**
     * 処理対象年月(終了)を返す
     *
     * @param request
     *     Top情報取得APIリクエスト
     * @return 処理対象年月(終了)
     */
    private LocalDateTime getTo(TopApiRequest request) {
        LocalDate date = getDate(request);
        return LocalDateTime.of(date.getYear(), date.getMonthValue(),
                DateTimeUtil.getLastDayOfMonth(date), 23, 59, 59);
    }

    /**
     * 対象年月を返す
     *
     * @param request
     *     Top情報取得APIリクエスト
     * @return 対象年月
     */
    private LocalDate getDate(TopApiRequest request) {
        return StringUtil.isEmpty(request.getDate())
                ? DateTimeUtil.toLocalDate(DateTimeUtil.getSysDate())
                : DateTimeUtil.toLocalDate(request.getDate() + "01",
                        DateFormatType.YYYYMMDD_NOSEP_STRICT);
    }
}
