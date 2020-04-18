package jp.co.ha.api.healthinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.healthinfo.HealthInfoRegistApi;
import jp.co.ha.business.api.healthinfo.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoRegistResponse;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.web.controller.BaseRestController;

/**
 * 健康情報登録APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/api/{userId}/healthinfo")
public class HealthInfoRegistController extends
        BaseRestController<HealthInfoRegistRequest, HealthInfoRegistResponse> {

    /** 健康情報登録API */
    @Autowired
    private HealthInfoRegistApi api;

    /**
     * ユーザIDを必要とするPOST形式のJSON通信の受付を行う
     *
     * @param userId
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
    public HealthInfoRegistResponse doPost(@PathVariable("userId") String userId,
            @RequestHeader("Api-Key") String apiKey,
            @RequestBody HealthInfoRegistRequest request) throws BaseException {

        request.setUserId(userId);
        request.setApiKey(apiKey);

        super.validate(request);

        HealthInfoRegistResponse response = new HealthInfoRegistResponse();

        this.accept(request, response);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(HealthInfoRegistRequest request, HealthInfoRegistResponse response)
            throws BaseException {

        api.execute(request, response);

    }

}
