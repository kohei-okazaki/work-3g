package jp.co.ha.common.log;

import org.slf4j.LoggerFactory;

/**
 * ロガーファクトリークラス<br>
 *
 */
public class AppLoggerFactory {

	/**
	 * プライベートコンストラクタ<br>
	 */
	private AppLoggerFactory() {
	}

	/**
	 * ロガーインスタンスを返す<br>
	 *
	 * @param clazz
	 *     クラス
	 * @return
	 */
	public static AppLogger getLogger(Class<?> clazz) {
		return new AppLogger(LoggerFactory.getLogger(clazz));
	}
}
