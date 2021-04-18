package jp.co.ha.business.api.healthinfo.service;

import jp.co.ha.business.api.healthinfo.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoRegistResponse;
import jp.co.ha.common.web.service.BaseRestApiService;

/**
 * 健康情報登録サービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoRegistService extends
        BaseRestApiService<HealthInfoRegistRequest, HealthInfoRegistResponse> {

}
