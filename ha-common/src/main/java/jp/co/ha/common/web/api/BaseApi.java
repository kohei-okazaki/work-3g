package jp.co.ha.common.web.api;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
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
import jp.co.ha.common.util.DateTimeUtil;
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
     *     APIリクエスト
     * @param connectInfo
     *     API接続に必要な情報
     * @return APIレスポンス
     */
    @SuppressWarnings("unchecked")
    public Rs callApi(Rq request, ApiConnectInfo connectInfo) {

        Rs response = getResponse();
        HttpStatusCode code = null;
        try {

            URI uri = getUri(connectInfo, request);
            LOG.info("====> API名=" + getApiName() + ",HttpMethod=" + getHttpMethod()
                    + ",URL=" + uri.toString());
            LOG.infoBean(request);

            RestTemplate restTemplate = getRestTemplate();
            HttpMethod method = getHttpMethod();
            RequestEntity<?> reqEntity = null;

            switch (method.toString()) {
            case "GET":

                HeadersBuilder<?> getBuilder = RequestEntity.get(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .acceptCharset(connectInfo.getCharset());
                for (Entry<String, String> entry : connectInfo.getHeaderMap()
                        .entrySet()) {
                    getBuilder.header(entry.getKey(), entry.getValue());
                }
                reqEntity = getBuilder.build();
                break;

            case "POST":

                BodyBuilder postBuilder = RequestEntity.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .acceptCharset(connectInfo.getCharset());
                for (Entry<String, String> entry : connectInfo.getHeaderMap()
                        .entrySet()) {
                    postBuilder.header(entry.getKey(), entry.getValue());
                }
                reqEntity = postBuilder.body(request);
                break;

            case "PUT":

                BodyBuilder putBuilder = RequestEntity.put(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .acceptCharset(connectInfo.getCharset());
                for (Map.Entry<String, String> e : connectInfo.getHeaderMap()
                        .entrySet()) {
                    putBuilder.header(e.getKey(), e.getValue());
                }
                reqEntity = putBuilder.body(request);
                break;

            case "DELETE":

                BodyBuilder deleteBuilder = RequestEntity
                        .method(HttpMethod.DELETE, uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .acceptCharset(connectInfo.getCharset());
                for (Entry<String, String> entry : connectInfo.getHeaderMap()
                        .entrySet()) {
                    deleteBuilder.header(entry.getKey(), entry.getValue());
                }
                // ボディを送るなら contentType を付与して body(...)、不要ならbuild()
                if (request == null) {
                    reqEntity = deleteBuilder.build();
                } else {
                    deleteBuilder.contentType(MediaType.APPLICATION_JSON);
                    reqEntity = deleteBuilder.body(request);
                }
                break;

            default:
                throw new IllegalStateException("Unsupported HttpMethod: " + method);
            }

            connectInfo.setRequestDate(DateTimeUtil.getSysDate());
            ResponseEntity<Rs> responseEntity = restTemplate
                    .exchange(reqEntity, (Class<Rs>) response.getClass());
            code = responseEntity.getStatusCode();
            response = responseEntity.getBody();

        } catch (Exception e) {

            String errorMessage = "通信エラーです.";
            bindErrorInfo(response, errorMessage);
            LOG.error(errorMessage, e);

        } finally {

            LOG.infoBean(response);
            LOG.info("<==== API名=" + getApiName() + ", HttpStatusCode=" + code);
            if (code != null) {
                connectInfo.setHttpStatus(HttpStatus.valueOf(code.value()));
                connectInfo.setResponseDate(DateTimeUtil.getSysDate());
            }

        }

        return response;
    }

    /**
     * リクエストURIを返す
     *
     * @param apiConnectInfo
     *     API接続情報
     * @param request
     *     リクエスト情報
     * @return リクエストURI
     */
    public URI getUri(ApiConnectInfo apiConnectInfo, BaseApiRequest request) {

        try {
            String baseUrl = apiConnectInfo.getUrlSupplier().get();
            if (HttpMethod.GET == getHttpMethod()) {
                baseUrl += toQueryParameter(request);
            }
            return new URI(baseUrl);
        } catch (URISyntaxException e) {
            throw new SystemRuntimeException(e);
        }
    }

    /**
     * APIレスポンスを返す
     *
     * @return APIレスポンス
     */
    public abstract Rs getResponse();

    /**
     * HTTPメソッドを返す
     *
     * @return HTTPメソッド
     */
    public abstract HttpMethod getHttpMethod();

    /**
     * API名を返す
     *
     * @return API名
     */
    public abstract String getApiName();

    /**
     * レスポンス情報にエラー情報を割り当てる
     *
     * @param response
     *     レスポンス情報
     * @param errorMessage
     *     エラーメッセージ
     */
    public abstract void bindErrorInfo(Rs response, String errorMessage);

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
