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
public interface BaseApiRestController<Rq extends BaseRequest
									, Rs extends BaseResponse
									, S extends BaseApiService<Rq, Rs, E>
									, E extends BaseAppException> {

	/**
	 * getでの通信の処理を行う<br>
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping
	default Rs doGet(HttpServletRequest request, HttpServletResponse response) {

		Rs resp = null;

		try {

			resp = this.execute(request, response);
			resp.setResult(0);

		} catch (BaseAppException e) {

			resp = (Rs) new ErrorResponse(e);
			System.out.println(e.toString());

		}

		return resp;
	}

	/**
	 * postでの通信の処理を行う<br>
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping
	default Rs doPost(HttpServletRequest request, HttpServletResponse response) {
		return doGet(request, response);
	}

	/**
	 * 継承先のコントローラクラスで処理する<br>
	 * @param request
	 * @param response
	 * @return
	 * @throws E
	 */
	Rs execute(HttpServletRequest request, HttpServletResponse response) throws E;

}
