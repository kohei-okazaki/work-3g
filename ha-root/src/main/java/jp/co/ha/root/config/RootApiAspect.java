package jp.co.ha.root.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.StringJoiner;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.web.form.BaseApiRequest;
import jp.co.ha.web.form.BaseApiResponse;

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

        Arrays.stream(pjp.getArgs()).filter(e -> e instanceof MultiValueMap)
                .forEach(e -> {

                    StringJoiner sj = new StringJoiner(",");
                    @SuppressWarnings("unchecked")
                    MultiValueMap<String, Object> map = (MultiValueMap<String, Object>) e;
                    for (Entry<String, List<Object>> entry : map.entrySet()) {
                        sj.add("{KEY=" + entry.getKey() + ",VALUE=" + entry.getValue()
                                + "}");
                    }
                    LOG.info(sj.toString());

                });

        Object object = pjp.proceed();

        // Responseログ出力
        if (object instanceof BaseApiResponse) {
            LOG.infoBean(object);
        }

        return object;
    }
}
