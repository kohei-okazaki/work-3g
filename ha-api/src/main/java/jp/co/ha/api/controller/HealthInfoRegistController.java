package jp.co.ha.api.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.api.service.HealthInfoRegistService;
import jp.co.ha.business.exception.HealthInfoException;
import jp.co.ha.common.api.BaseRestController;
import jp.co.ha.common.api.RequestType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;

/**
 * 健康情報登録コントローラクラス<br>
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoRegistRequest toRequest(HttpServletRequest request) throws HealthInfoException {

		HealthInfoRegistRequest apiRequest = new HealthInfoRegistRequest();
		apiRequest.setRequestType(RequestType.of(request.getParameter("requestId")));
		apiRequest.setUserId(request.getParameter("userId"));
		apiRequest.setHeight(BeanUtil.isNull(request.getParameter("height")) ? null : new BigDecimal(request.getParameter("height")));
		apiRequest.setWeight(BeanUtil.isNull(request.getParameter("weight")) ? null : new BigDecimal(request.getParameter("weight")));

		return apiRequest;
	}

}
