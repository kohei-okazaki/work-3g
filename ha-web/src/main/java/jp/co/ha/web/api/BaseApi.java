package jp.co.ha.web.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.RequestEntity.HeadersBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.web.form.BaseApiRequest;
import jp.co.ha.web.form.BaseApiResponse;

/**
 * 基底API通信クラス<br>
 * 外部サーバとのHTTP通信を行うAPIは本クラスを継承すること<br>
 * 呼び出し元からは基本的に以下の手順でAPI通信をする<br>
 *
 * <pre>
 * ApiConnectInfo apiConnectInfo = new ApiConnectInfo();
 * apiConnectInfo.setUrlSupplier(() -> リクエストURL);
 * XXXAPIのインスタンス.callApi(XXXAPIリクエスト, apiConnectInfo);
 * </pre>
 *
 * リクエストヘッダーを任意で設定したい場合、以下の方法で設定する<br>
 *
 * <pre>
 * ApiConnectInfo apiConnectInfo = new ApiConnectInfo();
 * apiConnectInfo.addHeader("Header-Key1", "Header-Value1");
 * apiConnectInfo.addHeader("Header-Key2", "Header-Value2");
 * apiConnectInfo.addHeader("Header-Key3", "Header-Value3");
 * apiConnectInfo.setUrlSupplier(() -> リクエストURL);
 * XXXAPIのインスタンス.callApi(XXXAPIリクエスト, apiConnectInfo);
 * </pre>
 *
 * @param <Rq>
 *     リクエスト
 * @param <Rs>
 *     レスポンス
 * @version 1.0.0
 */
public abstract class BaseApi<Rq extends BaseApiRequest, Rs extends BaseApiResponse> {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(BaseApi.class);

    /** APIを通信するためのClient */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * APIを呼び出す
     *
     * @param request
     *     APIリクエストクラス
     * @param apiConnectInfo
     *     API接続に必要な情報
     * @return APIレスポンス
     * @throws BaseException
     */
    @SuppressWarnings("unchecked")
    public Rs callApi(Rq request, ApiConnectInfo apiConnectInfo) throws BaseException {

        Rs response = getResponse();
        HttpStatus code = null;

        try {

            // URIを生成
            URI uri = getUri(apiConnectInfo);
            LOG.info("====> API名=" + getApiName() + ",URL=" + uri.toString());
            LOG.infoBean(request);

            if (HttpMethod.POST == getHttpMethod()) {
                // POST通信の場合

                BodyBuilder requestBuilder = RequestEntity.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .acceptCharset(apiConnectInfo.getCharset());

                // ヘッダーの設定
                for (Entry<String, String> entry : apiConnectInfo.getHeaderMap()
                        .entrySet()) {
                    requestBuilder.header(entry.getKey(), entry.getValue());
                }
                RequestEntity<Rq> reqEntity = requestBuilder.body(request);

                // API通信
                ResponseEntity<Rs> responseEntity = (ResponseEntity<Rs>) restTemplate
                        .exchange(reqEntity, response.getClass());

                code = responseEntity.getStatusCode();
                response = responseEntity.getBody();

            } else {
                // GET通信

                HeadersBuilder<?> requestBuilder = RequestEntity.get(uri)
                        .acceptCharset(apiConnectInfo.getCharset());

                // ヘッダーの設定
                for (Entry<String, String> entry : apiConnectInfo.getHeaderMap()
                        .entrySet()) {
                    requestBuilder.header(entry.getKey(), entry.getValue());
                }
                RequestEntity<Void> reqEntity = requestBuilder.build();

                ResponseEntity<Rs> responseEntity = (ResponseEntity<Rs>) restTemplate
                        .exchange(reqEntity, response.getClass());

                code = responseEntity.getStatusCode();
                response = responseEntity.getBody();

            }
        } catch (HttpClientErrorException e) {
            code = e.getStatusCode();
            bindErrorInfo(response);
        } catch (HttpServerErrorException e) {
            code = e.getStatusCode();
            bindErrorInfo(response);
        } catch (Exception e) {
            bindErrorInfo(response);
            throw new ApiException(CommonErrorCode.UNEXPECTED_ERROR, "対向サーバでエラーが発生しました.",
                    e);
        } finally {
            LOG.infoBean(response);
            Integer intCode = (code == null) ? null : code.value();
            LOG.info("<==== API名=" + getApiName() + " HttpStatusCode=" + intCode);
            apiConnectInfo.setHttpStatus(intCode);
        }

        return response;
    }

    /**
     * APIレスポンスクラスを返す
     *
     * @return Node APIレスポンス
     */
    protected abstract Rs getResponse();

    /**
     * HTTPメソッドを返す
     *
     * @return HTTPメソッド
     */
    protected abstract HttpMethod getHttpMethod();

    /**
     * API名を返す
     *
     * @return API名
     */
    protected abstract String getApiName();

    /**
     * レスポンス情報にエラー情報を割り当てる
     *
     * @param response
     *     レスポンス情報
     */
    protected abstract void bindErrorInfo(Rs response);

    /**
     * リクエストURIを返す
     *
     * @param apiConnectInfo
     *     API接続情報
     * @return リクエストURI
     * @throws URISyntaxException
     *     URLとして正しくない場合
     */
    private static URI getUri(ApiConnectInfo apiConnectInfo) throws URISyntaxException {
        String baseUrl = apiConnectInfo.getUrlSupplier().get();
        baseUrl += apiConnectInfo.getQureyString();
        return new URI(baseUrl);
    }

}
