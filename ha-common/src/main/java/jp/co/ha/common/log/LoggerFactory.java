package jp.co.ha.common.log;

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
	public static Logger getAppLogger(Class<?> clazz) {
		return new Logger(org.slf4j.LoggerFactory.getLogger(clazz));
	}

}
