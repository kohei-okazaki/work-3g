package jp.co.ha.web.api.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.web.api.RestApiExceptionHandler;
import jp.co.ha.web.form.BaseRestApiRequest;
import jp.co.ha.web.form.BaseRestApiResponse;

/**
 * API通信共通クラス
 *
 * @version 1.0.0
 */
@Aspect
@Component
public class ApiConnectAspect {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(ApiConnectAspect.class);

    /**
     * APIのリクエスト/レスポンスログ(正常系)を出力する<br>
     * レスポンスログ(異常系)は以下のクラスのメソッドで行う<br>
     * <li>{@linkplain RestApiExceptionHandler#handleException}</li>
     *
     * @param pjp
     *     ProceedingJoinPoint
     * @return APIレスポンス
     * @throws Throwable
     *     実行時のエラー
     */
    @Around("execution(* jp.co.ha.web.api.BaseApi.callApi(..))")
    public Object outApiLog(ProceedingJoinPoint pjp) throws Throwable {

        // Requestログ出力
        Arrays.stream(pjp.getArgs()).filter(e -> e instanceof BaseRestApiRequest)
                .forEach(e -> LOG.infoRes(e));

        Object object = pjp.proceed();

        // Responseログ出力
        Arrays.stream(pjp.getArgs()).filter(e -> e instanceof BaseRestApiResponse)
                .forEach(e -> LOG.infoRes(e));

        return object;
    }

}
