package jp.co.ha.business.api.healthinfo.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.api.healthinfo.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoRegistResponse;
import jp.co.ha.business.api.healthinfo.service.CommonService;
import jp.co.ha.business.api.healthinfo.service.HealthInfoRegistService;
import jp.co.ha.business.api.node.BasicHealthInfoCalcApi;
import jp.co.ha.business.api.node.request.BasicHealthInfoCalcRequest;
import jp.co.ha.business.api.node.response.BasicHealthInfoCalcResponse;
import jp.co.ha.business.api.node.response.BasicHealthInfoCalcResponse.BasicHealthInfo;
import jp.co.ha.business.db.crud.create.HealthInfoCreateService;
import jp.co.ha.business.db.crud.read.BmiRangeMtSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.healthInfo.service.HealthInfoCalcService;
import jp.co.ha.business.healthInfo.type.HealthInfoStatus;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.db.entity.BmiRangeMt;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.web.form.BaseNodeResponse.Result;
import jp.co.ha.web.form.BaseRestApiResponse;

/**
 * 健康情報登録サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class HealthInfoRegistServiceImpl extends CommonService
        implements HealthInfoRegistService {

    /** 健康情報検索サービス */
    @Autowired
    private HealthInfoSearchService healthInfoSearchService;
    /** 健康情報計算サービス */
    @Autowired
    private HealthInfoCalcService healthInfoCalcService;
    /** 健康情報作成サービス */
    @Autowired
    private HealthInfoCreateService healthInfoCreateService;
    /** BMI範囲マスタ検索サービス */
    @Autowired
    private BmiRangeMtSearchService bmiRangeMtSearchService;
    /** 基礎健康情報計算API */
    @Autowired
    private BasicHealthInfoCalcApi basicHealthInfoCalcApi;

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkRequest(HealthInfoRegistRequest request) throws BaseException {

        // API利用判定
        checkApiUse(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(HealthInfoRegistRequest request,
            HealthInfoRegistResponse response) throws BaseException {

        // 基礎健康情報計算API実施
        BasicHealthInfoCalcResponse apiResponse = callBasicHealthInfoCalcApi(
                request);

        // リクエストをEntityに変換
        HealthInfo entity = toEntity(request, apiResponse.getBasicHealthInfo());

        // Entityの登録処理を行う
        healthInfoCreateService.create(entity);

        {
            BaseRestApiResponse.Account account = new BaseRestApiResponse.Account();
            account.setUserId(request.getUserId());
            response.setAccount(account);
        }

        {
            HealthInfoRegistResponse.HealthInfo healthInfo = new HealthInfoRegistResponse.HealthInfo();
            BeanUtil.copy(entity, healthInfo);
            response.setHealthInfo(healthInfo);
        }

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
    private HealthInfo toEntity(HealthInfoRegistRequest request,
            BasicHealthInfo basicHealthInfo) throws BaseException {

        String userId = request.getUserId();
        BigDecimal weight = request.getWeight();
        BigDecimal bmi = basicHealthInfo.getBmi();

        // 最後に登録した健康情報を取得する
        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("SEQ_HEALTH_INFO_ID", SortType.DESC).limit(1).build();
        List<HealthInfo> lastHealthInfo = healthInfoSearchService.findByUserId(userId,
                selectOption);

        HealthInfoStatus status = CollectionUtil.isEmpty(lastHealthInfo)
                ? HealthInfoStatus.EVEN
                : healthInfoCalcService.getHealthInfoStatus(weight,
                        lastHealthInfo.get(0).getWeight());

        BmiRangeMt bmiRangeMt = bmiRangeMtSearchService.findAll().stream()
                .filter(e -> e.getRangeMin().intValue() <= bmi.intValue()
                        && bmi.intValue() < e.getRangeMax().intValue())
                .findFirst().orElseThrow(
                        () -> new BusinessException(CommonErrorCode.DB_NO_DATA,
                                "BMI範囲マスタが取得失敗しました。BMI範囲マスタを確認してください"));

        HealthInfo entity = new HealthInfo();
        BeanUtil.copy(request, entity);
        entity.setUserId(userId);
        entity.setBmi(bmi);
        entity.setStandardWeight(basicHealthInfo.getStandardWeight());
        entity.setHealthInfoStatus(status.getValue());
        entity.setSeqBmiRangeId(bmiRangeMt.getSeqBmiRangeId());
        entity.setHealthInfoRegDate(DateUtil.getSysDate());

        return entity;

    }

    /**
     * 基礎健康情報計算APIを呼び出す
     *
     * @param request
     *     リクエスト情報
     * @return 基礎健康情報計算APIレスポンス
     * @throws ApiException
     *     API通信に失敗した場合
     */
    private BasicHealthInfoCalcResponse callBasicHealthInfoCalcApi(
            HealthInfoRegistRequest request) throws ApiException {

        BasicHealthInfoCalcRequest apiRequest = new BasicHealthInfoCalcRequest();
        BeanUtil.copy(request, apiRequest);

        BasicHealthInfoCalcResponse apiResponse = basicHealthInfoCalcApi
                .callApi(apiRequest);

        if (Result.SUCCESS != apiResponse.getResult()) {
            // 基礎健康情報計算APIの処理が成功以外の場合
            throw new ApiException(BusinessErrorCode.BASIC_API_CONNERR,
                    apiResponse.getDetail());
        }

        return apiResponse;
    }

}
