package jp.co.ha.common.util;

import java.util.List;

import jp.co.ha.common.file.csv.annotation.CsvHeader;

/**
 * CsvのUtilクラス<br>
 * インスタンスの生成を制限<br>
 */
public class CsvUtil {

	/** シングルクォート */
	public static final String SINGLE_QUOTE = "\'";
	/** ダブルクォート */
	public static final String DOBBLE_QUOTE = "\"";

	/**
	 * プライベートコンストラクタ<br>
	 */
	private CsvUtil() {
	}

	/**
	 * ヘッダ名を取得する<br>
	 *
	 * @param clazz
	 *            CSVヘッダーのついたクラス型
	 * @return ヘッダー名
	 */
	public static List<String> getHeaderList(Class<?> clazz) {
		return List.of(getCsvHeaderClass(clazz).names());
	}

	/**
	 * 指定されたクラス型付けてるCsvHeaderアノテーションを返す<br>
	 *
	 * @param clazz
	 *            CSVヘッダーのついたクラス型
	 * @return
	 */
	public static CsvHeader getCsvHeaderClass(Class<?> clazz) {
		return clazz.getAnnotation(CsvHeader.class);
	}

}
