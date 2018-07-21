package jp.co.ha.common.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.exception.RestApiException;
import jp.co.ha.common.log.AppLogger;
import jp.co.ha.common.log.AppLoggerFactory;

/**
 * RestAPI基底コントローラ<br>
 * すべてのRestAPIコントローラはこのクラスを継承すること<br>
 *
 * @param <Rq>
 *     リクエストクラス
 * @param <Rs>
 *     レスポンスクラス
 * @param <S>
 *     サービスクラス
 */
public interface BaseRestController<Rq extends BaseRequest, Rs extends BaseResponse, S extends BaseService<Rq, Rs>> {

	/**
	 * GET通信の処理を行う<br>
	 *
	 * @param request
	 *     HttpServletRequest
	 * @param response
	 *     HttpServletResponse
	 * @return
	 * @throws BaseAppException
	 *     アプリ例外
	 */
	@GetMapping
	default Rs doGet(HttpServletRequest request, HttpServletResponse response) throws BaseAppException {

		AppLogger log = AppLoggerFactory.getLogger(this.getClass());
		Rq apiRequest = toRequest(request);
		log.info(apiRequest);
		Rs apiResponse = this.execute(apiRequest);
		apiResponse.setResult(ResultType.SUCCESS);
		log.info(apiResponse);

		return apiResponse;
	}

	/**
	 * POST通信の処理を行う<br>
	 *
	 * @param apiRequest
	 *     Rq
	 * @return
	 * @throws BaseAppException
	 *     アプリ例外
	 */
	@PostMapping
	default Rs doPost(@RequestBody Rq apiRequest) throws BaseAppException {

		AppLogger log = AppLoggerFactory.getLogger(this.getClass());
		Rs apiResponse = null;
		log.info(apiRequest);
		apiResponse = this.execute(apiRequest);
		apiResponse.setResult(ResultType.SUCCESS);
		log.info(apiResponse);

		return apiResponse;
	}

	/**
	 * 継承先のコントローラクラスで処理する<br>
	 *
	 * @param request
	 *     リクエストクラス
	 * @return response レスポンスクラス
	 * @throws BaseAppException
	 *     例外クラス
	 */
	Rs execute(Rq request) throws BaseAppException;

	/**
	 * Requestクラスに変換する<br>
	 *
	 * @param request
	 *     HttpServletRequest
	 * @return apiRequest Rq
	 * @throws BaseAppException
	 *     例外クラス
	 */
	Rq toRequest(HttpServletRequest request) throws BaseAppException;

	/**
	 * JSONで例外が起きた場合のエラーハンドリング<br>
	 *
	 * @param e
	 *     JSON系のエラー
	 * @return
	 */
	@ExceptionHandler(JsonProcessingException.class)
	public default Rs jsonProcessingExceptionHandle(JsonProcessingException e) {

		Rs apiResponse = null;
		if (e instanceof InvalidFormatException) {
			InvalidFormatException jfe = (InvalidFormatException) e;
			apiResponse = (Rs) new ErrorResponse(new RestApiException(ErrorCode.JSON_FORMAT_ERROR, jfe.getValue() + "はリクエスト形式エラーです"));
		} else if (e instanceof JsonParseException) {
			e = (JsonParseException) e;
			apiResponse = (Rs) new ErrorResponse(new RestApiException(ErrorCode.JSON_PARSE_ERROR, e.getLocation().getColumnNr() + "行目がjson形式ではありません"));
		}
		AppLogger log = AppLoggerFactory.getLogger(this.getClass());
		log.error(apiResponse);
		return apiResponse;
	}

	/**
	 * アプリケーション例外のエラーハンドリング<br>
	 *
	 * @param e
	 *     アプリエラー
	 * @return
	 */
	@ExceptionHandler(BaseAppException.class)
	public default Rs appExceptionHandle(BaseAppException e) {

		Rs apiResponse = (Rs) new ErrorResponse(e);

		AppLogger log = AppLoggerFactory.getLogger(this.getClass());
		log.error(apiResponse);
		return apiResponse;
	}

}
