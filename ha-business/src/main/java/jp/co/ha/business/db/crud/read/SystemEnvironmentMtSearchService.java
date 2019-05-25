package jp.co.ha.business.db.crud.read;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.SystemEnvironmentMt;

/**
 * システム環境マスタ検索サービスインターフェース
 *
 */
public interface SystemEnvironmentMtSearchService {

	/**
	 * システム環境マスタを取得する
	 *
	 * @return
	 * @throws BaseException
	 *     基底例外
	 */
	SystemEnvironmentMt find() throws BaseException;
}
