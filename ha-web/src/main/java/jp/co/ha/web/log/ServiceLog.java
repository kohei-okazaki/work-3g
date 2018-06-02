package jp.co.ha.web.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * サービスログクラス<br>
 *
 */
@Aspect
@Component
public class ServiceLog {
	/** ロガー */
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	/**
	 * 開始ログを出力する<br>
	 *
	 * @param jp
	 *            JoinPoint
	 */
	@Before("execution(* *..*ServiceImpl.*(..))*")
	public void startLog(JoinPoint jp) {
		LOG.info("■■■■■■■■■■■■■■■開始■■■■■■■■■■■■■■■" + jp.toString());
	}

	/**
	 * 終了ログを出力する<br>
	 *
	 * @param jp
	 *            JoinPoint
	 */
	@After("execution(* *..*ServiceImpl.*(..))*")
	public void endLog(JoinPoint jp) {
		LOG.info("■■■■■■■■■■■■■■■終了■■■■■■■■■■■■■■■" + jp.toString());
	}

}
