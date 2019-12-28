package jp.co.ha.common.db;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

import jp.co.ha.common.util.StringUtil;

/**
 * 検索オプション<br>
 * 検索における共通事項をまとめて保持するクラス
 *
 * @since 1.0
 */
public class SelectOption {

	/** ソートマップ */
	private Map<String, SortType> orderByMap = new LinkedHashMap<>();
	/** 検索上限数 */
	private int limit = 10000;

	/**
	 * 指定したカラムでのソートを設定する
	 *
	 * @param column
	 *     カラム
	 * @param sortType
	 *     昇順/降順の列挙
	 * @return メソッドチェーン用に検索オプション
	 */
	public SelectOption put(String column, SortType sortType) {
		orderByMap.put(column, sortType);
		return this;
	}

	/**
	 * limitを返す
	 *
	 * @return limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * limitを設定する
	 *
	 * @param limit
	 *     検索上限数
	 * @return メソッドチェーン用に検索オプション
	 */
	public SelectOption setLimit(int limit) {
		this.limit = limit;
		return this;
	}

	/**
	 * ORDER BY句を文字列表現で返す
	 *
	 * @return ORDER BY句の文字列表現
	 */
	public String toOrderBy() {
		StringJoiner sj = new StringJoiner(StringUtil.COMMA);
		this.orderByMap.entrySet()
				.stream()
				.map(e -> e.getKey() + StringUtil.SPACE + e.getValue())
				.forEach(e -> sj.add(e));
		return sj.toString();
	}

	/**
	 * DB検索のソートの列挙体
	 *
	 * @since 1.0
	 */
	public static enum SortType {

		/** 昇順 */
		ASC,
		/** 降順 */
		DESC;

	}

}
