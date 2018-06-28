package jp.co.ha.common.log;

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
	 * @param clazz
	 * @return
	 */
	public static AppLogger getLogger(Class<?> clazz) {
		return new AppLogger(clazz);
	}
}
