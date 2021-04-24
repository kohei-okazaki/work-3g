package jp.co.ha.api.healthinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.healthinfo.request.HealthInfoReferenceApiRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoReferenceApiResponse;
import jp.co.ha.business.api.healthinfo.service.HealthInfoReferenceService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.web.controller.BaseRestController;

/**
 * 健康情報照会APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/api/{seqUserId}/healthinfo/{seqHealthInfoId}")
public class HealthInfoReferenceController extends
        BaseRestController<HealthInfoReferenceApiRequest, HealthInfoReferenceApiResponse> {

    /** 健康情報照会サービス */
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
    public HealthInfoReferenceApiResponse doGet(@PathVariable("seqUserId") Long seqUserId,
            @PathVariable("seqHealthInfoId") Long seqHealthInfoId,
            @RequestHeader(value = "Api-Key") String apiKey) throws BaseException {

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
