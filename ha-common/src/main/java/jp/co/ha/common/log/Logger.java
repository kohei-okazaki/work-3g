package jp.co.ha.common.log;

/**
 * アプリ内のロガークラス<br>
 *
 */
public class Logger {

	/** ロガー */
	private org.slf4j.Logger logger;

	/**
	 * コンストラクタ
	 *
	 * @param logger
	 *     org.slf4j.Logger
	 */
	Logger(org.slf4j.Logger logger) {
		this.logger = logger;
	}

	/**
	 * Debugログを出力する
	 *
	 * @param bean
	 *     Bean
	 */
	public void debugRes(Object bean) {
		logger.debug(LogMessageFactory.getLogMessage(bean));
	}

	/**
	 * Debugログを出力する
	 *
	 * @param prefix
	 *     接頭語
	 * @param bean
	 *     Bean
	 */
	public void debugRes(String prefix, Object bean) {
		logger.debug(prefix + LogMessageFactory.getLogMessage(bean));
	}

	/**
	 * Debugログを出力する
	 *
	 * @param msg
	 *     ログメッセージ
	 */
	public void debug(String msg) {
		logger.debug(msg);
	}

	/**
	 * Debugログを出力する
	 *
	 * @param bean
	 *     Bean
	 */
	public void infoRes(Object bean) {
		logger.info(LogMessageFactory.getLogMessage(bean));
	}

	/**
	 * Infoログを出力する
	 *
	 * @param prefix
	 *     接頭語
	 * @param bean
	 *     Bean
	 */
	public void infoRes(String prefix, Object bean) {
		logger.info(prefix + LogMessageFactory.getLogMessage(bean));
	}

	/**
	 * Infoログを出力する
	 *
	 * @param msg
	 *     ログメッセージ
	 */
	public void info(String msg) {
		logger.info(msg);
	}

	/**
	 * Warnログを出力する
	 *
	 * @param bean
	 *     Bean
	 */
	public void warnRes(Object bean) {
		logger.warn(LogMessageFactory.getLogMessage(bean));
	}

	/**
	 * Warnログを出力する
	 *
	 * @param prefix
	 *     接頭語
	 * @param bean
	 *     Bean
	 */
	public void warnRes(String prefix, Object bean) {
		logger.warn(prefix + LogMessageFactory.getLogMessage(bean));
	}

	/**
	 * Warnログを出力する
	 *
	 * @param bean
	 *     Bean
	 * @param t
	 *     例外
	 */
	public void warnRes(Object bean, Throwable t) {
		logger.warn(LogMessageFactory.getLogMessage(bean), t);
	}

	/**
	 * Warnログを出力する
	 *
	 * @param msg
	 *     ログメッセージ
	 */
	public void warn(String msg) {
		logger.warn(msg);
	}

	/**
	 * Warnログを出力する
	 *
	 * @param msg
	 *     ログメッセージ
	 * @param t
	 *     例外
	 */
	public void warn(String msg, Throwable t) {
		logger.warn(msg, t);
	}

	/**
	 * Errorログを出力する
	 *
	 * @param bean
	 *     Bean
	 */
	public void errorRes(Object bean) {
		logger.error(LogMessageFactory.getLogMessage(bean));
	}

	/**
	 * Errorログを出力する
	 *
	 * @param prefix
	 *     接頭語
	 * @param bean
	 *     Bean
	 */
	public void errorRes(String prefix, Object bean) {
		logger.error(prefix + LogMessageFactory.getLogMessage(bean));
	}

	/**
	 * Errorログを出力する
	 *
	 * @param bean
	 *     Bean
	 * @param t
	 *     例外
	 */
	public void errorRes(Object bean, Throwable t) {
		logger.error(LogMessageFactory.getLogMessage(bean), t);
	}

	/**
	 * Errorログを出力する
	 *
	 * @param msg
	 *     ログメッセージ
	 */
	public void error(String msg) {
		logger.error(msg);
	}

	/**
	 * Errorログを出力する
	 *
	 * @param msg
	 *     ログメッセージ
	 * @param t
	 *     例外
	 */
	public void error(String msg, Throwable t) {
		logger.error(msg, t);
	}

}
