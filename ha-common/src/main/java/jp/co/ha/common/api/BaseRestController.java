package jp.co.ha.common.api;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * RestAPI基底コントローラ<br>
 * すべてのRestAPIコントローラはこのクラスを継承すること<br>
 *
 * @param <Rq>
 *     リクエスト
 * @param <Rs>
 *     レスポンス
 * @param <S>
 *     サービスクラス
 */
public interface BaseRestController<Rq extends BaseRequest, Rs extends BaseResponse, S extends BaseService<Rq, Rs>> {

	/**
	 * POST通信の処理を行う<br>
	 *
	 * @param apiRequest
	 *     Rq
	 * @return
	 * @throws BaseException
	 *     アプリ例外
	 */
	@PostMapping
	default Rs doPost(@RequestBody Rq apiRequest) throws BaseException {

		Logger log = LoggerFactory.getLogger(this.getClass());
		log.infoRes(apiRequest);
		Rs apiResponse = this.execute(apiRequest);
		apiResponse.setResult(ResultType.SUCCESS);
		log.infoRes(apiResponse);

		return apiResponse;
	}

	/**
	 * 継承先のコントローラクラスで処理する<br>
	 *
	 * @param request
	 *     リクエストクラス
	 * @return response レスポンスクラス
	 * @throws BaseException
	 *     例外クラス
	 */
	Rs execute(Rq request) throws BaseException;

	/**
	 * JSONで例外が起きた場合のエラーハンドリング<br>
	 *
	 * @param e
	 *     JSON系のエラー
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ExceptionHandler(JsonProcessingException.class)
	public default Rs jsonProcessingExceptionHandle(JsonProcessingException e) {

		Rs apiResponse = null;
		if (e instanceof InvalidFormatException) {
			InvalidFormatException jfe = (InvalidFormatException) e;
			apiResponse = (Rs) new ErrorResponse(new ApiException(ErrorCode.JSON_FORMAT_ERROR, jfe.getValue() + "はリクエスト形式エラーです"));
		} else if (e instanceof JsonParseException) {
			apiResponse = (Rs) new ErrorResponse(new ApiException(ErrorCode.JSON_PARSE_ERROR, e.getLocation().getColumnNr() + "行目がjson形式ではありません"));
		} else if (e instanceof JsonProcessingException) {
			apiResponse = (Rs) new ErrorResponse(new ApiException(ErrorCode.JSON_PARSE_ERROR, e.getLocation().getColumnNr() + ":json形式ではありません"));
		}
		Logger log = LoggerFactory.getLogger(this.getClass());
		log.errorRes(apiResponse);
		return apiResponse;
	}

	/**
	 * アプリケーション例外のエラーハンドリング<br>
	 *
	 * @param e
	 *     アプリエラー
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ExceptionHandler(BaseException.class)
	public default Rs appExceptionHandle(BaseException e) {
		Rs apiResponse = (Rs) new ErrorResponse(e);
		return apiResponse;
	}

}
