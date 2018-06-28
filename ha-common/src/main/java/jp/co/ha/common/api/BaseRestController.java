package jp.co.ha.common.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.log.AppLoggerFactory;

/**
 * RestAPI基底コントローラ<br>
 * すべてのRestAPIコントローラはこのクラスを継承すること<br>
 *
 * @param <Rq>
 *            リクエストクラス
 * @param <Rs>
 *            レスポンスクラス
 * @param <S>
 *            サービスクラス
 * @param <E>
 *            例外クラス
 */
public interface BaseRestController<Rq extends BaseRequest, Rs extends BaseResponse, S extends BaseService<Rq, Rs, E>, E extends BaseAppException> {

	/**
	 * GET通信の処理を行う<br>
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return
	 */
	@GetMapping
	default Rs doGet(HttpServletRequest request, HttpServletResponse response) {

		Rs apiResponse = null;
		try {
			Rq apiRequest = toRequest(request);
			AppLoggerFactory.getLogger(apiRequest.getClass()).info(apiRequest);
			apiResponse = this.execute(apiRequest);
			apiResponse.setResult(ResultType.SUCCESS);
			AppLoggerFactory.getLogger(apiResponse.getClass()).info(apiResponse);
		} catch (BaseAppException e) {
			apiResponse = (Rs) new ErrorResponse(e);
			AppLoggerFactory.getLogger(apiResponse.getClass()).error(apiResponse);
		}


		return apiResponse;
	}

	/**
	 * POST通信の処理を行う<br>
	 *
	 * @param apiRequest
	 *            Rq
	 * @return
	 */
	@PostMapping
	default Rs doPost(@RequestBody Rq apiRequest) {

		Rs apiResponse = null;
		try {
			apiResponse = this.execute(apiRequest);
			apiResponse.setResult(ResultType.SUCCESS);
		} catch (BaseAppException e) {
			apiResponse = (Rs) new ErrorResponse(e);
			System.out.println(e.toString());
		}
		return apiResponse;
	}

	/**
	 * 継承先のコントローラクラスで処理する<br>
	 *
	 * @param request
	 *            リクエストクラス
	 * @return response レスポンスクラス
	 * @throws E
	 *             例外クラス
	 */
	Rs execute(Rq request) throws E;

	/**
	 * Requestクラスに変換する<br>
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return apiRequest Rq
	 * @throws E
	 *             例外クラス
	 */
	Rq toRequest(HttpServletRequest request) throws E;

}
