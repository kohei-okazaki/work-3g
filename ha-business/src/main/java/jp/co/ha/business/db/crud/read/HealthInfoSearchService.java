package jp.co.ha.business.db.crud.read;

import java.util.Date;
import java.util.List;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報検索サービス<br>
 *
 */
public interface HealthInfoSearchService {

	/**
	 * 指定されたユーザIDと一致する健康情報のリストを返却する<br>
	 *
	 * @param userId
	 *     ユーザID
	 * @return List<HealthInfoDto>
	 * @throws BaseException
	 */
	List<HealthInfo> findByUserId(String userId) throws BaseException;

	/**
	 * 指定されたデータIDからと一致する健康情報を返却する<br>
	 *
	 * @param healthInfoId
	 *     	健康情報ID
	 * @return 健康情報Entity
	 * @throws BaseException
	 */
	HealthInfo findByHealthInfoId(Integer healthInfoId) throws BaseException;

	/**
	 * 指定したユーザIDで最後に登録した健康情報を返す<br>
	 *
	 * @param userId
	 *     ユーザID
	 * @return HealthInfo
	 * @throws BaseException
	 */
	HealthInfo findLastByUserId(String userId) throws BaseException;

	/**
	 * 指定されたユーザIDと登録日時の健康情報を返す<br>
	 *
	 * @param userId
	 *     ユーザID
	 * @param regDate
	 *     YYYYMMDD
	 * @return
	 * @throws BaseException
	 */
	List<HealthInfo> findByUserIdAndRegDate(String userId, Date regDate) throws BaseException;

	/**
	 * 指定されたユーザIDと指定された登録日時の期間内の健康情報を返す<br>
	 *
	 * @param userId
	 *     ユーザID
	 * @param fromRegDate
	 *     YYYYMMDD
	 * @param toRegDate
	 *     YYYYMMDD
	 * @return
	 * @throws BaseException
	 */
	List<HealthInfo> findByUserIdBetweenRegDate(String userId, Date fromRegDate, Date toRegDate) throws BaseException;

}
