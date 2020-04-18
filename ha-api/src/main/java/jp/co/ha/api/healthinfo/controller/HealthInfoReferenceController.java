package jp.co.ha.api.healthinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.healthinfo.HealthInfoReferenceApi;
import jp.co.ha.business.api.healthinfo.request.HealthInfoReferenceRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoReferenceResponse;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.web.controller.BaseUserAuthGetRestController;

/**
 * 健康情報照会コントローラ
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/api/{userId}/healthinfo/{seqHealthInfoId}")
public class HealthInfoReferenceController extends
        BaseUserAuthGetRestController<HealthInfoReferenceRequest, HealthInfoReferenceResponse> {

    /** 健康情報照会API */
    @Autowired
    private HealthInfoReferenceApi api;

    /**
     * 健康情報照会APIを受け付ける
     *
     * @param userId
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
    public HealthInfoReferenceResponse doGet(@PathVariable("userId") String userId,
            @PathVariable("seqHealthInfoId") Integer seqHealthInfoId,
            @RequestHeader(value = "Api-Key", required = false) String apiKey)
            throws BaseException {

        HealthInfoReferenceRequest request = getApiRequest();
        HealthInfoReferenceResponse response = getApiResponse();

        request.setUserId(userId);
        request.setApiKey(apiKey);
        request.setSeqHealthInfoId(seqHealthInfoId);

        super.validate(request);

        this.accept(request, response);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected HealthInfoReferenceRequest getApiRequest() {
        return new HealthInfoReferenceRequest();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HealthInfoReferenceResponse getApiResponse() {
        return new HealthInfoReferenceResponse();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(HealthInfoReferenceRequest request,
            HealthInfoReferenceResponse response) throws BaseException {

        api.execute(request, response);

    }

}
