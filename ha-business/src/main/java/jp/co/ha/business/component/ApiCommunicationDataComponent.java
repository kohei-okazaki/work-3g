package jp.co.ha.business.component;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse;
import jp.co.ha.business.api.root.response.BaseRootApiResponse;
import jp.co.ha.business.api.track.response.BaseTrackApiResponse;
import jp.co.ha.business.db.crud.create.ApiCommunicationDataCreateService;
import jp.co.ha.business.db.crud.read.ApiCommunicationDataSearchService;
import jp.co.ha.business.db.crud.update.ApiCommunicationDataUpdateService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.web.api.ApiConnectInfo;
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
    /** API通信情報更新サービス */
    @Autowired
    private ApiCommunicationDataUpdateService updateService;
    /** API通信情報検索サービス */
    @Autowired
    private ApiCommunicationDataSearchService searchService;

    /**
     * トランザクションIDを取得
     *
     * @return トランザクションID
     */
    public Long getTransactionId() {
        return searchService.selectLastTransactionId();
    }

    /**
     * API通信情報を作成する
     *
     * @param apiName
     *     API名
     * @param transactionId
     *     トランザクションID
     * @param method
     *     HTTPメソッド
     * @param url
     *     リクエストURL
     * @param request
     *     リクエスト
     * @return API通信情報
     * @throws BaseException
     *     JSONの変換に失敗した場合
     */
    public ApiCommunicationData create(String apiName, Long transactionId,
            HttpMethod method, URI url, BaseApiRequest request) throws BaseException {

        ApiCommunicationData entity = new ApiCommunicationData();
        entity.setTransactionId(transactionId);
        entity.setApiName(apiName);
        entity.setHttpMethod(method.toString());
        entity.setUrl(url.toString());
        if (HttpMethod.POST == method
                || HttpMethod.PUT == method) {
            entity.setBody(new JsonReader().read(request));
        }
        entity.setRequestDate(DateTimeUtil.getSysDate());

        createService.create(entity);

        return entity;
    }

    /**
     * 指定したAPI通信情報を更新する
     *
     * @param apiCommunicationData
     *     {@linkplain ApiCommunicationData}
     * @param connectInfo
     *     {@linkplain ApiConnectInfo}
     * @param response
     *     {@linkplain BaseNodeApiResponse}
     */
    public void update(ApiCommunicationData apiCommunicationData,
            ApiConnectInfo connectInfo, BaseNodeApiResponse response) {

        if (connectInfo.getHttpStatus() != null) {
            apiCommunicationData
                    .setHttpStatus(String.valueOf(connectInfo.getHttpStatus().value()));
        }
        apiCommunicationData.setDetail(response.getDetail());
        apiCommunicationData.setResponseDate(DateTimeUtil.getSysDate());

        updateService.update(apiCommunicationData);
    }

    /**
     * 指定したAPI通信情報を更新する
     *
     * @param apiCommunicationData
     *     {@linkplain ApiCommunicationData}
     * @param connectInfo
     *     {@linkplain ApiConnectInfo}
     * @param response
     *     {@linkplain BaseAppApiResponse}
     */
    public void update(ApiCommunicationData apiCommunicationData,
            ApiConnectInfo connectInfo, BaseAppApiResponse response) {

        if (connectInfo.getHttpStatus() != null) {
            apiCommunicationData
                    .setHttpStatus(String.valueOf(connectInfo.getHttpStatus().value()));
        }
        String detail = null;
        if (response.getErrorInfo() != null) {
            detail = response.getErrorInfo().getDetail();
        }
        apiCommunicationData.setDetail(detail);
        apiCommunicationData.setResponseDate(DateTimeUtil.getSysDate());

        updateService.update(apiCommunicationData);
    }

    /**
     * 指定したAPI通信情報を更新する
     *
     * @param apiCommunicationData
     *     {@linkplain ApiCommunicationData}
     * @param connectInfo
     *     {@linkplain ApiConnectInfo}
     * @param response
     *     {@linkplain BaseRootApiResponse}
     */
    public void update(ApiCommunicationData apiCommunicationData,
            ApiConnectInfo connectInfo, BaseRootApiResponse response) {

        if (connectInfo.getHttpStatus() != null) {
            apiCommunicationData
                    .setHttpStatus(String.valueOf(connectInfo.getHttpStatus().value()));
        }
        apiCommunicationData.setResponseDate(DateTimeUtil.getSysDate());

        updateService.update(apiCommunicationData);
    }

    /**
     * 指定したAPI通信情報を更新する
     *
     * @param apiCommunicationData
     *     {@linkplain ApiCommunicationData}
     * @param connectInfo
     *     {@linkplain ApiConnectInfo}
     * @param response
     *     {@linkplain BaseTrackApiResponse}
     */
    public void update(ApiCommunicationData apiCommunicationData,
            ApiConnectInfo connectInfo, BaseTrackApiResponse response) {

        if (connectInfo.getHttpStatus() != null) {
            apiCommunicationData
                    .setHttpStatus(String.valueOf(connectInfo.getHttpStatus().value()));
        }
        apiCommunicationData.setResponseDate(DateTimeUtil.getSysDate());

        updateService.update(apiCommunicationData);
    }

}
