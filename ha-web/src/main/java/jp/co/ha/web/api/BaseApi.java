package jp.co.ha.web.api;

import java.lang.reflect.Method;

import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.web.api.annotation.ApiExecute;
import jp.co.ha.web.form.BaseRestApiRequest;
import jp.co.ha.web.form.BaseRestApiResponse;

/**
 * 基底API
 *
 * @param <Rq>
 *     リクエスト
 * @param <Rs>
 *     レスポンス
 * @version 1.0.0
 */
public interface BaseApi<Rq extends BaseRestApiRequest, Rs extends BaseRestApiResponse> {

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
                if (m.isAnnotationPresent(ApiExecute.class)) {
                    m.invoke(this, request, response);
                }
            }
        } catch (Exception e) {
            if (e.getCause() instanceof BaseException) {
                throw (BaseException) e.getCause();
            } else {
                throw new ApiException(CommonErrorCode.UNEXPECTED_ERROR, "APIの実行に失敗しました",
                        e);
            }
        }
    }
}
