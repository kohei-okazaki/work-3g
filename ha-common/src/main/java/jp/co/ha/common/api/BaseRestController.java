package jp.co.ha.common.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.ha.common.exception.BaseAppException;

/**
 * RestAPI基底コントローラ<br>
 * すべてのRestAPIコントローラはこのクラスを継承すること<br>
 *
 * @param <Rq> リクエストクラス
 * @param <Rs> レスポンスクラス
 * @param <S> サービスクラス
 * @param <E> 例外クラス
 */
public interface BaseRestController<Rq extends BaseRequest, Rs extends BaseResponse, S extends BaseService<Rq, Rs, E>, E extends BaseAppException> {

	/**
	 * getでの通信の処理を行う<br>
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping
	default Rs doGet(HttpServletRequest request, HttpServletResponse response) {

		Rs apiResponse = null;

		try {
			Rq apiRequest = toRequest(request);
			apiResponse = this.execute(apiRequest);
			apiResponse.setResult(ResultType.SUCCESS);
		} catch (BaseAppException e) {
			apiResponse = (Rs) new ErrorResponse(e);
			System.out.println(e.toString());
		}

		return apiResponse;
	}

	/**
	 * postでの通信の処理を行う<br>
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return
	 */
	@PostMapping
	default Rs doPost(HttpServletRequest request, HttpServletResponse response) {
		return doGet(request, response);
	}

	/**
	 * 継承先のコントローラクラスで処理する<br>
	 * @param request リクエストクラス
	 * @return response レスポンスクラス
	 * @throws E
	 */
	Rs execute(Rq request) throws E;

	/**
	 * Requestクラスに変換する<br>
	 * @param request HttpServletRequest
	 * @return apiRequest Rq
	 * @throws E
	 */
	Rq toRequest(HttpServletRequest request) throws E;

}
