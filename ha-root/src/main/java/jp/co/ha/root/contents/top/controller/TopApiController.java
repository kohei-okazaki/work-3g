package jp.co.ha.root.contents.top.controller;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.db.crud.read.UserSearchService;
import jp.co.ha.business.db.crud.read.impl.HealthInfoSearchServiceImpl;
import jp.co.ha.business.db.crud.read.impl.UserSearchServiceImpl;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.top.request.TopApiRequest;
import jp.co.ha.root.contents.top.response.TopApiResponse;
import jp.co.ha.root.contents.top.response.TopApiResponse.RegGraph;

/**
 * Top情報取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class TopApiController
        extends BaseRootApiController<TopApiRequest, TopApiResponse> {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(TopApiController.class);

    /** {@linkplain UserSearchServiceImpl} */
    @Autowired
    private UserSearchService userSearchService;
    /** {@linkplain HealthInfoSearchServiceImpl} */
    @Autowired
    private HealthInfoSearchService healthInfoSearchService;

    /**
     * Top情報取得
     *
     * @param request
     *     Top情報取得APIリクエスト
     * @param date
     *     対象年月
     * @return Top情報取得APIレスポンス
     */
    @GetMapping(value = "top", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<TopApiResponse> top(TopApiRequest request,
            @RequestParam(name = "date", required = false) String date) {

        LocalDateTime from = null;
        LocalDateTime to = null;
        try {
            from = getFrom(date);
            to = getTo(date);

        } catch (DateTimeException e) {
            LOG.warn("date parse error. date=" + date, e);
            return ResponseEntity.badRequest().body(
                    getErrorResponse("date format error. date is yyyymm"));
        }

        TopApiResponse response = getSuccessResponse();
        response.setAccountRegGraphList(userSearchService.findMonthly(from, to)
                .stream()
                .map(e -> {
                    RegGraph graph = new RegGraph();
                    BeanUtil.copy(e, graph);
                    return graph;
                }).collect(Collectors.toList()));
        response.setHealthInfoRegGraphList(healthInfoSearchService.findMonthly(from, to)
                .stream()
                .map(e -> {
                    RegGraph graph = new RegGraph();
                    BeanUtil.copy(e, graph);
                    return graph;
                }).collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }

    /**
     * Top情報(健康情報)取得
     *
     * @param request
     *     Top情報取得APIリクエスト
     * @param date
     *     対象年月
     * @return Top情報取得APIレスポンス
     */
    @GetMapping(value = "top/healthinfo", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<TopApiResponse> healthinfo(TopApiRequest request,
            @RequestParam(name = "date", required = false) String date) {

        LocalDateTime from = null;
        LocalDateTime to = null;
        try {
            from = getFrom(date);
            to = getTo(date);

        } catch (DateTimeException e) {
            LOG.warn("date parse error. date=" + date, e);
            return ResponseEntity.badRequest().body(
                    getErrorResponse("date format error. date is yyyymm"));
        }

        TopApiResponse response = getSuccessResponse();
        response.setHealthInfoRegGraphList(healthInfoSearchService.findMonthly(from, to)
                .stream()
                .map(e -> {
                    RegGraph graph = new RegGraph();
                    BeanUtil.copy(e, graph);
                    return graph;
                }).collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }

    /**
     * Top情報(ユーザ情報)取得
     *
     * @param request
     *     Top情報取得APIリクエスト
     * @param date
     *     対象年月
     * @return Top情報取得APIレスポンス
     */
    @GetMapping(value = "top/account", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<TopApiResponse> account(TopApiRequest request,
            @RequestParam(name = "date", required = false) String date) {

        LocalDateTime from = null;
        LocalDateTime to = null;
        try {
            from = getFrom(date);
            to = getTo(date);

        } catch (DateTimeException e) {
            LOG.warn("date parse error. date=" + date, e);
            return ResponseEntity.badRequest().body(
                    getErrorResponse("date format error. date is yyyymm"));
        }

        TopApiResponse response = getSuccessResponse();
        response.setAccountRegGraphList(userSearchService.findMonthly(from, to)
                .stream()
                .map(e -> {
                    RegGraph graph = new RegGraph();
                    BeanUtil.copy(e, graph);
                    return graph;
                }).collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }

    @Override
    protected TopApiResponse getResponse() {
        return new TopApiResponse();
    }

    /**
     * 処理対象年月(開始)を返す
     *
     * @param date
     *     対象年月
     * @return 処理対象年月(開始)
     */
    private LocalDateTime getFrom(String date) {
        LocalDate localDate = getDate(date);
        return LocalDateTime.of(localDate.getYear(), localDate.getMonthValue(), 1, 0, 0,
                0);
    }

    /**
     * 処理対象年月(終了)を返す
     *
     * @param date
     *     対象年月
     * @return 処理対象年月(終了)
     */
    private LocalDateTime getTo(String date) {
        LocalDate localDate = getDate(date);
        return LocalDateTime.of(localDate.getYear(), localDate.getMonthValue(),
                DateTimeUtil.getLastDayOfMonth(localDate), 23, 59, 59);
    }

    /**
     * 対象年月を返す<br>
     * <ul>
     * <li>未指定の場合、システム日付</li>
     * <li>指定されている場合、指定値+01</li>
     * </ul>
     *
     * @param date
     *     対象年月
     * @return 対象年月
     */
    private LocalDate getDate(String date) {
        if (StringUtil.isEmpty(date)) {
            return DateTimeUtil.toLocalDate(DateTimeUtil.getSysDate());
        }
        return DateTimeUtil.toLocalDate(date + "01",
                DateFormatType.YYYYMMDD_NOSEP_STRICT);
    }

}
