package jp.co.ha.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.api.service.HealthInfoRegistService;
import jp.co.ha.common.api.controller.BaseRestController;
import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報登録コントローラ<br>
 *
 */
@RestController
@RequestMapping(value = "/healthInfoRegist", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
public class HealthInfoRegistController implements
		BaseRestController<HealthInfoRegistRequest, HealthInfoRegistResponse, HealthInfoRegistService> {

	/** 健康情報登録サービス */
	@Autowired
	private HealthInfoRegistService service;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoRegistResponse execute(HealthInfoRegistRequest apiRequest) throws BaseException {

		// リクエスト情報のチェック
		service.checkRequest(apiRequest);

		HealthInfoRegistResponse apiResponse = service.execute(apiRequest);

		return apiResponse;
	}

}
