package jp.co.ha.common.web.service;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.web.form.BaseRestApiRequest;
import jp.co.ha.common.web.form.BaseRestApiResponse;

/**
 * Rest APIの基底サービスインターフェース
 *
 * @param <Rq>
 *     リクエスト
 * @param <Rs>
 *     レスポンス
 * @version 1.0.0
 */
public interface BaseRestApiService<Rq extends BaseRestApiRequest, Rs extends BaseRestApiResponse> {

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
