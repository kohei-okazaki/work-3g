package jp.co.ha.business.component;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.aws.AwsProperties;
import jp.co.ha.business.api.aws.AwsSqsComponent;
import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse;
import jp.co.ha.business.api.root.response.BaseRootApiResponse;
import jp.co.ha.business.api.track.response.BaseTrackApiResponse;
import jp.co.ha.business.db.crud.create.ApiCommunicationDataCreateService;
import jp.co.ha.business.dto.ApiCommunicationDataQueuePayload;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.common.web.api.BaseApi;
import jp.co.ha.common.web.form.BaseApiRequest;
import jp.co.ha.db.entity.ApiCommunicationData;

/**
 * API通信情報の共通Componentクラス
 *
 * @version 1.0.0
 */
@Component
public class ApiCommunicationDataComponent {

    /** API通信情報作成サービス */
    @Autowired
    private ApiCommunicationDataCreateService createService;
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
    public String getTransactionId() {
        return UUID.randomUUID().toString();
    }

    /**
     * API通信情報Payloadを返す<br>
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
     * @return API通信情報Payload
     * @throws BaseException
     *     JSONの生成に失敗した場合
     */
    public ApiCommunicationDataQueuePayload getPayload4AppApi(BaseApi<?, ?> api,
            ApiConnectInfo connectInfo, BaseApiRequest request,
            BaseAppApiResponse response, String transactionId)
            throws BaseException {

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
     * API通信情報Payloadを返す<br>
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
     * @return API通信情報Payload
     * @throws BaseException
     *     JSONの生成に失敗した場合
     */
    public ApiCommunicationDataQueuePayload getPayload4NodeApi(BaseApi<?, ?> api,
            ApiConnectInfo connectInfo, BaseApiRequest request,
            BaseNodeApiResponse response, String transactionId)
            throws BaseException {

        String apiName = api.getApiName();

        HttpMethod method = api.getHttpMethod();

        URI uri = api.getUri(connectInfo, request);

        String detail = response.getDetail();

        return this.getPayload(connectInfo, request, transactionId, apiName, method, uri,
                detail);
    }

    /**
     * API通信情報Payloadを返す<br>
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
     * @return API通信情報Payload
     * @throws BaseException
     *     JSONの生成に失敗した場合
     */
    public ApiCommunicationDataQueuePayload getPayload4TrackApi(BaseApi<?, ?> api,
            ApiConnectInfo connectInfo, BaseApiRequest request,
            BaseTrackApiResponse response, String transactionId)
            throws BaseException {

        String apiName = api.getApiName();

        HttpMethod method = api.getHttpMethod();

        URI uri = api.getUri(connectInfo, request);

        return this.getPayload(connectInfo, request, transactionId, apiName, method, uri,
                null);
    }

    /**
     * API通信情報Payloadを返す<br>
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
     * @return API通信情報Payload
     * @throws BaseException
     *     JSONの生成に失敗した場合
     */
    public ApiCommunicationDataQueuePayload getPayload4RootApi(BaseApi<?, ?> api,
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
    public void registQueue(ApiCommunicationDataQueuePayload payload)
            throws BaseException {
        sqs.enqueue(awsProps.getApiCommunicationDataQueueName(), payload,
                payload.getTransactionId());
    }

    /**
     * API通信情報を作成する
     * 
     * @param payload
     *     API通信情報のSQS-Payload
     */
    public void create(ApiCommunicationDataQueuePayload payload) {

        ApiCommunicationData entity = new ApiCommunicationData();

        entity.setTransactionId(payload.getTransactionId());
        entity.setApiName(payload.getApiName());
        entity.setHttpMethod(payload.getHttpMethod());
        entity.setUrl(payload.getUrl());
        if (HttpMethod.POST == HttpMethod.valueOf(payload.getHttpMethod())
                || HttpMethod.PUT == HttpMethod.valueOf(payload.getHttpMethod())) {
            entity.setBody(payload.getBody());
        }
        entity.setRequestDate(payload.getRequestDate());

        entity.setHttpStatus(payload.getHttpStatus());
        entity.setDetail(payload.getDetail());
        entity.setResponseDate(payload.getResponseDate());

        // DB登録
        createService.create(entity);
    }

    /**
     * API通信情報Payloadを返す
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
     * @return API通信情報Payload
     * @throws BaseException
     *     JSONの生成に失敗した場合
     */
    private ApiCommunicationDataQueuePayload getPayload(ApiConnectInfo connectInfo,
            BaseApiRequest request, String transactionId, String apiName,
            HttpMethod method, URI url, String detail) throws BaseException {

        ApiCommunicationDataQueuePayload payload = new ApiCommunicationDataQueuePayload();

        payload.setTransactionId(transactionId);
        payload.setApiName(apiName);
        payload.setHttpMethod(method.toString());
        payload.setUrl(url.toString());
        if (HttpMethod.POST == method
                || HttpMethod.PUT == method) {
            payload.setBody(new JsonReader().read(request));
        }
        payload.setRequestDate(connectInfo.getRequestDate());

        if (connectInfo.getHttpStatus() != null) {
            payload.setHttpStatus(String.valueOf(connectInfo.getHttpStatus().value()));
        }
        payload.setDetail(detail);
        payload.setResponseDate(connectInfo.getResponseDate());

        return payload;
    }

}
