package jp.co.ha.business.parameter;

import java.util.ArrayList;
import java.util.List;

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
	HEALTH_INFO_USER_STATUS_DOWN_MESSAGE(MainKey.HEALTH_INFO_USER_STATUS_MESSAGE, SubKey.DOWN, "減りました"),
	HEALTH_INFO_USER_STATUS_EVEN_MESSAGE(MainKey.HEALTH_INFO_USER_STATUS_MESSAGE, SubKey.EVEN, "変化ありません"),
	HEALTH_INFO_USER_STATUS_INCREASE_MESSAGE(MainKey.HEALTH_INFO_USER_STATUS_MESSAGE, SubKey.INCREASE, "増えました"),

	/** ページタイプ：入力 */
	PAGE_VIEW_INPUT(MainKey.PAGE_VIEW, SubKey.INPUT, "0"),
	/** ページタイプ：確認 */
	PAGE_VIEW_CONFIRM(MainKey.PAGE_VIEW, SubKey.CONFIRM, "1"),
	/** ページタイプ：完了 */
	PAGE_VIEW_COMPLETE(MainKey.PAGE_VIEW, SubKey.COMPLETE, "2"),

	/** 真偽値：真*/
	FLAG_TRUE(MainKey.FLAG, SubKey.TRUE, "1"),
	/** 真偽値：偽*/
	FLAG_FALSE(MainKey.FLAG, SubKey.FALSE, "0"),

	/** CSVファイル名 */
	CSV_FILE_NAME_HEALTH_INFO(MainKey.CSV_FILE_NAME, SubKey.HEALTH_INFO,
			"HealthInfo.csv"), CSV_FILE_NAME_REFERNCE_RESULT(MainKey.CSV_FILE_NAME, SubKey.REFERNCE_RESULT,
					"ReferenceResult.csv"),
					;

	/**
	 * コンストラクタ<br>
	 *
	 * @param mainKey
	 *            メインキー
	 * @param subKey
	 *            サブキー
	 * @param value
	 *            値
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
	 *
	 * @param mainKey
	 *            メインキー
	 * @param subKey
	 *            サブキー
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
	 * メインキーと一致するParamConstのリストを返す<br>
	 *
	 * @param mainKey
	 *            メインキー
	 * @return
	 */
	public static List<ParamConst> ofList(MainKey mainKey) {
		List<ParamConst> resultList = new ArrayList<>();
		for (ParamConst paramConst : ParamConst.class.getEnumConstants()) {
			if (paramConst.mainKey.equals(mainKey)) {
				resultList.add(paramConst);
			}
		}
		return resultList;
	}

	/**
	 * mainKeyを返す<br>
	 *
	 * @return mainKey メインキー
	 */
	public MainKey getMainKey() {
		return mainKey;
	}

	/**
	 * subKeyを返す<br>
	 *
	 * @return subKey サブキー
	 */
	public SubKey getSubKey() {
		return subKey;
	}

	/**
	 * valueを返す<br>
	 *
	 * @return value 値
	 */
	public String getValue() {
		return value;
	}

}
