package jp.co.ha.common.log;

/**
 * アプリ内のロガークラス<br>
 *
 */
/**
 * @author kou1210hei
 *
 */
public class Logger {

	/** ロガー */
	private org.slf4j.Logger logger;

	/**
	 * コンストラクタ
	 * @param logger org.slf4j.Logger
	 */
	Logger(org.slf4j.Logger logger) {
		this.logger = logger;
	}

	/**
	 * 指定したBeanのDebugログを出力する
	 * @param bean Bean
	 */
	public void debugRes(Object bean) {
		logger.debug(LogMessageFactory.getLogMessage(bean));
	}

	/**
	 * 指定したBeanのDebugログを出力する
	 * @param prefix 接頭語
	 * @param bean Bean
	 */
	public void debugRes(String prefix, Object bean) {
		logger.debug(prefix + LogMessageFactory.getLogMessage(bean));
	}

	/**
	 * @param msg
	 */
	public void debug(String msg) {
		logger.debug(msg);
	}

	/**
	 * 指定したBeanのInfoログを出力する
	 * @param bean Bean
	 */
	public void infoRes(Object bean) {
		logger.info(LogMessageFactory.getLogMessage(bean));
	}

	public void infoRes(String prefix, Object bean) {
		logger.info(prefix + LogMessageFactory.getLogMessage(bean));
	}

	public void info(String msg) {
		logger.info(msg);
	}

	public void warnRes(Object bean) {
		logger.warn(LogMessageFactory.getLogMessage(bean));
	}

	public void warnRes(String prefix, Object bean) {
		logger.warn(prefix + LogMessageFactory.getLogMessage(bean));
	}

	public void warnRes(Object bean, Throwable t) {
		logger.warn(LogMessageFactory.getLogMessage(bean), t);
	}

	public void warn(String msg) {
		logger.warn(msg);
	}

	public void warn(String msg, Throwable t) {
		logger.warn(msg, t);
	}

	public void errorRes(Object bean) {
		logger.error(LogMessageFactory.getLogMessage(bean));
	}

	public void errorRes(String prefix, Object bean) {
		logger.error(prefix + LogMessageFactory.getLogMessage(bean));
	}

	public void errorRes(Object bean, Throwable t) {
		logger.error(LogMessageFactory.getLogMessage(bean), t);
	}

	public void error(String msg) {
		logger.error(msg);
	}

	public void error(String msg, Throwable t) {
		logger.error(msg, t);
	}

}
