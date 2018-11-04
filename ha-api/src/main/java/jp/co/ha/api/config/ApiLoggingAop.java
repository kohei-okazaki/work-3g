package jp.co.ha.api.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import jp.co.ha.common.api.controller.BaseRestController;
import jp.co.ha.common.api.request.BaseRequest;
import jp.co.ha.common.api.response.BaseResponse;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * API AOPクラス<br>
 *
 */
@Aspect
@Component
public class ApiLoggingAop {

	/** ロガー */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

//	@Before("execution(* *jp.co.ha.api.service.impl.*ServiceImpl.execute(..)) throws BaseException")
//	public void apiRequestLog(JoinPoint jp) {
//		for (Object arg : jp.getArgs()) {
//			if (arg instanceof BaseApiRequest) {
//				// リクエストログを出力
//				LOG.infoRes(arg);
//			}
//		}
//	}

//	@After("execution(* *jp.co.ha.api.service.impl.*ServiceImpl.execute(..)) throws BaseException")
//	public void apiResponseSuccessLog(JoinPoint jp) {
//		for (Object arg : jp.getArgs()) {
//
//			System.out.println(jp.getTarget());
//			System.out.println(jp.getSignature());
//			if (arg instanceof BaseApiResponse) {
//				// レスポンスログを出力
//				LOG.infoRes(arg);
//			}
//		}
//	}

//	@AfterThrowing(value = "execution(* *..BaseRestController.appExceptionHandle(..))*", throwing = "e")
//	public void apiResponseFailureLog(JoinPoint jp, Exception e) {
//		BaseResponse response = null;
//		if (e instanceof BaseException) {
//			response = new ErrorResponse((BaseException) e);
//		} else if (e instanceof InvalidFormatException) {
//			InvalidFormatException jfe = (InvalidFormatException) e;
//			response = new ErrorResponse(new ApiException(ErrorCode.JSON_FORMAT_ERROR, jfe.getValue() + "はリクエスト形式エラーです"));
//		} else if (e instanceof JsonParseException) {
//			JsonProcessingException jpe = (JsonProcessingException) e;
//			response = new ErrorResponse(new ApiException(ErrorCode.JSON_PARSE_ERROR, jpe.getLocation().getColumnNr() + "行目がjson形式ではありません"));
//		} else if (e instanceof JsonProcessingException) {
//			JsonProcessingException jpe = (JsonProcessingException) e;
//			response = new ErrorResponse(new ApiException(ErrorCode.JSON_PARSE_ERROR, jpe.getLocation().getColumnNr() + ":json形式ではありません"));
//		}
//		LOG.infoRes(response);
//	}

	/**
	 * APIのリクエスト/レスポンスログ(正常系)を出力する<br>
	 * レスポンスログ(異常系)は
	 * @see BaseRestController#jsonProcessingExceptionHandle(com.fasterxml.jackson.core.JsonProcessingException)
	 * @see BaseRestController#appExceptionHandle(jp.co.ha.common.exception.BaseException)
	 *
	 * @param pjp
	 *     リクエスト情報
	 * @throws Throwable
	 */
	@Around("execution(* *jp.co.ha.api.service.impl.*ServiceImpl.execute(..)) throws BaseException")
	public BaseResponse startApiRequestLog(ProceedingJoinPoint pjp) throws Throwable {
		for (Object arg : pjp.getArgs()) {
			if (arg instanceof BaseRequest) {
				// リクエストログを出力
				LOG.infoRes(arg);
			}
		}

		// jp.co.ha.api.service.impl.*ServiceImpl.execute実行
		BaseResponse response = (BaseResponse) pjp.proceed();
		// レスポンスログを出力
		LOG.infoRes(response);
		return response;

	}

}
