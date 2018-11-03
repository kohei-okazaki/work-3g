package jp.co.ha.business.db.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * DB更新処理の共通クラス
 *
 */
@Aspect
@Component
public class UpdateExecutor {

	/** ロガー */
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Before("execution(* *..*UpdateServiceImpl.update(..))*")
	public void startLog(JoinPoint jp) {
		LOG.info("■■■■■■■■■■■■■■■開始■■■■■■■■■■■■■■■" + jp.getThis().toString());
		LOG.info("=====>" + jp.getSignature().getName());
	}
}
