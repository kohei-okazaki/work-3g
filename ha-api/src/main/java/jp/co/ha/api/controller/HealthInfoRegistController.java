package jp.co.ha.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.api.service.HealthInfoRegistService;
import jp.co.ha.business.exception.HealthInfoException;
import jp.co.ha.common.api.controller.BaseRestController;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.validator.BeanValidator;
import jp.co.ha.common.validator.ValidateError;
import jp.co.ha.common.validator.ValidateErrorResult;

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
	/** 妥当性チェック */
	@Autowired
	private BeanValidator<HealthInfoRegistRequest> validator;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoRegistResponse execute(HealthInfoRegistRequest request) throws BaseException {

		ValidateErrorResult result = validator.validate(request);

		if (result.hasError()) {
			ValidateError error = result.getFirst();
			// 妥当性チェックエラー
			throw new HealthInfoException(CommonErrorCode.VALIDATE_ERROR,
					error.getMessage() + " " + error.getName() + "=" + error.getValue());
		}
		// リクエスト情報のチェック
		service.checkRequest(request);

		HealthInfoRegistResponse response = service.execute(request);

		return response;
	}

}
