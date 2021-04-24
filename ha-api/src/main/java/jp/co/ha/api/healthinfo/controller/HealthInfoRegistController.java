package jp.co.ha.api.healthinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.healthinfo.request.HealthInfoRegistApiRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoRegistApiResponse;
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
        BaseRestController<HealthInfoRegistApiRequest, HealthInfoRegistApiResponse> {

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
    public HealthInfoRegistApiResponse doPost(@PathVariable("seqUserId") Long seqUserId,
            @RequestHeader(ApiConnectInfo.X_API_KEY) String apiKey,
            @RequestBody HealthInfoRegistApiRequest request) throws BaseException {

        request.setSeqUserId(seqUserId);
        request.setApiKey(apiKey);

        super.validate(request);

        HealthInfoRegistApiResponse response = new HealthInfoRegistApiResponse();

        this.accept(request, response);

        return response;
    }

    @Override
    public void accept(HealthInfoRegistApiRequest request, HealthInfoRegistApiResponse response)
            throws BaseException {

        service.checkRequest(request);

        service.execute(request, response);

    }

}
