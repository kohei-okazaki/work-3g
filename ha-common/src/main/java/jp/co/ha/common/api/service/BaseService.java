package jp.co.ha.common.api.service;

import jp.co.ha.common.api.request.BaseRequest;
import jp.co.ha.common.api.response.BaseResponse;
import jp.co.ha.common.exception.BaseException;

/**
 * API基底サービス<br>
 * 各機能のサービスの親クラスとする<br>
 *
 * @param <Rq>
 *     リクエスト
 * @param <Rs>
 *     レスポンス
 */
public interface BaseService<Rq extends BaseRequest, Rs extends BaseResponse> {

	/**
	 * 継承先でそれぞれチェックを実装<br>
	 *
	 * @param Rq
	 *     Request実装クラス
	 * @throws BaseException
	 *     例外クラス
	 */
	void checkRequest(Rq request) throws BaseException;

	/**
	 * メイン処理<br>
	 * リクエスト型を処理し、レスポンス型で返す<br>
	 *
	 * @param Rq
	 *     Request実装クラス
	 * @return Rs Response実装クラス
	 * @throws BaseException
	 *     例外クラス
	 */
	Rs execute(Rq request) throws BaseException;

}
