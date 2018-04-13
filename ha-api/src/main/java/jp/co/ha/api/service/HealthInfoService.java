package jp.co.ha.api.service;

import jp.co.ha.api.exception.HealthInfoException;
import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.common.api.BaseService;

/**
 * 健康情報登録サービス<br>
 *
 */
public interface HealthInfoService extends BaseService<HealthInfoRegistRequest, HealthInfoRegistResponse, HealthInfoException> {

}
