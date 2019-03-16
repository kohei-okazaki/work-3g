package jp.co.ha.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.request.HealthInfoReferenceRequest;
import jp.co.ha.business.api.response.HealthInfoReferenceResponse;
import jp.co.ha.business.api.service.HealthInfoReferenceService;
import jp.co.ha.business.exception.HealthInfoException;
import jp.co.ha.common.api.controller.BaseRestController;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.validator.BeanValidator;
import jp.co.ha.common.validator.ValidateError;
import jp.co.ha.common.validator.ValidateErrorHolder;

/**
 * 健康情報照会コントローラ
 *
 */
@RestController
@RequestMapping(value = "/healthInfoReference",
	headers = { "Content-type=application/json;charset=UTF-8" },
	produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
public class HealthInfoReferenceController extends
		BaseRestController<HealthInfoReferenceRequest, HealthInfoReferenceResponse> {

	/** 健康情報照会サービス */
	@Autowired
	private HealthInfoReferenceService service;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoReferenceResponse execute(HealthInfoReferenceRequest request) throws BaseException {

		BeanValidator<HealthInfoReferenceRequest> validator = new BeanValidator<>();
		ValidateErrorHolder holder = validator.validate(request);

		if (holder.hasError()) {
			ValidateError error = holder.getFirst();
			// 妥当性チェックエラー
			throw new HealthInfoException(CommonErrorCode.VALIDATE_ERROR,
					error.getMessage() + " " + error.getName() + "=" + error.getValue());
		}

		// リクエスト情報のチェック
		service.checkRequest(request);

		HealthInfoReferenceResponse response = service.execute(request);

		return response;
	}

}
