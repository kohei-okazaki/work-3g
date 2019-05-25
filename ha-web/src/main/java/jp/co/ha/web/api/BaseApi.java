package jp.co.ha.web.api;

import java.lang.reflect.Method;

import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.web.api.annotation.Execute;
import jp.co.ha.web.form.BaseApiRequest;
import jp.co.ha.web.form.BaseApiResponse;

/**
 * 基底API
 *
 * @param <Rq>
 *     リクエスト
 * @param <Rs>
 *     レスポンス
 */
public interface BaseApi<Rq extends BaseApiRequest, Rs extends BaseApiResponse> {

	/**
	 * APIを実行する
	 *
	 * @param request
	 *     リクエスト
	 * @param response
	 *     レスポンス
	 * @throws BaseException
	 *     基底例外
	 */
	public default void execute(Rq request, Rs response) throws BaseException {
		try {
			for (Method m : this.getClass().getDeclaredMethods()) {
				if (m.isAnnotationPresent(Execute.class)) {
					m.invoke(this, request, response);
				}
			}
		} catch (Exception e) {
			throw new ApiException(CommonErrorCode.UNEXPECTED_ERROR, "APIの実行に失敗しました", e);
		}
	}
}
