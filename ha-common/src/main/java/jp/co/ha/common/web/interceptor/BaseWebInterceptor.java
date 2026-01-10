package jp.co.ha.common.web.interceptor;

import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

/**
 * Webの基底インターセプタ－
 *
 * @version 1.0.0
 */
public abstract class BaseWebInterceptor implements BaseInterceptor {

    /**
     * インターセプターで検査対象のリソースかどうか判定する<br>
     * javascriptなどの静的リソースはtrueを返す
     * 
     * @param handler
     *     リソース
     * @return 判定結果
     */
    protected boolean isStaticResource(Object handler) {
        return handler instanceof ResourceHttpRequestHandler;
    }

}
