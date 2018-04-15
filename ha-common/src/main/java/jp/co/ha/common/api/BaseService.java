package jp.co.ha.common.api;

import jp.co.ha.common.exception.BaseAppException;

/**
 * API基底サービス<br>
 * 各機能のサービスの親クラスとする<br>
 * @param <Rq> リクエスト種別
 * @param <Rs> レスポンス種別
 * @param <E> 例外種別
 */
public interface BaseService<Rq extends BaseRequest, Rs extends BaseResponse, E extends BaseAppException> {

	/**
	 * 継承先でそれぞれチェックを実装<br>
	 * @param Rq Request実装クラス
	 * @throws E 例外実装クラス
	 */
	void checkRequest(Rq request) throws E;

	/**
	 * メイン処理<br>
	 * リクエスト型を処理し、レスポンス型で返す<br>
	 * @param Rq Request実装クラス
	 * @return Rs Response実装クラス
	 * @throws E
	 */
	Rs execute(Rq request) throws E;

}
