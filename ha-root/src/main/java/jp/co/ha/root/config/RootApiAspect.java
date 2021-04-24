package jp.co.ha.root.config;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.web.form.BaseApiRequest;
import jp.co.ha.common.web.form.BaseApiResponse;

/**
 * RootAPIの正常ログ出力クラス<br>
 * ログ出力対象は以下のクラスとする<br>
 * <b>jp.co.ha.root.contents.*.controller.*Controller.*</b>
 *
 * @version 1.0.0
 */
@Aspect
@Component
public class RootApiAspect {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(RootApiAspect.class);

    /**
     * APIのリクエスト/レスポンスログ(正常系)を出力する<br>
     * レスポンスログ(異常系)は以下のクラスのメソッドで行う
     *
     * @param pjp
     *     {@linkplain ProceedingJoinPoint}
     * @return APIレスポンス
     * @throws Throwable
     *     実行時のエラー
     */
    @Around("execution(* jp.co.ha.root.contents.*.controller.*Controller.*(..))")
    public Object outApiLog(ProceedingJoinPoint pjp) throws Throwable {

        // Requestログ出力
        Arrays.stream(pjp.getArgs()).filter(e -> e instanceof BaseApiRequest)
                .forEach(e -> LOG.infoBean(e));

        Object object = pjp.proceed();

        // Responseログ出力
        if (object instanceof BaseApiResponse) {
            LOG.infoBean(object);
        }

        return object;
    }
}
