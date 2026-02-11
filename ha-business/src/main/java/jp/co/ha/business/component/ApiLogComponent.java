package jp.co.ha.business.component;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse;
import jp.co.ha.business.api.root.response.BaseRootApiResponse;
import jp.co.ha.business.api.track.response.BaseTrackApiResponse;
import jp.co.ha.business.db.crud.create.ApiLogCreateService;
import jp.co.ha.business.dto.ApiLogQueuePayload;
import jp.co.ha.common.aws.AwsProperties;
import jp.co.ha.common.aws.AwsSqsComponent;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.common.web.api.BaseApi;
import jp.co.ha.common.web.form.BaseApiRequest;
import jp.co.ha.db.entity.ApiLog;

/**
 * API通信情報の共通Componentクラス
 *
 * @version 1.0.0
 */
@Component
public class ApiLogComponent {

    /** API通信情報作成サービス */
    @Autowired
    private ApiLogCreateService createService;
    /** SQS-Component */
    @Autowired
    private AwsSqsComponent sqs;
    /** AWS設定ファイル情報 */
    @Autowired
    private AwsProperties awsProps;

    /**
     * トランザクションIDを取得
     *
     * @return トランザクションID
     */
    public String transactionId() {
        return UUID.randomUUID().toString();
    }

    /**
     * API通信ログPayloadを返す<br>
     * 健康管理API用
     * 
     * @param api
     *     APIクラス
     * @param connectInfo
     *     API接続情報
     * @param request
     *     APIリクエスト
     * @param response
     *     APIレスポンス
     * @param transactionId
     *     トランザクションID
     * @return API通信ログPayload
     * @throws BaseException
     *     JSONの生成に失敗した場合
     */
    public ApiLogQueuePayload getPayload4AppApi(BaseApi<?, ?> api,
            ApiConnectInfo connectInfo, BaseApiRequest request,
            BaseAppApiResponse response, String transactionId) throws BaseException {

        String apiName = api.getApiName();

        HttpMethod method = api.getHttpMethod();

        URI uri = api.getUri(connectInfo, request);

        String detail = null;
        if (response.getErrorInfo() != null) {
            detail = response.getErrorInfo().getDetail();
        }

        return this.getPayload(connectInfo, request, transactionId, apiName,
                method, uri, detail);
    }

    /**
     * API通信ログPayloadを返す<br>
     * Node API用
     * 
     * @param api
     *     APIクラス
     * @param connectInfo
     *     API接続情報
     * @param request
     *     APIリクエスト
     * @param response
     *     APIレスポンス
     * @param transactionId
     *     トランザクションID
     * @return API通信ログPayload
     * @throws BaseException
     *     JSONの生成に失敗した場合
     */
    public ApiLogQueuePayload getPayload4NodeApi(BaseApi<?, ?> api,
            ApiConnectInfo connectInfo, BaseApiRequest request,
            BaseNodeApiResponse response, String transactionId) throws BaseException {

        String apiName = api.getApiName();

        HttpMethod method = api.getHttpMethod();

        URI uri = api.getUri(connectInfo, request);

        String detail = response.getDetail();

        return this.getPayload(connectInfo, request, transactionId, apiName, method, uri,
                detail);
    }

    /**
     * API通信ログPayloadを返す<br>
     * Track API用
     * 
     * @param api
     *     APIクラス
     * @param connectInfo
     *     API接続情報
     * @param request
     *     APIリクエスト
     * @param response
     *     APIレスポンス
     * @param transactionId
     *     トランザクションID
     * @return API通信ログPayload
     * @throws BaseException
     *     JSONの生成に失敗した場合
     */
    public ApiLogQueuePayload getPayload4TrackApi(BaseApi<?, ?> api,
            ApiConnectInfo connectInfo, BaseApiRequest request,
            BaseTrackApiResponse response, String transactionId) throws BaseException {

        String apiName = api.getApiName();

        HttpMethod method = api.getHttpMethod();

        URI uri = api.getUri(connectInfo, request);

        return this.getPayload(connectInfo, request, transactionId, apiName, method, uri,
                null);
    }

    /**
     * API通信ログPayloadを返す<br>
     * Root API用
     * 
     * @param api
     *     APIクラス
     * @param connectInfo
     *     API接続情報
     * @param request
     *     APIリクエスト
     * @param response
     *     APIレスポンス
     * @param transactionId
     *     トランザクションID
     * @return API通信ログPayload
     * @throws BaseException
     *     JSONの生成に失敗した場合
     */
    public ApiLogQueuePayload getPayload4RootApi(BaseApi<?, ?> api,
            ApiConnectInfo connectInfo, BaseApiRequest request,
            BaseRootApiResponse response, String transactionId) throws BaseException {

        String apiName = api.getApiName();

        HttpMethod method = api.getHttpMethod();

        URI uri = api.getUri(connectInfo, request);

        return this.getPayload(connectInfo, request, transactionId, apiName, method, uri,
                null);
    }

    /**
     * キュー情報を登録
     * 
     * @param payload
     *     Queue情報
     * @throws BaseException
     *     Queueの登録に失敗した場合
     */
    public void registQueue(ApiLogQueuePayload payload)
            throws BaseException {
        sqs.enqueue(awsProps.apiLogQueueName(), payload, payload.transactionId());
    }

    /**
     * API通信ログを作成する
     * 
     * @param payload
     *     API通信ログのSQS-Payload
     */
    public void create(ApiLogQueuePayload payload) {

        ApiLog entity = new ApiLog();

        entity.setTransactionId(payload.transactionId());
        entity.setApiName(payload.apiName());
        entity.setHttpMethod(payload.httpMethod());
        entity.setUrl(payload.url());
        if (HttpMethod.POST == HttpMethod.valueOf(payload.httpMethod())
                || HttpMethod.PUT == HttpMethod.valueOf(payload.httpMethod())) {
            entity.setBody(payload.body());
        }
        entity.setRequestDate(payload.requestDate());

        entity.setHttpStatus(payload.httpStatus().value());
        entity.setDetail(payload.detail());
        entity.setResponseDate(payload.responseDate());
        entity.setDeleteFlag(false);

        // DB登録
        createService.create(entity);
    }

    /**
     * API通信ログPayloadを返す
     * 
     * @param connectInfo
     *     API接続情報
     * @param request
     *     リクエスト情報
     * @param transactionId
     *     トランザクションID
     * @param apiName
     *     API名
     * @param method
     *     HTTPメソッド
     * @param url
     *     リクエストURL
     * @param detail
     *     詳細情報
     * @return API通信ログPayload
     * @throws BaseException
     *     JSONの生成に失敗した場合
     */
    private ApiLogQueuePayload getPayload(ApiConnectInfo connectInfo,
            BaseApiRequest request, String transactionId, String apiName,
            HttpMethod method, URI url, String detail) throws BaseException {

        String body = null;
        if (HttpMethod.POST == method
                || HttpMethod.PUT == method) {
            body = new JsonReader().read(request);
        }

        HttpStatus httpStatus = null;
        if (connectInfo.getHttpStatus() != null) {
            httpStatus = connectInfo.getHttpStatus();
        }

        return new ApiLogQueuePayload(transactionId,
                apiName,
                method.toString(),
                url.toString(),
                body,
                connectInfo.getRequestDate(),
                httpStatus,
                detail,
                connectInfo.getResponseDate());
    }

}
