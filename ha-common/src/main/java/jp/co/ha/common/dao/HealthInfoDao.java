package jp.co.ha.common.dao;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import jp.co.ha.common.entity.HealthInfo;

/**
 * 健康情報のDaoインターフェイス
 *
 */
public interface HealthInfoDao extends BaseDao {

	/** 保存先シート名 */
	public static final String SHEET = "HEALTH_INFO";

	/**
	 * 指定したユーザIDの健康情報を返す<br>
	 *
	 * @param userId
	 *            ユーザID
	 * @return List<HealthInfo> 健康情報リスト
	 */
	List<HealthInfo> findByUserId(String userId);

	/**
	 * 指定されたデータIDに対応する健康情報を返す<br>
	 *
	 * @param dataId
	 *            データID
	 * @return HealthInfo 健康情報
	 */
	HealthInfo findByDataId(String dataId);

	/**
	 * 健康情報を登録する<br>
	 *
	 * @param healthInfo
	 *            健康情報
	 * @throws DuplicateKeyException
	 */
	void create(HealthInfo healthInfo) throws DuplicateKeyException;

}
