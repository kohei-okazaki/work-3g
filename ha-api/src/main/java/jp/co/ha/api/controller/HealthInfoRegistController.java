package jp.co.ha.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.api.service.HealthInfoRegistService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.web.controller.BaseRestController;

/**
 * 健康情報登録コントローラ
 *
 */
@RestController
@RequestMapping(value = "/healthInfoRegist")
public class HealthInfoRegistController extends
		BaseRestController<HealthInfoRegistRequest, HealthInfoRegistResponse> {

	/** 健康情報登録サービス */
	@Autowired
	private HealthInfoRegistService service;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoRegistResponse getApiResponse() {
		return new HealthInfoRegistResponse();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(HealthInfoRegistRequest request, HealthInfoRegistResponse response) throws BaseException {

		// リクエスト情報のチェック
		service.checkRequest(request);

		service.execute(request, response);

	}

}
