package jp.co.ha.common.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.exception.DataBaseException;

/**
 * 健康情報のDaoインターフェース
 *
 */
public interface HealthInfoDao {

	public static final String TABLE_NAME = "HEALTH_INFO";
	public static final String HEALTH_INFO_ID = "HEALTH_INFO_ID";
	public static final String USER_ID = "USER_ID";
	public static final String HEIGHT = "HEIGHT";
	public static final String WEIGHT = "WEIGHT";
	public static final String BMI = "BMI";
	public static final String STANDARD_WEIGHT = "STANDARD_WEIGHT";
	public static final String USER_STATUS = "USER_STATUS";
	public static final String REG_DATE = "REG_DATE";
	/**
	 * 指定したユーザIDの健康情報を返す<br>
	 *
	 * @param userId
	 *            ユーザID
	 * @return List<HealthInfo> 健康情報リスト
	 */
	List<HealthInfo> selectByUserId(String userId) throws DataBaseException;

	/**
	 * 指定された健康情報IDに対応する健康情報を返す<br>
	 *
	 * @param healthInfoId
	 *            健康情報ID
	 * @return HealthInfo 健康情報
	 */
	HealthInfo selectByHealthInfoId(BigDecimal healthInfoId) throws DataBaseException;

	/**
	 * 健康情報を登録する<br>
	 * @param healthInfo 健康情報
	 * @throws DuplicateKeyException
	 * @throws DataBaseException
	 */
	void create(HealthInfo healthInfo) throws DuplicateKeyException, DataBaseException;

}
