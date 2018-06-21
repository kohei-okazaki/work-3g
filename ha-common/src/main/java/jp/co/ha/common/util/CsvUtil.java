package jp.co.ha.common.util;

import java.util.ArrayList;
import java.util.List;

import jp.co.ha.common.file.csv.annotation.CsvModel;

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
	 *            CSVヘッダのついたクラス型
	 * @return ヘッダー名
	 */
	public static List<String> getHeaderList(Class<?> clazz) {
		List<String> headerList = new ArrayList<String>();
		headerList.addAll(List.of(clazz.getAnnotation(CsvModel.class).headerNames()));
		return headerList;
	}

	/**
	 * フッタ名を取得する<br>
	 *
	 * @param clazz
	 *            CSVフッタのついたクラス型
	 * @return
	 */
	public static List<String> getFooterList(Class<?> clazz) {
		List<String> footerList = new ArrayList<String>();
		footerList.addAll(List.of(clazz.getAnnotation(CsvModel.class).footerNames()));
		return footerList;
	}

}
