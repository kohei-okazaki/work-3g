package jp.co.ha.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.api.request.HealthInfoReferenceRequest;
import jp.co.ha.api.response.HealthInfoReferenceResponse;
import jp.co.ha.api.service.HealthInfoReferenceService;
import jp.co.ha.common.api.controller.BaseRestController;
import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報照会コントローラ<br>
 *
 */
@RestController
@RequestMapping(value = "/healthInfoReference", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
public class HealthInfoReferenceController extends
		BaseRestController<HealthInfoReferenceRequest, HealthInfoReferenceResponse, HealthInfoReferenceService> {

	/** 健康情報照会サービス */
	@Autowired
	private HealthInfoReferenceService service;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoReferenceResponse execute(HealthInfoReferenceRequest request) throws BaseException {

		// リクエスト情報のチェック
		service.checkRequest(request);

		HealthInfoReferenceResponse response = service.execute(request);

		return response;
	}

}
