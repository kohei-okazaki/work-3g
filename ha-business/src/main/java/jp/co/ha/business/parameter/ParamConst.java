package jp.co.ha.business.parameter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 定数定義列挙<br>
 *
 */
public enum ParamConst {

	/** ページタイプ：入力 */
	PAGE_VIEW_INPUT(MainKey.PAGE_VIEW, SubKey.INPUT, "0"),
	/** ページタイプ：確認 */
	PAGE_VIEW_CONFIRM(MainKey.PAGE_VIEW, SubKey.CONFIRM, "1"),
	/** ページタイプ：完了 */
	PAGE_VIEW_COMPLETE(MainKey.PAGE_VIEW, SubKey.COMPLETE, "2"),

	/** 真偽値：真 */
	FLAG_TRUE(MainKey.FLAG, SubKey.TRUE, "1"),
	/** 真偽値：偽 */
	FLAG_FALSE(MainKey.FLAG, SubKey.FALSE, "0"),

	/** 健康情報CSVファイル名 */
	CSV_FILE_NAME_HEALTH_INFO(MainKey.CSV_FILE_NAME, SubKey.HEALTH_INFO, "HealthInfo.csv"),
	/** 結果照会CSVファイル名 */
	CSV_FILE_NAME_REFERNCE_RESULT(MainKey.CSV_FILE_NAME, SubKey.REFERNCE_RESULT, "ReferenceResult.csv"),
	;

	/**
	 * コンストラクタ<br>
	 *
	 * @param mainKey
	 *     メインキー
	 * @param subKey
	 *     サブキー
	 * @param value
	 *     値
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
	 *     メインキー
	 * @param subKey
	 *     サブキー
	 * @return
	 */
	public static ParamConst of(MainKey mainKey, SubKey subKey) {
		return Stream.of(ParamConst.class.getEnumConstants())
				.filter(paramConst -> paramConst.mainKey.equals(mainKey) && paramConst.subKey.equals(subKey))
				.findFirst()
				.orElse(null);
	}

	/**
	 * メインキーと一致するParamConstのリストを返す<br>
	 *
	 * @param mainKey
	 *     メインキー
	 * @return
	 */
	public static List<ParamConst> ofList(MainKey mainKey) {
		return Stream.of(ParamConst.class.getEnumConstants())
				.filter(param -> param.mainKey.equals(mainKey))
				.collect(Collectors.toList());
	}

	/**
	 * mainKeyを返す<br>
	 *
	 * @return mainKey メインキー
	 */
	public MainKey getMainKey() {
		return this.mainKey;
	}

	/**
	 * subKeyを返す<br>
	 *
	 * @return subKey サブキー
	 */
	public SubKey getSubKey() {
		return this.subKey;
	}

	/**
	 * valueを返す<br>
	 *
	 * @return value 値
	 */
	public String getValue() {
		return this.value;
	}

}
