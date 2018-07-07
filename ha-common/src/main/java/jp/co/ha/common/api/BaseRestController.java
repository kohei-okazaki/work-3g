package jp.co.ha.common.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jp.co.ha.common.exception.BaseAppException;
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
	 */
	@GetMapping
	default Rs doGet(HttpServletRequest request, HttpServletResponse response) {

		AppLogger log = AppLoggerFactory.getLogger(this.getClass());
		Rs apiResponse = null;
		try {
			Rq apiRequest = toRequest(request);
			log.info(apiRequest);
			apiResponse = this.execute(apiRequest);
			apiResponse.setResult(ResultType.SUCCESS);
			log.info(apiResponse);
		} catch (BaseAppException e) {
			apiResponse = (Rs) new ErrorResponse(e);
			log.error(apiResponse);
		}

		return apiResponse;
	}

	/**
	 * POST通信の処理を行う<br>
	 *
	 * @param apiRequest
	 *     Rq
	 * @return
	 */
	@PostMapping
	default Rs doPost(@RequestBody Rq apiRequest) {

		AppLogger log = AppLoggerFactory.getLogger(this.getClass());
		Rs apiResponse = null;
		try {
			log.info(apiRequest);
			apiResponse = this.execute(apiRequest);
			apiResponse.setResult(ResultType.SUCCESS);
			log.info(apiResponse);
		} catch (BaseAppException e) {
			apiResponse = (Rs) new ErrorResponse(e);
			log.error(apiResponse);
		}

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

}
