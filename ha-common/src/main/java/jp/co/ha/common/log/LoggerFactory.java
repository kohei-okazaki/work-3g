package jp.co.ha.common.log;

import org.slf4j.Logger;

/**
 * ロガーファクトリークラス<br>
 *
 */
public class LoggerFactory {

	/**
	 * プライベートコンストラクタ<br>
	 */
	private LoggerFactory() {
	}

	/**
	 * AppLoggerを返す<br>
	 *
	 * @param clazz
	 *     クラス型
	 * @return
	 */
	public static AppLogger getAppLogger(Class<?> clazz) {
		return new AppLogger(org.slf4j.LoggerFactory.getLogger(clazz));
	}

	/**
	 * slf4jのロガーを返す<br>
	 *
	 * @param clazz
	 *     クラス型
	 * @return
	 */
	public static Logger getLogger(Class<?> clazz) {
		return org.slf4j.LoggerFactory.getLogger(clazz);
	}
}
