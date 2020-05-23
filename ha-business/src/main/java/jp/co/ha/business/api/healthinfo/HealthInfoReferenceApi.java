package jp.co.ha.business.api.healthinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.healthinfo.request.HealthInfoReferenceRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoReferenceResponse;
import jp.co.ha.business.api.healthinfo.service.HealthInfoReferenceService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.web.api.BaseApi;
import jp.co.ha.web.api.annotation.ApiExecute;

/**
 * 健康情報照会API
 *
 * @version 1.0.0
 */
@Component
public class HealthInfoReferenceApi
        extends BaseApi<HealthInfoReferenceRequest, HealthInfoReferenceResponse> {

    /** 健康情報照会サービス */
    @Autowired
    private HealthInfoReferenceService service;

    /**
     * 照会
     *
     * @param request
     *     リクエスト
     * @param response
     *     レスポンス
     * @throws BaseException
     *     基底例外
     */
    @ApiExecute
    public void reference(HealthInfoReferenceRequest request,
            HealthInfoReferenceResponse response) throws BaseException {

        service.checkRequest(request);

        service.execute(request, response);
    }

}
