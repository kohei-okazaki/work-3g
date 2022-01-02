package jp.co.ha.api.healthinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.healthinfoapp.request.HealthInfoReferenceApiRequest;
import jp.co.ha.business.api.healthinfoapp.response.HealthInfoReferenceApiResponse;
import jp.co.ha.business.api.healthinfoapp.service.HealthInfoReferenceService;
import jp.co.ha.business.api.healthinfoapp.service.impl.HealthInfoReferenceServiceImpl;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.common.web.controller.BaseRestController;

/**
 * 健康情報照会APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/api/{seq_user_id}/healthinfo/{seq_health_info_id}")
public class HealthInfoReferenceApiController extends
        BaseRestController<HealthInfoReferenceApiRequest, HealthInfoReferenceApiResponse> {

    /** {@linkplain HealthInfoReferenceServiceImpl} */
    @Autowired
    private HealthInfoReferenceService service;

    /**
     * 健康情報照会APIを受け付ける
     *
     * @param seqUserId
     *     ユーザID
     * @param seqHealthInfoId
     *     健康情報ID
     * @param apiKey
     *     APIキー
     * @return 健康情報照会APIレスポンス
     * @throws BaseException
     *     基底例外
     */
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public HealthInfoReferenceApiResponse doGet(
            @PathVariable("seq_user_id") Long seqUserId,
            @PathVariable("seq_health_info_id") Long seqHealthInfoId,
            @RequestHeader(ApiConnectInfo.X_API_KEY) String apiKey) throws BaseException {

        HealthInfoReferenceApiRequest request = new HealthInfoReferenceApiRequest();
        HealthInfoReferenceApiResponse response = new HealthInfoReferenceApiResponse();

        request.setSeqUserId(seqUserId);
        request.setApiKey(apiKey);
        request.setSeqHealthInfoId(seqHealthInfoId);

        super.validate(request);

        this.accept(request, response);

        return response;
    }

    @Override
    public void accept(HealthInfoReferenceApiRequest request,
            HealthInfoReferenceApiResponse response) throws BaseException {

        service.checkRequest(request);

        service.execute(request, response);

    }

}
