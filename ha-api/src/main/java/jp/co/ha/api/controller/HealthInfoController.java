package jp.co.ha.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.api.exception.HealthInfoException;
import jp.co.ha.api.request.HealthInfoRequest;
import jp.co.ha.api.response.HealthInfoResponse;
import jp.co.ha.api.service.HealthInfoService;
import jp.co.ha.common.api.BaseApiRestController;

/**
 * 健康情報コントローラクラス<br>
 *
 */
@RestController
@RequestMapping(value = "/healthInfo", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
public class HealthInfoController implements BaseApiRestController<HealthInfoRequest
																, HealthInfoResponse
																, HealthInfoService
																, HealthInfoException> {

	@Autowired
	private HealthInfoRequest apiRequest;
	@Autowired
	private HealthInfoResponse apiResponse;
	@Autowired
	private HealthInfoService service;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoResponse execute(HttpServletRequest request, HttpServletResponse response) throws HealthInfoException {

		apiResponse = null;
		return apiResponse;
	}

}
