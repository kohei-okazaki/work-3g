package jp.co.ha.web.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

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
	 *     JoinPoint
	 */
	@Before("execution(* *..*ServiceImpl.*(..))*")
	public void startLog(JoinPoint jp) {
		LOG.info("■■■■■■■■■■■■■■■開始■■■■■■■■■■■■■■■" + jp.getThis().toString());
		LOG.info("=====>" + jp.getSignature().getName());
	}

	/**
	 * 終了ログを出力する<br>
	 *
	 * @param jp
	 *     JoinPoint
	 */
	@After("execution(* *..*ServiceImpl.*(..))*")
	public void endLog(JoinPoint jp) {
		LOG.info("■■■■■■■■■■■■■■■終了■■■■■■■■■■■■■■■" + jp.getThis().toString());
	}

}
