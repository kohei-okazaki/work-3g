package jp.co.ha.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.api.request.HealthInfoReferenceRequest;
import jp.co.ha.api.response.HealthInfoReferenceResponse;
import jp.co.ha.api.service.HealthInfoReferenceService;
import jp.co.ha.common.api.BaseRestController;
import jp.co.ha.common.exception.HealthInfoException;
import jp.co.ha.common.log.AppLogger;

/**
 * 健康情報照会コントローラクラス<br>
 *
 */
@RestController
@RequestMapping(value = "/healthInfoReference", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
public class HealthInfoReferenceController implements
		BaseRestController<HealthInfoReferenceRequest, HealthInfoReferenceResponse, HealthInfoReferenceService, HealthInfoException> {

	/** 健康情報照会サービス */
	@Autowired
	private HealthInfoReferenceService service;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoReferenceResponse execute(HealthInfoReferenceRequest apiRequest) throws HealthInfoException {

		// リクエスト情報のチェック
		service.checkRequest(apiRequest);

		HealthInfoReferenceResponse apiResponse = service.execute(apiRequest);

		AppLogger logger = new AppLogger(this.getClass());
		logger.info(apiResponse);
		return apiResponse;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoReferenceRequest toRequest(HttpServletRequest request) throws HealthInfoException {

		HealthInfoReferenceRequest apiRequest = new HealthInfoReferenceRequest();
		apiRequest.setRequestId(request.getParameter("requestId"));
		apiRequest.setUserId(request.getParameter("userId"));
		apiRequest.setHealthInfoId(request.getParameter("healthInfoId"));

		return apiRequest;
	}

}
