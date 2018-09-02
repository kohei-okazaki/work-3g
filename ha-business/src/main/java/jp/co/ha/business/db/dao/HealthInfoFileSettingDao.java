package jp.co.ha.business.db.dao;

import jp.co.ha.business.db.entity.HealthInfoFileSetting;
import jp.co.ha.common.exception.DataBaseException;

/**
 * 健康情報ファイル設定Daoインターフェース<br>
 *
 */
public interface HealthInfoFileSettingDao {

	/** テーブル名 */
	public static final String TABLE_NAME = "HEALTH_INFO_FILE_SETTING";

	public static final String USER_ID = "USER_ID";
	public static final String HEADER_FLAG = "HEADER_FLAG";
	public static final String FOOTER_FLAG = "FOOTER_FLAG";
	public static final String MASK_FLAG = "MASK_FLAG";
	public static final String ENCLOSURE_CHAR_FLAG = "ENCLOSURE_CHAR_FLAG";
	public static final String UPDATE_DATE = "UPDATE_DATE";
	public static final String REG_DATE = "REG_DATE";

	/**
	 * 指定されたユーザIDの健康情報ファイル設定を取得<br>
	 *
	 * @param userId
	 *     ユーザID
	 * @return
	 * @throws DataBaseException
	 *     DBエラー
	 */
	HealthInfoFileSetting selectByUserId(String userId) throws DataBaseException;

	/**
	 * 健康情報ファイル設定を作成<br>
	 *
	 * @param healthInfoFileSetting
	 *     健康情報ファイル設定
	 * @throws DataBaseException
	 *     DBエラー
	 */
	void create(HealthInfoFileSetting healthInfoFileSetting) throws DataBaseException;

	/**
	 * 健康情報ファイル設定を更新する<br>
	 *
	 * @param healthInfoFileSetting
	 *     健康情報ファイル設定
	 * @throws DataBaseException
	 *     DBエラー
	 */
	void update(HealthInfoFileSetting healthInfoFileSetting) throws DataBaseException;

}
