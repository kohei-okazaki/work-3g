package jp.co.ha.common.log;

import java.util.Optional;

import jp.co.ha.common.util.StringUtil;

/**
 * Logの追加情報を管理するクラス
 * 
 * @since 1.0
 */
public class MDC {

	/**
	 * プライベートコンストラクタ
	 */
	private MDC() {
	}

	/**
	 * {@code MDC}に値を設定
	 *
	 * @param key
	 *     キー
	 * @param value
	 *     値
	 */
	public static void put(String key, String value) {
		org.slf4j.MDC.put(key, value);
	}

	/**
	 * {@code MDC}から<code>key</code>に対応する値を取得
	 *
	 * @param key
	 *     キー
	 * @return 設定値
	 */
	public static String get(String key) {
		return Optional.ofNullable(org.slf4j.MDC.get(key)).orElse("<NULL>");
	}

	/**
	 * {@code MDC}から<code>key</code>を削除
	 *
	 * @param key
	 *     キー
	 */
	public static void remove(String key) {
		org.slf4j.MDC.remove(key);
	}

	/**
	 * {@code MDC}に<code>key</code>が設定されているかどうか評価
	 *
	 * @param key
	 *     キー
	 * @return 評価値
	 */
	public static boolean isContains(String key) {
		return StringUtil.hasValue(get(key));
	}
}