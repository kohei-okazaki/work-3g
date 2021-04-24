package jp.co.ha.business.api.healthinfo.service;

import jp.co.ha.business.api.healthinfo.request.HealthInfoRegistApiRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoRegistApiResponse;
import jp.co.ha.common.web.service.BaseRestApiService;

/**
 * 健康情報登録サービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoRegistService extends
        BaseRestApiService<HealthInfoRegistApiRequest, HealthInfoRegistApiResponse> {

}
