package jp.co.ha.batch.healthInfoMigrateBatch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.track.HealthInfoMigrateApi;
import jp.co.ha.business.api.track.request.HealthInfoMigrateApiRequest;
import jp.co.ha.business.api.track.response.HealthInfoMigrateApiResponse;
import jp.co.ha.business.component.ApiCommunicationDataComponent;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.db.crud.read.impl.HealthInfoSearchServiceImpl;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報連携バッチ
 * <ul>
 * <li>引数1=処理対象日(YYYYMMDD)</li>
 * </ul>
 *
 * @version 1.0.0
 */
@StepScope
@Component
public class HealthInfoMigrateBatch implements Tasklet {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(HealthInfoMigrateBatch.class);
    /** 処理対象日(YYYYMMDD) */
    @Value("#{jobParameters[d]}")
    private String targetDate;
    /** {@linkplain HealthInfoSearchServiceImpl} */
    @Autowired
    private HealthInfoSearchService searchService;
    /** {@linkplain HealthInfoProperties} */
    @Autowired
    private HealthInfoProperties prop;
    /** {@linkplain ApiCommunicationDataComponent} */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;
    /** {@linkplain HealthInfoMigrateApi} */
    @Autowired
    private HealthInfoMigrateApi api;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {

        targetDate = StringUtil.isEmpty(targetDate)
                ? DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                        DateFormatType.YYYYMMDD_NOSEP)
                : targetDate;
        LOG.debug("targetDate=" + targetDate);

        // 健康情報リスト
        List<HealthInfo> healthInfoList = getHealthInfoList();

        if (CollectionUtil.exists(healthInfoList)) {
            sendHealthInfoMirgateApi(healthInfoList);
        }

        return RepeatStatus.FINISHED;
    }

    /**
     * 処理対象日の健康情報リストを返す
     *
     * @return 健康情報リスト
     */
    private List<HealthInfo> getHealthInfoList() {

        int year = Integer.parseInt(targetDate.substring(0, 4));
        int month = Integer.parseInt(targetDate.substring(4, 6));
        int day = Integer.parseInt(targetDate.substring(6, 8));
        LocalDateTime from = LocalDateTime.of(year, month, day, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(year, month, day, 23, 59, 59);
        LOG.debug("from=" + from + ", to=" + to);

        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("SEQ_USER_ID", SortType.ASC)
                .orderBy("HEALTH_INFO_REG_DATE", SortType.DESC)
                .build();

        return searchService.findBySeqUserIdBetweenHealthInfoRegDate(null, from, to,
                selectOption);
    }

    /**
     * 健康情報連携APIを呼び出す
     * 
     * @param healthInfoList
     *     健康情報リスト
     * @throws BaseException
     *     JSON変換に失敗した場合
     */
    private void sendHealthInfoMirgateApi(List<HealthInfo> healthInfoList)
            throws BaseException {

        Long transactionId = apiCommunicationDataComponent.getTransactionId();

        ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getTrackApiUrl() + "healthinfo/");

        List<HealthInfoMigrateApiRequest> requestList = new ArrayList<>();
        for (Entry<Long, List<HealthInfo>> entry : toMap(healthInfoList).entrySet()) {
            requestList.add(toRequest(entry.getKey(), entry.getValue()));
        }

        for (HealthInfoMigrateApiRequest request : requestList) {
            // API通信情報を登録
            ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                    .create(api.getApiName(), transactionId, api.getHttpMethod(),
                            api.getUri(apiConnectInfo, request), request);

            HealthInfoMigrateApiResponse response = api.callApi(request, apiConnectInfo);

            // API通信情報を更新
            apiCommunicationDataComponent.update(apiCommunicationData, apiConnectInfo,
                    response);
        }

    }

    /**
     * 健康情報リストからユーザID->健康情報リストのマップを返す
     * 
     * @param healthInfoList
     *     健康情報リスト
     * @return ユーザID->健康情報リスト
     */
    private Map<Long, List<HealthInfo>> toMap(List<HealthInfo> healthInfoList) {
        Map<Long, List<HealthInfo>> map = new LinkedHashMap<>();
        for (HealthInfo healthInfo : healthInfoList) {
            if (map.containsKey(healthInfo.getSeqUserId())) {
                map.get(healthInfo.getSeqUserId()).add(healthInfo);
            } else {
                List<HealthInfo> list = new ArrayList<>();
                list.add(healthInfo);
                map.put(healthInfo.getSeqUserId(), list);
            }
        }
        return map;
    }

    /**
     * ユーザIDと健康情報リストから健康情報連携APIのリクエスト情報へ変換する
     * 
     * @param seqUserId
     *     ユーザID
     * @param healthInfoList
     *     健康情報リスト
     * @return 健康情報連携APIのリクエスト情報
     */
    private HealthInfoMigrateApiRequest toRequest(Long seqUserId,
            List<HealthInfo> healthInfoList) {

        HealthInfoMigrateApiRequest req = new HealthInfoMigrateApiRequest();

        req.setSeqUserId(seqUserId);
        List<HealthInfoMigrateApiRequest.HealthInfo> list = healthInfoList.stream()
                .map(e -> {
                    HealthInfoMigrateApiRequest.HealthInfo entity = new HealthInfoMigrateApiRequest.HealthInfo();
                    BeanUtil.copy(e, entity);
                    return entity;
                }).collect(Collectors.toList());
        req.setHealthInfoList(list);

        return req;
    }

}
