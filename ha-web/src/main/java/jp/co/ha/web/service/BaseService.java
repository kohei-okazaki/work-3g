package jp.co.ha.web.service;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.web.request.BaseApiRequest;
import jp.co.ha.web.response.BaseApiResponse;

/**
 * API基底サービス<br>
 * 各機能のサービスの親クラスとする
 *
 * @param <Rq>
 *     リクエスト
 * @param <Rs>
 *     レスポンス
 */
public interface BaseService<Rq extends BaseApiRequest, Rs extends BaseApiResponse> {

	/**
	 * 継承先でそれぞれチェックを実装
	 *
	 * @param request
	 *     Request実装クラス
	 * @throws BaseException
	 *     基底例外
	 */
	void checkRequest(Rq request) throws BaseException;

	/**
	 * メイン処理<br>
	 * リクエスト型を処理し、レスポンス型で返す
	 *
	 * @param request
	 *     Request実装クラス
	 * @return Rs Response実装クラス
	 * @throws BaseException
	 *     基底例外
	 */
	Rs execute(Rq request) throws BaseException;

}
