package jp.co.ha.root.contents.healthinfo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.node.request.BasicHealthInfoCalcRequest;
import jp.co.ha.business.api.node.response.BasicHealthInfoCalcResponse;
import jp.co.ha.business.api.node.response.TokenResponse;
import jp.co.ha.business.component.ApiCommunicationDataComponent;
import jp.co.ha.business.component.BasicHealthInfoCalcApiComponent;
import jp.co.ha.business.component.TokenApiComponent;
import jp.co.ha.business.db.crud.update.HealthInfoUpdateService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
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

    /** API通信情報Component */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;
    /** トークン発行APIComponent */
    @Autowired
    private TokenApiComponent tokenApiComponent;
    /** 基礎健康情報計算APIComponent */
    @Autowired
    private BasicHealthInfoCalcApiComponent basicHealthInfoCalcApiComponent;
    /** 健康情報更新サービス */
    @Autowired
    private HealthInfoUpdateService healthInfoUpdateService;

    /**
     * 健康情報編集
     *
     * @param id
     *     健康情報ID
     * @param request
     *     健康情報編集APIリクエスト
     * @return 健康情報編集APIレスポンス
     * @throws BaseException
     *     API呼び出しに失敗した場合
     */
    @PutMapping(value = "healthinfo/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public HealthInfoEditApiResponse edit(
            @PathVariable(name = "id", required = false) Optional<String> id,
            @RequestBody HealthInfoEditApiRequest request) throws BaseException {

        // API通信情報.トランザクションIDを採番
        Integer transactionId = apiCommunicationDataComponent.getTransactionId();
        // トークン発行API実施
        TokenResponse tokenResponse = tokenApiComponent.callTokenApi(
                request.getSeqUserId(), transactionId);

        // 基礎健康情報計算API実施
        BasicHealthInfoCalcRequest basicHealthInfoCalcRequest = new BasicHealthInfoCalcRequest();
        BeanUtil.copy(request, basicHealthInfoCalcRequest);
        BasicHealthInfoCalcResponse basicHealthInfoCalcResponse = basicHealthInfoCalcApiComponent
                .callBasicHealthInfoCalcApi(basicHealthInfoCalcRequest,
                        tokenResponse.getToken(), request.getSeqUserId(), transactionId);

        HealthInfo healthInfo = new HealthInfo();
        healthInfoUpdateService.update(healthInfo);

        HealthInfoEditApiResponse response = getSuccessResponse();
        return response;
    }

    @Override
    protected HealthInfoEditApiResponse getResponse() {
        return new HealthInfoEditApiResponse();
    }

}
