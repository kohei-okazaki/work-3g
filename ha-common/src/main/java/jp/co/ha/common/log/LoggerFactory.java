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
	 * <code>Logger</code>を返す<br>
	 *
	 * @param clazz
	 *     クラス型
	 * @return
	 */
	public static Logger getLogger(Class<?> clazz) {
		return new Logger(org.slf4j.LoggerFactory.getLogger(clazz));
	}

}
