package jp.co.ha.common.web.api;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.StringJoiner;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.RequestEntity.HeadersBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jp.co.ha.common.exception.SystemRuntimeException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.BaseEnum;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.BeanUtil.AccessorType;
import jp.co.ha.common.web.form.BaseApiRequest;
import jp.co.ha.common.web.form.BaseApiResponse;

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

    /**
     * APIを呼び出す
     *
     * @param request
     *     APIリクエストクラス
     * @param apiConnectInfo
     *     API接続に必要な情報
     * @return APIレスポンス
     */
    @SuppressWarnings("unchecked")
    public Rs callApi(Rq request, ApiConnectInfo apiConnectInfo) {

        Rs response = getResponse();
        HttpStatusCode code = null;
        String errorMessage = null;

        try {

            URI uri = getUri(apiConnectInfo, request);
            LOG.info("====> API名=" + getApiName() + ",HttpMethod=" + getHttpMethod()
                    + ",URL=" + uri.toString());
            LOG.infoBean(request);

            RestTemplate restTemplate = getRestTemplate();

            if (HttpMethod.GET == getHttpMethod()) {
                // GET通信の場合
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

            } else {
                // POST/PUT/DELETE通信の場合

                BodyBuilder requestBuilder = RequestEntity.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
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
            }

        } catch (URISyntaxException e) {
            errorMessage = "URLが不正です.";
            bindErrorInfo(response, errorMessage);
            LOG.error(errorMessage, e);
        } catch (HttpStatusCodeException e) {
            code = e.getStatusCode();
            if (code.is4xxClientError()) {
                errorMessage = "Clientエラーです. HttpStatusCode=" + e.getStatusCode();
            } else if (code.is5xxServerError()) {
                errorMessage = "対向Serverエラーです. HttpStatusCode=" + e.getStatusCode();
            }
            bindErrorInfo(response, errorMessage);
            LOG.error(errorMessage, e);
        } catch (Exception e) {
            errorMessage = "通信エラーです.";
            bindErrorInfo(response, errorMessage);
            LOG.error(errorMessage, e);
        } finally {
            LOG.infoBean(response);
            LOG.info("<==== API名=" + getApiName() + ", HttpStatusCode=" + code);
            if (code != null) {
                apiConnectInfo.setHttpStatus(HttpStatus.valueOf(code.value()));
            }
        }

        return response;
    }

    /**
     * APIレスポンスを返す
     *
     * @return APIレスポンス
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
     * @param errorMessage
     *     エラーメッセージ
     */
    protected abstract void bindErrorInfo(Rs response, String errorMessage);

    /**
     * リクエストURIを返す
     *
     * @param apiConnectInfo
     *     API接続情報
     * @param request
     *     リクエスト情報
     * @return リクエストURI
     * @throws URISyntaxException
     *     URLとして正しくない場合
     */
    private URI getUri(ApiConnectInfo apiConnectInfo, BaseApiRequest request)
            throws URISyntaxException {
        String baseUrl = apiConnectInfo.getUrlSupplier().get();
        if (HttpMethod.GET == getHttpMethod()) {
            baseUrl += toQueryParameter(request);
        }
        return new URI(baseUrl);
    }

    /**
     * RestTemplateを返す
     * 
     * @return RestTemplate
     */
    private RestTemplate getRestTemplate() {

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setInterceptors(
                Collections.singletonList(new LoggingInterceptor()));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(
                objectMapper);
        restTemplate.getMessageConverters().add(0, converter);

        return restTemplate;
    }

    /**
     * クエリパラメータを返す
     *
     * @param request
     *     リクエスト情報
     * @return クエリパラメータ
     */
    private static String toQueryParameter(BaseApiRequest request) {

        StringJoiner sj = new StringJoiner("&");
        try {
            for (Field f : request.getClass().getDeclaredFields()) {

                Object value = BeanUtil
                        .getAccessor(f.getName(), request.getClass(), AccessorType.GETTER)
                        .invoke(request);

                if (value == null) {
                    continue;
                }

                String key = f.getAnnotation(JsonProperty.class).value();
                if (value instanceof BaseEnum) {
                    BaseEnum baseEnum = (BaseEnum) value;
                    sj.add(key + "=" + baseEnum.getValue());
                } else {
                    sj.add(key + "=" + value.toString());
                }
            }

            if (sj.length() == 0) {
                return sj.toString();
            }
            return "?" + sj.toString();

        } catch (Exception e) {
            throw new SystemRuntimeException(e);
        }
    }

}
