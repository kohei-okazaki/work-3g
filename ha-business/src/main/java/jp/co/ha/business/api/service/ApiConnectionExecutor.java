package jp.co.ha.business.api.service;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.web.controller.BaseRestController;
import jp.co.ha.web.request.BaseApiRequest;
import jp.co.ha.web.response.BaseApiResponse;

/**
 * API通信共通クラス
 *
 */
@Aspect
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
	@Around("execution(* *jp.co.ha.business.api.service.impl.*ServiceImpl.execute(..)) throws BaseException")
	public BaseApiResponse outApiLog(ProceedingJoinPoint pjp) throws Throwable {

		// リクエストログを出力
		Arrays.stream(pjp.getArgs()).filter(e -> e instanceof BaseApiRequest).forEach(e -> LOG.infoRes(e));

		// jp.co.ha.business.api.service.impl.*ServiceImpl#execute実行
		BaseApiResponse response = (BaseApiResponse) pjp.proceed();
		// レスポンスログを出力
		LOG.infoRes(response);
		return response;
	}

}
