package jp.co.ha.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.api.exception.HealthInfoException;
import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.api.service.HealthInfoService;
import jp.co.ha.common.api.BaseRestController;

/**
 * 健康情報コントローラクラス<br>
 *
 */
@RestController
@RequestMapping(value = "/healthInfoRegist", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
public class HealthInfoRegistController implements BaseRestController<HealthInfoRegistRequest
																, HealthInfoRegistResponse
																, HealthInfoService
																, HealthInfoException> {

	@Autowired
	private HealthInfoService service;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoRegistResponse execute(HealthInfoRegistRequest apiRequest) throws HealthInfoException {

		HealthInfoRegistResponse apiResponse = null;
		return apiResponse;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoRegistRequest toRequest(HttpServletRequest request) throws HealthInfoException {
		HealthInfoRegistRequest apiRequest = null;
		return apiRequest;
	}

}
