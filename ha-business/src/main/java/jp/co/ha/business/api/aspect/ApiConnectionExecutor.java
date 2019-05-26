package jp.co.ha.business.api.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.web.controller.BaseRestController;
import jp.co.ha.web.form.BaseApiRequest;
import jp.co.ha.web.form.BaseApiResponse;

/**
 * API通信共通クラス
 *
 */
@Aspect
@Component
public class ApiConnectionExecutor {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(ApiConnectionExecutor.class);

	/**
	 * APIのリクエスト/レスポンスログ(正常系)を出力する<br>
	 * レスポンスログ(異常系)は以下のクラスのメソッドで行う<br>
	 * <ul>
	 * <li>{@link BaseRestController#jsonProcessingExceptionHandle}</li>
	 * <li>{@link BaseRestController#appExceptionHandle}</li>
	 * </ul>
	 *
	 * @param pjp
	 *     リクエスト情報
	 * @return response
	 * @throws Throwable
	 *     実行時のエラー
	 */
	@Around("execution(* jp.co.ha.business.api.service.impl.*ServiceImpl.execute(..))")
	public void outApiLog(ProceedingJoinPoint pjp) throws Throwable {

		// Requestログ出力
		Arrays.stream(pjp.getArgs()).filter(e -> e instanceof BaseApiRequest).forEach(e -> LOG.infoRes(e));

		pjp.proceed();

		// Responseログ出力
		Arrays.stream(pjp.getArgs()).filter(e -> e instanceof BaseApiResponse).forEach(e -> LOG.infoRes(e));
	}

}
