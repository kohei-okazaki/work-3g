package jp.co.ha.business.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import jp.co.ha.common.api.controller.BaseRestController;
import jp.co.ha.common.api.request.BaseRequest;
import jp.co.ha.common.api.response.BaseResponse;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * API通信共通クラス<br>
 *
 */
@Aspect
public class ApiCommonExecutor {

	/** ロガー */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

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
	 * @throws Throwable
	 */
	@Around("execution(* *jp.co.ha.api.service.impl.*ServiceImpl.execute(..)) throws BaseException")
	public BaseResponse outApiLog(ProceedingJoinPoint pjp) throws Throwable {

		// リクエストログを出力
		Arrays.stream(pjp.getArgs()).filter(e -> e instanceof BaseRequest).forEach(e -> LOG.infoRes(e));

		// jp.co.ha.api.service.impl.*ServiceImpl.execute実行
		BaseResponse response = (BaseResponse) pjp.proceed();
		// レスポンスログを出力
		LOG.infoRes(response);
		return response;
	}

}
