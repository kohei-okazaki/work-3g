package jp.co.ha.root.contents.top.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.db.entity.composite.CompositeMonthlyHealthInfo;
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
    public TopApiResponse top(TopApiRequest request) {

        LocalDate targetDate = DateTimeUtil.toLocalDate(DateTimeUtil.getSysDate());
        LocalDateTime from = LocalDateTime.of(targetDate.getYear(),
                targetDate.getMonthValue(),
                1, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(targetDate.getYear(),
                targetDate.getMonthValue(),
                DateTimeUtil.getLastDayOfMonth(targetDate), 23, 59, 59);

        List<CompositeMonthlyHealthInfo> healthInfoList = healthInfoSearchService
                .findMonthly(from, to);

        TopApiResponse response = new TopApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);
        response.setHealthInfoRegGraphList(healthInfoList.stream().map(e -> {
            TopApiResponse.HealthInfoRegGraph graph = new TopApiResponse.HealthInfoRegGraph();
            BeanUtil.copy(e, graph);
            return graph;
        }).collect(Collectors.toList()));

        return response;
    }
}
