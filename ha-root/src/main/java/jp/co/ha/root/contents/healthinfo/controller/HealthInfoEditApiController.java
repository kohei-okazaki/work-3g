package jp.co.ha.root.contents.healthinfo.controller;

import static jp.co.ha.common.exception.CommonErrorCode.*;

import java.math.BigDecimal;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.node.request.BasicHealthInfoCalcApiRequest;
import jp.co.ha.business.api.node.response.BasicHealthInfoCalcApiResponse;
import jp.co.ha.business.api.node.response.TokenApiResponse;
import jp.co.ha.business.component.ApiLogComponent;
import jp.co.ha.business.component.BasicHealthInfoCalcApiComponent;
import jp.co.ha.business.component.TokenApiComponent;
import jp.co.ha.business.db.crud.read.BmiRangeMtSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.db.crud.update.HealthInfoUpdateService;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.db.entity.BmiRangeMt;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.healthinfo.request.HealthInfoEditApiRequest;
import jp.co.ha.root.contents.healthinfo.response.HealthInfoEditApiResponse;

/**
 * 健康情報編集APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class HealthInfoEditApiController extends
        BaseRootApiController<HealthInfoEditApiRequest, HealthInfoEditApiResponse> {

    /** API通信ログComponent */
    @Autowired
    private ApiLogComponent apiLogComponent;
    /** トークン発行APIComponent */
    @Autowired
    private TokenApiComponent tokenApiComponent;
    /** 基礎健康情報計算APIComponent */
    @Autowired
    private BasicHealthInfoCalcApiComponent basicHealthInfoCalcApiComponent;
    /** BMI範囲マスタ検索サービス */
    @Autowired
    private BmiRangeMtSearchService bmiRangeMtSearchService;
    /** 健康情報検索サービス */
    @Autowired
    private HealthInfoSearchService healthInfoSearchService;
    /** 健康情報更新サービス */
    @Autowired
    private HealthInfoUpdateService healthInfoUpdateService;
    /** 健康情報設定ファイル */
    @Autowired
    private HealthInfoProperties prop;

    /**
     * 健康情報編集
     *
     * @param seqHealthInfoId
     *     健康情報ID
     * @param request
     *     健康情報編集APIリクエスト
     * @return 健康情報編集APIレスポンス
     * @throws BaseException
     *     API呼び出しに失敗した場合
     */
    @SuppressWarnings("deprecation")
    @PutMapping(value = "healthinfo/{seq_health_info_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HealthInfoEditApiResponse> edit(
            @PathVariable(name = "seq_health_info_id", required = true) Long seqHealthInfoId,
            @Valid @RequestBody HealthInfoEditApiRequest request) throws BaseException {

        if (healthInfoSearchService.countByHealthInfoIdAndSeqUserId(seqHealthInfoId,
                request.getSeqUserId()) == 0L) {
            // 健康情報IDとユーザIDの組み合わせが存在しない場合
            return ResponseEntity.badRequest()
                    .body(getErrorResponse("health_info is not found"));
        }

        // API通信ログ.トランザクションIDを採番
        String transactionId = apiLogComponent.getTransactionId();

        // 基礎健康情報計算API実施
        BasicHealthInfoCalcApiRequest basicHealthInfoCalcRequest = new BasicHealthInfoCalcApiRequest();
        BeanUtil.copy(request, basicHealthInfoCalcRequest);

        BasicHealthInfoCalcApiResponse basicHealthInfoCalcResponse;
        if (prop.isHealthinfoNodeApiMigrateFlg()) {

            // 基礎健康情報計算API実施
            basicHealthInfoCalcResponse = basicHealthInfoCalcApiComponent
                    .callBasicHealthInfoCalcApi(basicHealthInfoCalcRequest,
                            transactionId);

        } else {

            // トークン発行API実施
            TokenApiResponse tokenResponse = tokenApiComponent.callTokenApi(
                    request.getSeqUserId(), transactionId);

            // 基礎健康情報計算API実施
            basicHealthInfoCalcResponse = basicHealthInfoCalcApiComponent
                    .callBasicHealthInfoCalcApi(basicHealthInfoCalcRequest,
                            tokenResponse.getToken(), transactionId);
        }

        BigDecimal bmi = basicHealthInfoCalcResponse.getBasicHealthInfo().getBmi();
        BmiRangeMt bmiRangeMt = bmiRangeMtSearchService.findAll().stream()
                .filter(e -> bmi.compareTo(BigDecimal.valueOf(e.getRangeMin())) >= 0 &&
                        bmi.compareTo(BigDecimal.valueOf(e.getRangeMax())) < 0)
                .findFirst()
                .orElseThrow(() -> new BusinessException(DB_NO_DATA,
                        "BMI範囲マスタの取得に失敗しました。データを確認してください。"));

        HealthInfo healthInfo = new HealthInfo();
        BeanUtil.copy(basicHealthInfoCalcResponse.getBasicHealthInfo(), healthInfo);
        healthInfo.setSeqHealthInfoId(seqHealthInfoId);
        healthInfo.setSeqBmiRangeMtId(bmiRangeMt.getSeqBmiRangeMtId());
        healthInfoUpdateService.update(healthInfo);

        return ResponseEntity.ok(getSuccessResponse());
    }

    @Override
    protected HealthInfoEditApiResponse getResponse() {
        return new HealthInfoEditApiResponse();
    }

}
