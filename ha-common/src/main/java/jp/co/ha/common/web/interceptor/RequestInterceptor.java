package jp.co.ha.common.web.interceptor;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.StringJoiner;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.io.encodeanddecode.Sha256HashEncoder;
import jp.co.ha.common.io.encodeanddecode.annotation.Sha256;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.log.MDC;
import jp.co.ha.common.system.SystemMemory;
import jp.co.ha.common.util.MapUtil;
import jp.co.ha.common.util.StringUtil;

/**
 * Requestインターセプター
 *
 * @version 1.0.0
 */
public class RequestInterceptor extends BaseWebInterceptor {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(RequestInterceptor.class);

    /** {@linkplain Sha256HashEncoder} */
    @Sha256
    @Autowired
    private HashEncoder hashEncoder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {

        if (isStaticResource().test(handler)) {
            // 静的リソースの場合は認証不要
            return true;
        }

        // MDCを設定する
        MDC.put("id", StringUtil.getRandamStr(20));
        if (!(handler instanceof HandlerMethod)) {
            LOG.info("[URI=" + request.getRequestURI() + getParameter(request)
                    + ",METHOD=" + request.getMethod()
                    + ",HEADER=" + getHeader(request) + "]"
                    + ",Memory=" + SystemMemory.getInstance().getMemoryUsage());
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        LOG.info("START " + method.getDeclaringClass().getName() + "#" + method.getName()
                + "[URI=" + request.getRequestURI() + getParameter(request)
                + ",METHOD=" + request.getMethod()
                + ",HEADER=" + getHeader(request) + "]"
                + ",Memory=" + SystemMemory.getInstance().getMemoryUsage());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception e) throws Exception {

        if (isStaticResource().test(handler)) {
            // 静的リソースの場合は認証不要
            return;
        }
        if (!(handler instanceof HandlerMethod)) {
            LOG.info("END Memory=" + SystemMemory.getInstance().getMemoryUsage());
        }
        Method method = ((HandlerMethod) handler).getMethod();
        LOG.info("END " + method.getDeclaringClass().getName() + "#" + method.getName()
                + ",Memory=" + SystemMemory.getInstance().getMemoryUsage());

    }

    /**
     * 指定された{@linkplain HttpServletRequest}よりリクエストパラメータを取得する
     *
     * @param request
     *     HttpServletRequest
     * @return リクエストパラメータ
     */
    private String getParameter(HttpServletRequest request) {

        if (MapUtil.isEmpty(request.getParameterMap())) {
            return "";
        }

        StringJoiner sj = new StringJoiner("&");
        for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            StringJoiner innerSj = new StringJoiner(",");
            for (String s : entry.getValue()) {
                innerSj.add(s);
            }
            sj.add(entry.getKey() + "=" + innerSj.toString());
        }
        return "?" + sj.toString();
    }

    /**
     * 指定された{@linkplain HttpServletRequest}よりヘッダー情報を取得する
     *
     * @param request
     *     HttpServletRequest
     * @return ヘッダー情報
     */
    private String getHeader(HttpServletRequest request) {

        StringJoiner headers = new StringJoiner(StringUtil.COMMA);

        // キーの一覧を取得
        Enumeration<String> keys = request.getHeaderNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();

            StringJoiner header = new StringJoiner(StringUtil.COMMA);

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
