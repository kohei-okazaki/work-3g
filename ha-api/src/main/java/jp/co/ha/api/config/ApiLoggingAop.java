package jp.co.ha.api.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

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
	private final Logger LOG = LoggerFactory.getLogger(getClass());

//	@Before("execution(* *..BaseRestController.doPost(..))*")
//	public void apiRequestLog(JoinPoint jp) {
//		for (Object arg : jp.getArgs()) {
//			if (arg instanceof BaseRequest) {
//				// リクエストログを出力
//				LOG.infoRes(arg);
//			}
//		}
//	}

//	@AfterReturning(value = "execution(* *..BaseRestController.doPost(..))*", returning = "response")
//	public void apiResponseSuccessLog(JoinPoint jp, BaseResponse response) {
//		LOG.infoRes(response);
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

//	/**
//	 * APIのログを出力する
//	 *
//	 * @param jp
//	 *     リクエスト情報
//	 */
//	@Around("execution(* *..BaseRestController.doPost(..))*")
//	public BaseResponse startApiRequestLog(ProceedingJoinPoint pjp) {
//		for (Object arg : pjp.getArgs()) {
//			if (arg instanceof BaseRequest) {
//				// リクエストログを出力
//				LOG.infoRes(arg);
//			}
//		}
//		try {
//			BaseResponse response = (BaseResponse) pjp.proceed();
//			LOG.infoRes(response);
//			return response;
//		} catch (BaseException e) {
//			return new ErrorResponse(e);
//		} catch (JsonProcessingException e) {
//			if (e instanceof InvalidFormatException) {
//				InvalidFormatException jfe = (InvalidFormatException) e;
//				return new ErrorResponse(new ApiException(ErrorCode.JSON_FORMAT_ERROR, jfe.getValue() + "はリクエスト形式エラーです"));
//			} else if (e instanceof JsonParseException) {
//				return  new ErrorResponse(new ApiException(ErrorCode.JSON_PARSE_ERROR, e.getLocation().getColumnNr() + "行目がjson形式ではありません"));
//			} else if (e instanceof JsonProcessingException) {
//				return  new ErrorResponse(new ApiException(ErrorCode.JSON_PARSE_ERROR, e.getLocation().getColumnNr() + ":json形式ではありません"));
//			}
//			return null;
//		} catch (Throwable e) {
//			// Controller側でエラーハンドリングできているので処理を行わない
//		}
//		return null;
//	}

}
