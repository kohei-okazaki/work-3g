package jp.co.ha.business.db.crud.read;

import java.util.Date;
import java.util.List;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報検索サービスインターフェース
 *
 */
public interface HealthInfoSearchService {

	/**
	 * 指定されたユーザIDと一致する健康情報のリストを返す
	 *
	 * @param userId
	 *     ユーザID
	 * @return 健康情報のリスト
	 * @throws BaseException
	 *     基底例外
	 */
	List<HealthInfo> findByUserId(String userId) throws BaseException;

	/**
	 * 指定された健康情報IDと一致する健康情報を返す
	 *
	 * @param healthInfoId
	 *     健康情報ID
	 * @return 健康情報Entity
	 * @throws BaseException
	 *     基底例外
	 */
	HealthInfo findByHealthInfoId(Integer healthInfoId) throws BaseException;

	/**
	 * 指定したユーザIDで最後に登録した健康情報を返す
	 *
	 * @param userId
	 *     ユーザID
	 * @return 健康情報Entity
	 * @throws BaseException
	 *     基底例外
	 */
	HealthInfo findLastByUserId(String userId) throws BaseException;

	/**
	 * 指定されたユーザIDと指定された健康情報作成日時の期間内の健康情報のリストを返す
	 *
	 * @param userId
	 *     ユーザID
	 * @param fromHealthInfoRegDate
	 *     YYYYMMDD
	 * @param toHealthInfoRegDate
	 *     YYYYMMDD
	 * @return 健康情報のリスト
	 * @throws BaseException
	 *     基底例外
	 */
	List<HealthInfo> findByUserIdBetweenRegDate(String userId, Date fromHealthInfoRegDate, Date toHealthInfoRegDate) throws BaseException;

	/**
	 * 指定された健康情報IDとユーザIDと一致する健康情報を返す
	 *
	 * @param healthInfoId
	 *     健康情報ID
	 * @param userId
	 *     ユーザID
	 * @return 健康情報リスト
	 * @throws BaseException
	 *     基底例外
	 */
	List<HealthInfo> findByHealthInfoIdAndUserId(Integer healthInfoId, String userId) throws BaseException;

	/**
	 * 指定されたユーザIDの件数を返す
	 * @param userId ユーザID
	 * @return 件数
	 * @throws BaseException
	 *     基底例外
	 */
	int getSelectCountByUserId(String userId) throws BaseException;

}
