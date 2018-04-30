package jp.co.ha.business.parameter;

import jp.co.ha.common.manager.MainKey;
import jp.co.ha.common.manager.SubKey;

/**
 * 定数定義列挙<br>
 *
 */
public enum ParamConst {

	/** 健康情報ステータス */
	HEALTH_INFO_USER_STATUS_DOWN(MainKey.HEALTH_INFO_USER_STATUS, SubKey.DOWN, "10"),
	HEALTH_INFO_USER_STATUS_EVEN(MainKey.HEALTH_INFO_USER_STATUS, SubKey.EVEN, "20"),
	HEALTH_INFO_USER_STATUS_INCREASE(MainKey.HEALTH_INFO_USER_STATUS, SubKey.INCREASE, "30"),

	/** 健康情報ステータスメッセージ */
	HEALTH_INFO_USER_STATUS_DOWN_MESSAGE(MainKey.HEALTH_INFO_USER_STATUS, SubKey.DOWN_MESSAGE, "減りました"),
	HEALTH_INFO_USER_STATUS_EVEN_MESSAGE(MainKey.HEALTH_INFO_USER_STATUS, SubKey.EVEN_MESSAGE, "変化ありません"),
	HEALTH_INFO_USER_STATUS_INCREASE_MESSAGE(MainKey.HEALTH_INFO_USER_STATUS, SubKey.INCREASE_MESSAGE, "増えました"),

	/** ページタイプ */
	PAGE_VIEW_INPUT(MainKey.PAGE_VIEW, SubKey.INPUT, "0"),
	PAGE_VIEW_CONFIRM(MainKey.PAGE_VIEW, SubKey.CONFIRM, "1"),
	PAGE_VIEW_COMPLETE(MainKey.PAGE_VIEW, SubKey.COMPLETE, "2"),

	/** 共通真偽値 */
	FLAG_TRUE(MainKey.FLAG, SubKey.TRUE, "1"),
	FLAG_FALSE(MainKey.FLAG, SubKey.FALSE, "0"),

	/** CSVファイル名 */
	CSV_FILE_NAME_HEALTH_INFO(MainKey.CSV_FILE_NAME, SubKey.HEALTH_INFO, "HealthInfo.csv"),
	CSV_FILE_NAME_REFERNCE_RESULT(MainKey.CSV_FILE_NAME, SubKey.REFERNCE_RESULT, "ReferenceResult.csv"),
	;

	/**
	 * コンストラクタ<br>
	 * @param mainKey
	 * @param subKey
	 * @param value
	 */
	private ParamConst(MainKey mainKey, SubKey subKey, String value) {
		this.mainKey = mainKey;
		this.subKey = subKey;
		this.value = value;
	}

	/** メインキー */
	private MainKey mainKey;
	/** サブキー */
	private SubKey subKey;
	/** 値 */
	private String value;

	/**
	 * 指定されたMainKeyとSubKeyと一致する定数定義列挙を返す<br>
	 * @param mainKey
	 * @param subKey
	 * @return
	 */
	public static ParamConst of(MainKey mainKey, SubKey subKey) {

		for (ParamConst paramConst : ParamConst.class.getEnumConstants()) {
			if (paramConst.mainKey.equals(mainKey)
					&& paramConst.subKey.equals(subKey)) {
				return paramConst;
			}
		}
		return null;
	}

	/**
	 * mainKeyを返す<br>
	 * @return mainKey
	 */
	public MainKey getMainKey() {
		return mainKey;
	}

	/**
	 * subKeyを返す<br>
	 * @return subKey
	 */
	public SubKey getSubKey() {
		return subKey;
	}

	/**
	 * valueを返す<br>
	 * @return value
	 */
	public String getValue() {
		return value;
	}

}
