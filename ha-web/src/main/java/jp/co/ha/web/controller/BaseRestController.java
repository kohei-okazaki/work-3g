package jp.co.ha.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.function.ThrowableBiConsumer;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.log.type.LogLevel;
import jp.co.ha.common.validator.BeanValidator;
import jp.co.ha.common.validator.ValidateError;
import jp.co.ha.common.validator.ValidateErrorResult;
import jp.co.ha.web.form.BaseApiRequest;
import jp.co.ha.web.form.BaseApiResponse;
import jp.co.ha.web.form.ErrorResponse;

/**
 * Rest形式の基底コントローラ<br>
 * すべてのRestコントローラはこのクラスを継承すること
 *
 * @param <Rq>
 *     リクエスト
 * @param <Rs>
 *     レスポンス
 */
@RestController
public abstract class BaseRestController<Rq extends BaseApiRequest, Rs extends BaseApiResponse>
		implements ThrowableBiConsumer<Rq, Rs> {

	/** LOG */
	protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
	/** 妥当性チェック */
	@Autowired
	private BeanValidator<Rq> validator;

	/**
	 * POST通信の処理を行う
	 *
	 * @param request
	 *     Rq
	 * @return response
	 * @throws BaseException
	 *     基底例外
	 */
	@PostMapping(headers = { "Content-type=application/json;charset=UTF-8" }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public Rs doPost(@RequestBody Rq request) throws BaseException {

		ValidateErrorResult result = validator.validate(request);
		if (result.hasError()) {
			ValidateError error = result.getFirst();
			// 妥当性チェックエラー
			throw new ApiException(CommonErrorCode.VALIDATE_ERROR,
					error.getMessage() + " " + error.getName() + "=" + error.getValue());
		}

		Rs response = getApiResponse();

		this.accept(request, response);

		return response;
	}

	/**
	 * APIレスポンスを返す
	 *
	 * @return APIレスポンス
	 */
	protected abstract Rs getApiResponse();

	/**
	 * JSONで例外が起きた場合のエラーハンドリング
	 *
	 * @param e
	 *     JSON系のエラー
	 * @return エラーレスポンス
	 */
	@SuppressWarnings("unchecked")
	@ExceptionHandler(JsonProcessingException.class)
	public Rs jsonExceptionHandle(JsonProcessingException e) {

		BaseException baseException = null;
		if (e instanceof InvalidFormatException) {
			InvalidFormatException ife = (InvalidFormatException) e;
			baseException = new ApiException(CommonErrorCode.JSON_FORMAT_ERROR, ife.getValue() + "はリクエスト形式エラーです", e);
		} else if (e instanceof JsonParseException) {
			baseException = new ApiException(CommonErrorCode.JSON_PARSE_ERROR,
					e.getLocation().getColumnNr() + "行目がjson形式ではありません", e);
		} else if (e instanceof JsonProcessingException) {
			baseException = new ApiException(CommonErrorCode.JSON_PARSE_ERROR, "JSON生成処理が失敗しました", e);
		}

		Rs response = (Rs) new ErrorResponse(baseException);
		LOG.warnRes(response, baseException);

		return response;
	}

	/**
	 * アプリケーション例外のエラーハンドリング
	 *
	 * @param e
	 *     アプリエラー
	 * @return エラーレスポンス
	 */
	@SuppressWarnings("unchecked")
	@ExceptionHandler(BaseException.class)
	public Rs appExceptionHandle(BaseException e) {
		Rs response = (Rs) new ErrorResponse(e);
		if (LogLevel.WARN.is(e.getErrorCode().getLogLevel())) {
			LOG.warnRes(response, e);
		} else if (LogLevel.ERROR.is(e.getErrorCode().getLogLevel())) {
			LOG.errorRes(response, e);
		}
		return response;
	}

}
