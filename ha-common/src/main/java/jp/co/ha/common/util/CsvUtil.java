package jp.co.ha.common.util;

import java.util.Arrays;
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

	private CsvUtil() {
	}

	/**
	 * ヘッダ名を取得する
	 * @param clazz
	 * @return ヘッダー名
	 */
	public static List<String> getHeaderList(Class<?> clazz) {
		return Arrays.asList(getCsvHeaderClass(clazz).names());
	}

	/**
	 * 指定されたクラス型付けてるCsvHeaderアノテーションを返す<br>
	 * @param clazz
	 * @return
	 */
	public static CsvHeader getCsvHeaderClass(Class<?> clazz) {
		return clazz.getAnnotation(CsvHeader.class);
	}

}
