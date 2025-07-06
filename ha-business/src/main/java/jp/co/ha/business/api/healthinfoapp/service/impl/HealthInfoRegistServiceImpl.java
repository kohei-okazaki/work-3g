package jp.co.ha.business.api.healthinfoapp.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.api.healthinfoapp.request.HealthInfoRegistApiRequest;
import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse;
import jp.co.ha.business.api.healthinfoapp.response.HealthInfoRegistApiResponse;
import jp.co.ha.business.api.healthinfoapp.service.CommonService;
import jp.co.ha.business.api.healthinfoapp.service.HealthInfoRegistService;
import jp.co.ha.business.api.node.request.BasicHealthInfoCalcApiRequest;
import jp.co.ha.business.api.node.response.BasicHealthInfoCalcApiResponse;
import jp.co.ha.business.api.node.response.BasicHealthInfoCalcApiResponse.BasicHealthInfo;
import jp.co.ha.business.api.node.response.TokenApiResponse;
import jp.co.ha.business.component.ApiCommunicationDataComponent;
import jp.co.ha.business.component.BasicHealthInfoCalcApiComponent;
import jp.co.ha.business.component.TokenApiComponent;
import jp.co.ha.business.db.crud.create.HealthInfoCreateService;
import jp.co.ha.business.db.crud.create.impl.HealthInfoCreateServiceImpl;
import jp.co.ha.business.db.crud.read.BmiRangeMtSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.db.crud.read.impl.BmiRangeMtSearchServiceImpl;
import jp.co.ha.business.db.crud.read.impl.HealthInfoSearchServiceImpl;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.healthInfo.service.HealthInfoCalcService;
import jp.co.ha.business.healthInfo.service.impl.HealthInfoCalcServiceImpl;
import jp.co.ha.business.healthInfo.type.HealthInfoStatus;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.db.entity.BmiRangeMt;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報登録サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class HealthInfoRegistServiceImpl extends CommonService
        implements HealthInfoRegistService {

    /** {@linkplain HealthInfoSearchServiceImpl} */
    @Autowired
    private HealthInfoSearchService healthInfoSearchService;
    /** {@linkplain HealthInfoCalcServiceImpl} */
    @Autowired
    private HealthInfoCalcService healthInfoCalcService;
    /** {@linkplain HealthInfoCreateServiceImpl} */
    @Autowired
    private HealthInfoCreateService healthInfoCreateService;
    /** {@linkplain BmiRangeMtSearchServiceImpl} */
    @Autowired
    private BmiRangeMtSearchService bmiRangeMtSearchService;
    /** {@linkplain ApiCommunicationDataComponent} */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;
    /** {@linkplain TokenApiComponent} */
    @Autowired
    private TokenApiComponent tokenApiComponent;
    /** {@linkplain BasicHealthInfoCalcApiComponent} */
    @Autowired
    private BasicHealthInfoCalcApiComponent basicHealthInfoCalcApiComponent;
    /** {@linkplain HealthInfoProperties} */
    @Autowired
    private HealthInfoProperties prop;

    @Override
    public void checkRequest(HealthInfoRegistApiRequest request) throws BaseException {

        // API利用判定
        checkApiUse(request);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void execute(HealthInfoRegistApiRequest request,
            HealthInfoRegistApiResponse response) throws BaseException {

        if (request.getTransactionId() == null) {
            // API通信情報.トランザクションIDを採番
            request.setTransactionId(apiCommunicationDataComponent.getTransactionId());
        }

        BasicHealthInfoCalcApiRequest basicHealthInfoCalcRequest = new BasicHealthInfoCalcApiRequest();
        BeanUtil.copy(request, basicHealthInfoCalcRequest);

        BasicHealthInfoCalcApiResponse basicHealthInfoCalcResponse;
        if (prop.isHealthinfoNodeApiMigrateFlg()) {

            // 基礎健康情報計算API実施
            basicHealthInfoCalcResponse = basicHealthInfoCalcApiComponent
                    .callBasicHealthInfoCalcApi(basicHealthInfoCalcRequest,
                            request.getTransactionId());

        } else {

            // トークン発行API実施
            TokenApiResponse tokenResponse = tokenApiComponent.callTokenApi(
                    request.getSeqUserId(), request.getTransactionId());

            // 基礎健康情報計算API実施
            basicHealthInfoCalcResponse = basicHealthInfoCalcApiComponent
                    .callBasicHealthInfoCalcApi(basicHealthInfoCalcRequest,
                            tokenResponse.getToken(), request.getTransactionId());
        }

        // リクエストをEntityに変換
        HealthInfo entity = toEntity(request,
                basicHealthInfoCalcResponse.getBasicHealthInfo());

        // Entityの登録処理を行う
        healthInfoCreateService.create(entity);

        BaseAppApiResponse.Account account = new BaseAppApiResponse.Account();
        account.setSeqUserId(request.getSeqUserId());
        response.setAccount(account);

        HealthInfoRegistApiResponse.HealthInfo healthInfo = new HealthInfoRegistApiResponse.HealthInfo();
        BeanUtil.copy(entity, healthInfo);
        response.setHealthInfo(healthInfo);

    }

    /**
     * 指定した健康情報登録APIリクエスト情報を健康情報Entityに変換する
     *
     * @param request
     *     健康情報登録APIリクエスト情報
     * @param basicHealthInfo
     *     基礎健康情報計算APIレスポンス
     * @return 健康情報Entity
     * @throws BaseException
     *     基底例外
     */
    private HealthInfo toEntity(HealthInfoRegistApiRequest request,
            BasicHealthInfo basicHealthInfo) throws BaseException {

        Long seqUserId = request.getSeqUserId();
        BigDecimal weight = request.getWeight();
        BigDecimal bmi = basicHealthInfo.getBmi();

        // 最後に登録した健康情報を取得する
        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("SEQ_HEALTH_INFO_ID", SortType.DESC).limit(1).build();
        List<HealthInfo> lastHealthInfo = healthInfoSearchService
                .findBySeqUserId(seqUserId, selectOption);

        HealthInfoStatus status = CollectionUtil.isEmpty(lastHealthInfo)
                ? HealthInfoStatus.EVEN
                : healthInfoCalcService.getHealthInfoStatus(weight,
                        lastHealthInfo.get(0).getWeight());

        BmiRangeMt bmiRangeMt = bmiRangeMtSearchService.findAll().stream()
                .filter(e -> e.getRangeMin().intValue() <= bmi.intValue()
                        && bmi.intValue() < e.getRangeMax().intValue())
                .findFirst()
                .orElseThrow(() -> new BusinessException(CommonErrorCode.DB_NO_DATA,
                        "BMI範囲マスタが取得失敗しました。BMI範囲マスタを確認してください"));

        HealthInfo entity = new HealthInfo();
        BeanUtil.copy(request, entity);
        entity.setSeqUserId(seqUserId);
        entity.setBmi(bmi);
        entity.setStandardWeight(basicHealthInfo.getStandardWeight());
        entity.setHealthInfoStatus(status.getValue());
        entity.setSeqBmiRangeMtId(bmiRangeMt.getSeqBmiRangeMtId());
        entity.setHealthInfoRegDate(DateTimeUtil.getSysDate());

        return entity;

    }

}
