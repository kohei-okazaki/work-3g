package jp.co.ha.business.api.healthinfoapp.service;

import jp.co.ha.business.api.healthinfoapp.request.BaseAppApiRequest;
import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse;
import jp.co.ha.common.exception.BaseException;

/**
 * Rest APIの基底サービスインターフェース
 *
 * @param <Rq>
 *     リクエスト
 * @param <Rs>
 *     レスポンス
 * @version 1.0.0
 */
public interface BaseAppApiService<Rq extends BaseAppApiRequest, Rs extends BaseAppApiResponse> {

    /**
     * 継承先でそれぞれチェックを実装
     *
     * @param request
     *     APIリクエスト
     * @throws BaseException
     *     基底例外
     */
    void checkRequest(Rq request) throws BaseException;

    /**
     * メイン処理<br>
     * リクエスト処理を行い、レスポンスにbindする
     *
     * @param request
     *     APIリクエスト
     * @param response
     *     APIレスポンス
     * @throws BaseException
     *     基底例外
     */
    void execute(Rq request, Rs response) throws BaseException;
}
