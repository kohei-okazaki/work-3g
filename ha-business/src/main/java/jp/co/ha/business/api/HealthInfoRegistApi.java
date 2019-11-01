package jp.co.ha.business.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.api.service.HealthInfoRegistService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.web.api.BaseApi;
import jp.co.ha.web.api.annotation.ApiExecute;

/**
 * 健康情報登録API
 * 
 * @since 1.0
 */
@Component
public class HealthInfoRegistApi implements BaseApi<HealthInfoRegistRequest, HealthInfoRegistResponse> {

	/** 健康情報登録サービス */
	@Autowired
	private HealthInfoRegistService service;

	/**
	 * 登録
	 *
	 * @param request
	 *     リクエスト
	 * @param response
	 *     レスポンス
	 * @throws BaseException
	 *     基底例外
	 */
	@ApiExecute
	public void regist(HealthInfoRegistRequest request, HealthInfoRegistResponse response) throws BaseException {

		service.checkRequest(request);

		service.execute(request, response);
	}

}
