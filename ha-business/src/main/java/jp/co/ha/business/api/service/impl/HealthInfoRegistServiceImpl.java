package jp.co.ha.business.api.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.api.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.api.service.CommonService;
import jp.co.ha.business.api.service.HealthInfoRegistService;
import jp.co.ha.business.api.type.RequestType;
import jp.co.ha.business.db.crud.create.HealthInfoCreateService;
import jp.co.ha.business.db.crud.read.BmiRangeMtSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.exception.ApiErrorCode;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.healthInfo.service.HealthInfoCalcService;
import jp.co.ha.business.healthInfo.type.HealthInfoStatus;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.DateUtil;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkRequest(HealthInfoRegistRequest request) throws BaseException {

        // リクエスト種別チェック
        if (RequestType.HEALTH_INFO_REGIST != request.getRequestType()) {
            throw new BusinessException(ApiErrorCode.REQUEST_TYPE_INVALID,
                    "リクエスト種別が一致しません リクエスト種別:" + request.getRequestType().getName());
        }

        // API利用判定
        checkApiUse(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(HealthInfoRegistRequest request,
            HealthInfoRegistResponse response) throws BaseException {

        // リクエストをEntityにつめる
        HealthInfo entity = toEntity(request);

        // Entityの登録処理を行う
        healthInfoCreateService.create(entity);

        BeanUtil.copy(entity, response);
    }

    /**
     * 指定した健康情報登録APIリクエスト情報を健康情報Entityに変換する
     *
     * @param request
     *     健康情報登録APIリクエスト情報
     * @return 健康情報Entity
     * @throws BaseException
     *     基底例外
     */
    private HealthInfo toEntity(HealthInfoRegistRequest request) throws BaseException {

        String userId = request.getAccount().getUserId();
        BigDecimal height = request.getHeight();
        BigDecimal weight = request.getWeight();

        // メートルに変換する
        BigDecimal meterHeight = healthInfoCalcService.convertMeterFromCentiMeter(height);

        BigDecimal bmi = healthInfoCalcService.calcBmi(meterHeight, weight, 2);
        BigDecimal standardWeight = healthInfoCalcService.calcStandardWeight(meterHeight,
                2);

        // 最後に登録した健康情報を取得する
        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("SEQ_HEALTH_INFO_ID", SortType.DESC).limit(1).build();
        List<HealthInfo> lastHealthInfo = healthInfoSearchService.findByUserId(userId,
                selectOption);

        HealthInfoStatus status = CollectionUtil.isEmpty(lastHealthInfo)
                ? HealthInfoStatus.EVEN
                : healthInfoCalcService.getHealthInfoStatus(weight,
                        lastHealthInfo.get(0).getWeight());

        List<BmiRangeMt> mtList = bmiRangeMtSearchService.findAll();
        BmiRangeMt bmiRangeMt = mtList.stream().filter(
                e -> e.getRangeMin().intValue() <= bmi.intValue()
                        && bmi.intValue() < e.getRangeMax().intValue())
                .findFirst().orElseThrow(
                        () -> new BusinessException(CommonErrorCode.DB_NO_DATA,
                                "BMI範囲マスタが取得失敗しました。BMI範囲マスタを確認してください"));

        HealthInfo entity = new HealthInfo();
        BeanUtil.copy(request, entity);
        entity.setUserId(userId);
        entity.setBmi(bmi);
        entity.setStandardWeight(standardWeight);
        entity.setHealthInfoStatus(status.getValue());
        entity.setSeqBmiRangeId(bmiRangeMt.getSeqBmiRangeId());
        entity.setHealthInfoRegDate(DateUtil.getSysDate());

        return entity;

    }

}
