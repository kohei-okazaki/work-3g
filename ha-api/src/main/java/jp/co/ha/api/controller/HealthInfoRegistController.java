package jp.co.ha.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.api.service.HealthInfoRegistService;
import jp.co.ha.common.api.controller.BaseRestController;
import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報登録コントローラ
 *
 */
@RestController
@RequestMapping(value = "/healthInfoRegist",
	headers = { "Content-type=application/json;charset=UTF-8" },
	produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
public class HealthInfoRegistController extends
		BaseRestController<HealthInfoRegistRequest, HealthInfoRegistResponse> {

	/** 健康情報登録サービス */
	@Autowired
	private HealthInfoRegistService service;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoRegistResponse execute(HealthInfoRegistRequest request) throws BaseException {

		// リクエスト情報のチェック
		service.checkRequest(request);

		HealthInfoRegistResponse response = service.execute(request);

		return response;
	}

}
