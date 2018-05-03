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
	 * @param userId
	 * @return List<HealthInfo>
	 */
	List<HealthInfo> findByUserId(String userId);

	/**
	 * 指定されたデータIDに対応する健康情報を返す<br>
	 * @param dataId
	 * @return HealthInfo
	 */
	HealthInfo findByDataId(String dataId);

	/**
	 * 健康情報を登録する<br>
	 * @param healthInfo
	 * @throws DuplicateKeyException
	 */
	void registHealthInfo(HealthInfo healthInfo) throws DuplicateKeyException;

	/**
	 * 指定したユーザIDで最後に登録した健康情報を返す<br>
	 * @param userId
	 * @return HealthInfo
	 */
	HealthInfo getLastHealthInfoByUserId(String userId);

}
