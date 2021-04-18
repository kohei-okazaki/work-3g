package jp.co.ha.api.healthinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.healthinfo.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoRegistResponse;
import jp.co.ha.business.api.healthinfo.service.HealthInfoRegistService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.common.web.controller.BaseRestController;

/**
 * 健康情報登録APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/api/{seqUserId}/healthinfo")
public class HealthInfoRegistController extends
        BaseRestController<HealthInfoRegistRequest, HealthInfoRegistResponse> {

    /** 健康情報登録サービス */
    @Autowired
    private HealthInfoRegistService service;

    /**
     * 健康情報登録APIを受け付ける
     *
     * @param seqUserId
     *     ユーザID
     * @param apiKey
     *     APIキー
     * @param request
     *     リクエスト情報
     * @return レスポンス
     * @throws BaseException
     *     基底例外
     */
    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public HealthInfoRegistResponse doPost(@PathVariable("seqUserId") Long seqUserId,
            @RequestHeader(ApiConnectInfo.X_API_KEY) String apiKey,
            @RequestBody HealthInfoRegistRequest request) throws BaseException {

        request.setSeqUserId(seqUserId);
        request.setApiKey(apiKey);

        super.validate(request);

        HealthInfoRegistResponse response = new HealthInfoRegistResponse();

        this.accept(request, response);

        return response;
    }

    @Override
    public void accept(HealthInfoRegistRequest request, HealthInfoRegistResponse response)
            throws BaseException {

        service.checkRequest(request);

        service.execute(request, response);

    }

}
