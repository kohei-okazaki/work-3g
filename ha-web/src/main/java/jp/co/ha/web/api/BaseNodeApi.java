package jp.co.ha.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.BaseEnum;
import jp.co.ha.web.form.BaseNodeRequest;
import jp.co.ha.web.form.BaseNodeResponse;

/**
 * 基底NodeAPI<br>
 * ha-nodeとの通信で扱うAPIは本クラスを継承すること
 *
 * @param <Rq>
 *     リクエスト
 * @param <Rs>
 *     レスポンス
 * @version 1.0.0
 */
public abstract class BaseNodeApi<Rq extends BaseNodeRequest, Rs extends BaseNodeResponse> {

    /** LOG */
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /** APIを通信するためのClient */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Node APIを実施する
     *
     * @param request
     *     NodeAPIリクエスト
     * @return NodeAPIレスポンス
     */
    @SuppressWarnings("unchecked")
    public Rs callApi(Rq request) {

        LOG.info(" ==> " + getNodeApiType().getName());
        LOG.infoRes(request);

        Rs response = getResponse();
        HttpStatus code = HttpStatus.OK;
        try {

            if (HttpMethod.POST == getHttpMethod()) {

                // POST通信
                response = (Rs) restTemplate.postForObject(getUrl(), request,
                        response.getClass());
            } else {
                response = (Rs) restTemplate.getForObject(getUrl(),
                        response.getClass());

            }

        } catch (HttpClientErrorException e) {
            code = e.getStatusCode();
            LOG.error("APIの通信に失敗しました. HTTPステータスコード=" + code.value(), e);
        } catch (HttpServerErrorException e) {
            code = e.getStatusCode();
            LOG.error("APIの通信に失敗しました. HTTPステータスコード=" + code.value(), e);
        } catch (Exception e) {
            LOG.error("APIの通信に失敗しました.", e);
        } finally {
            LOG.infoRes(response);
            LOG.info(" <== " + getNodeApiType().getName() + " HTTPステータスコード="
                    + code.value());
        }

        return response;
    }

    /**
     * Node APIレスポンスクラスを返す
     *
     * @return Node APIレスポンス
     */
    protected abstract Rs getResponse();

    /**
     * リクエストURLを返す
     *
     * @return リクエストURL
     */
    protected abstract String getUrl();

    /**
     * HTTPメソッドを返す
     *
     * @return HTTPメソッド
     */
    protected abstract HttpMethod getHttpMethod();

    /**
     * Node API種別を返す
     *
     * @return Node API種別
     */
    protected abstract NodeApiType getNodeApiType();

    /**
     * API種別<br>
     * <ul>
     * <li>BASIC：基礎健康情報計算API</li>
     * <li>CALORIE：カロリー計算API</li>
     * </ul>
     *
     * @version 1.0.0
     */
    public static enum NodeApiType implements BaseEnum {

        /** 基礎健康情報計算API */
        BASIC("healthinfo", "基礎健康情報計算API"),
        /** カロリー計算API */
        CALORIE("calorie", "カロリー計算API");

        /** 値 */
        private String value;
        /** API名 */
        private String name;

        /**
         * コンストラクタ
         *
         * @param value
         *     値
         * @param name
         *     名前
         */
        private NodeApiType(String value, String name) {
            this.value = value;
            this.name = name;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        /**
         * nameを返す
         *
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
         * @param value
         *     値
         * @return NodeApiType
         */
        public static NodeApiType of(String value) {
            return BaseEnum.of(NodeApiType.class, value);
        }
    }
}
