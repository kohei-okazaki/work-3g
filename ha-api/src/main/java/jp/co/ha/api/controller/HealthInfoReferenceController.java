package jp.co.ha.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.request.HealthInfoReferenceRequest;
import jp.co.ha.business.api.response.HealthInfoReferenceResponse;
import jp.co.ha.business.api.service.HealthInfoReferenceService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.web.controller.BaseRestController;

/**
 * 健康情報照会コントローラ
 *
 */
@RestController
@RequestMapping(value = "/healthInfoReference")
public class HealthInfoReferenceController extends
		BaseRestController<HealthInfoReferenceRequest, HealthInfoReferenceResponse> {

	/** 健康情報照会サービス */
	@Autowired
	private HealthInfoReferenceService service;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected HealthInfoReferenceResponse getApiResponse() {
		return new HealthInfoReferenceResponse();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(HealthInfoReferenceRequest request, HealthInfoReferenceResponse response) throws BaseException {

		// リクエスト情報のチェック
		service.checkRequest(request);

		service.execute(request, response);

	}

}
