package jp.co.ha.common.web.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * API通信のログInterceptor
 * 
 * @version 1.0.0
 */
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

        // リクエストの内容をログに出力
        LOG.debug("Request URI: " + request.getURI());
        LOG.debug("Request Method: " + request.getMethod());
        LOG.debug("Request Headers: " + request.getHeaders());
        LOG.debug("Request Body: " + new String(body, StandardCharsets.UTF_8));

        // レスポンスを取得
        ClientHttpResponse response = execution.execute(request, body);

        // レスポンスの内容をログに出力
        LOG.debug("Response Status Code: " + response.getStatusCode());
        LOG.debug("Response Headers: " + response.getHeaders());

        // ここでResponseBodyを取得するとBaseApi側でresponseが取れなくなるため、以下のレスポンスBody取得処理はコメントアウト
        // InputStreamReader inputStreamReader = new InputStreamReader(
        // response.getBody(), StandardCharsets.UTF_8);
        // BufferedReader br = new BufferedReader(inputStreamReader);
        // String responseBody =
        // br.lines().collect(Collectors.joining("\n"));
        // LOG.debug("Response Body: " + responseBody);

        return response;

    }

}
