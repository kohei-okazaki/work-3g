package jp.co.ha.web.interceptor;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.method.HandlerMethod;

import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.log.MDC;
import jp.co.ha.common.util.StringUtil;

/**
 * Requestインターセプター
 *
 * @version 1.0.0
 */
public class RequestInterceptor extends BaseWebInterceptor {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(RequestInterceptor.class);

    /** ハッシュ生成関数 */
    @Autowired
    @Qualifier("sha256HashEncoder")
    private HashEncoder hashEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {

        if (isStaticResource().test(handler)) {
            // 静的リソースの場合は認証不要
            return true;
        }

        // MDCを設定する
        MDC.put("id", StringUtil.getRandamStr(20));
        Method method = ((HandlerMethod) handler).getMethod();
        LOG.info("START " + method.getDeclaringClass().getName() + "#" + method.getName()
                + "[URI=" + request.getRequestURI()
                + ",METHOD=" + request.getMethod()
                + ",HEADER=" + getHeader(request) + "]");

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception e) throws Exception {

        if (isStaticResource().test(handler)) {
            // 静的リソースの場合は認証不要
            return;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        LOG.info("END " + method.getDeclaringClass().getName() + "#" + method.getName());

    }

    /**
     * 指定された{@linkplain HttpServletRequest}よりヘッダー情報を取得する
     *
     * @param request
     *     HttpServletRequest
     * @return ヘッダー情報
     */
    private String getHeader(HttpServletRequest request) {

        StringJoiner headers = new StringJoiner(",");

        // キーの一覧を取得
        Enumeration<String> keys = request.getHeaderNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();

            StringJoiner header = new StringJoiner(",");

            // 値の一覧を取得
            Enumeration<String> values = request.getHeaders(key);
            while (values.hasMoreElements()) {
                String value = values.nextElement();
                header.add(value);
            }
            headers.add(header.toString());
        }

        return headers.toString();
    }
}
