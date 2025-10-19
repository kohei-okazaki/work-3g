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
import jp.co.ha.business.db.crud.update.ApiCommunicationDataUpdateService;
import jp.co.ha.business.dto.ApiCommunicationDataQueuePayload;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.common.web.form.BaseApiRequest;

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
    /** API通信情報更新サービス */
    @Autowired
    private ApiCommunicationDataUpdateService updateService;
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
     * キュー情報を登録
     * 
     * @param payload
     *     Queue情報
     * @throws BaseException
     *     Queueの登録に失敗した場合
     */
    public void inQueue(ApiCommunicationDataQueuePayload payload) throws BaseException {
        sqs.send(awsProps.getApiCommunicationDataQueueName(), payload,
                payload.getTransactionId());
    }

    /**
     * API通信情報Payloadを返す
     * 
     * @param transactionId
     *     トランザクションID
     * @param apiName
     *     API名
     * @param method
     *     HTTPメソッド
     * @param url
     *     リクエストURL
     * @param request
     *     APIリクエスト
     * @return API通信情報Payload
     * @throws BaseException
     *     JSONの変換に失敗した場合
     */
    public ApiCommunicationDataQueuePayload getPayload(
            String transactionId, String apiName, HttpMethod method, URI url,
            BaseApiRequest request) throws BaseException {

        ApiCommunicationDataQueuePayload payload = new ApiCommunicationDataQueuePayload();
        payload.setTransactionId(transactionId);
        payload.setApiName(apiName);
        payload.setHttpMethod(method.toString());
        payload.setUrl(url.toString());
        if (HttpMethod.POST == method
                || HttpMethod.PUT == method) {
            payload.setBody(new JsonReader().read(request));
        }
        payload.setRequestDate(DateTimeUtil.getSysDate());

        return payload;
    }

    /**
     * API通信情報Payloadに応答項目を設定する
     * 
     * @param payload
     *     API通信情報Payload
     * @param connectInfo
     *     API通信情報
     * @param response
     *     APIレスポンス
     */
    public void fillResponseParam(ApiCommunicationDataQueuePayload payload,
            ApiConnectInfo connectInfo, BaseNodeApiResponse response) {
        if (connectInfo.getHttpStatus() != null) {
            payload
                    .setHttpStatus(String.valueOf(connectInfo.getHttpStatus().value()));
        }
        payload.setDetail(response.getDetail());
        payload.setResponseDate(DateTimeUtil.getSysDate());
    }

    /**
     * API通信情報Payloadに応答項目を設定する
     * 
     * @param payload
     *     API通信情報Payload
     * @param connectInfo
     *     API通信情報
     * @param response
     *     APIレスポンス
     */
    public void fillResponseParam(ApiCommunicationDataQueuePayload payload,
            ApiConnectInfo connectInfo, BaseAppApiResponse response) {
        if (connectInfo.getHttpStatus() != null) {
            payload
                    .setHttpStatus(String.valueOf(connectInfo.getHttpStatus().value()));
        }
        String detail = null;
        if (response.getErrorInfo() != null) {
            detail = response.getErrorInfo().getDetail();
        }
        payload.setDetail(detail);
        payload.setResponseDate(DateTimeUtil.getSysDate());
    }

    /**
     * API通信情報Payloadに応答項目を設定する
     * 
     * @param payload
     *     API通信情報Payload
     * @param connectInfo
     *     API通信情報
     * @param response
     *     APIレスポンス
     */
    public void fillResponseParam(ApiCommunicationDataQueuePayload payload,
            ApiConnectInfo connectInfo, BaseRootApiResponse response) {
        if (connectInfo.getHttpStatus() != null) {
            payload
                    .setHttpStatus(String.valueOf(connectInfo.getHttpStatus().value()));
        }
        payload.setResponseDate(DateTimeUtil.getSysDate());
    }

    /**
     * API通信情報Payloadに応答項目を設定する
     * 
     * @param payload
     *     API通信情報Payload
     * @param connectInfo
     *     API通信情報
     * @param response
     *     APIレスポンス
     */
    public void fillResponseParam(ApiCommunicationDataQueuePayload payload,
            ApiConnectInfo connectInfo, BaseTrackApiResponse response) {
        if (connectInfo.getHttpStatus() != null) {
            payload
                    .setHttpStatus(String.valueOf(connectInfo.getHttpStatus().value()));
        }
        payload.setResponseDate(DateTimeUtil.getSysDate());
    }

}
