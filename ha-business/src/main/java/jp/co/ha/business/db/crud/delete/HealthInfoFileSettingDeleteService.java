package jp.co.ha.business.db.crud.delete;

import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報ファイル設定削除サービスインターフェース
 *
 */
public interface HealthInfoFileSettingDeleteService {

	/**
	 * 指定したユーザIDの健康情報ファイル設定を削除する
	 *
	 * @param userId
	 *     ユーザID
	 * @throws BaseException
	 *     基底例外
	 */
	void deleteByUserId(String userId) throws BaseException;
}
