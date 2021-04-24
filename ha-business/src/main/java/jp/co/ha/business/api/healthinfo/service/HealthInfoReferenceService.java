package jp.co.ha.business.api.healthinfo.service;

import jp.co.ha.business.api.healthinfo.request.HealthInfoReferenceApiRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoReferenceApiResponse;
import jp.co.ha.common.web.service.BaseRestApiService;

/**
 * 健康情報照会サービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoReferenceService extends
        BaseRestApiService<HealthInfoReferenceApiRequest, HealthInfoReferenceApiResponse> {

}
