package jp.co.ha.business.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.response.BaseNodeResponse;
import jp.co.ha.business.db.crud.create.ApiCommunicationDataCreateService;
import jp.co.ha.business.db.crud.read.ApiCommunicationDataSearchService;
import jp.co.ha.business.db.crud.update.ApiCommunicationDataUpdateService;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.web.api.ApiConnectInfo;
import jp.co.ha.web.form.BaseRestApiResponse;

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
    public Integer getTransactionId() {
        return searchService.selectLastTransactionId();
    }

    /**
     * API通信情報を作成する
     *
     * @param apiName
     *     API名
     * @param seqUserId
     *     ユーザID
     * @param transactionId
     *     トランザクションID
     * @return API通信情報
     */
    public ApiCommunicationData create(String apiName, Integer seqUserId,
            Integer transactionId) {

        ApiCommunicationData entity = new ApiCommunicationData();
        entity.setTransactionId(transactionId);
        entity.setApiName(apiName);
        entity.setSeqUserId(seqUserId);
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
     *     {@linkplain BaseNodeResponse}
     */
    public void update(ApiCommunicationData apiCommunicationData,
            ApiConnectInfo connectInfo, BaseNodeResponse response) {

        apiCommunicationData.setHttpStatus(String.valueOf(connectInfo.getHttpStatus()));
        apiCommunicationData.setResult(response.getResult().getValue());
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
     *     {@linkplain BaseRestApiResponse}
     */
    public void update(ApiCommunicationData apiCommunicationData,
            ApiConnectInfo connectInfo, BaseRestApiResponse response) {

        apiCommunicationData.setHttpStatus(String.valueOf(connectInfo.getHttpStatus()));
        apiCommunicationData.setResult(response.getResultType().getValue());
        String detail = null;
        if (response.getErrorInfo() != null) {
            detail = response.getErrorInfo().getDetail();
        }
        apiCommunicationData.setDetail(detail);
        apiCommunicationData.setResponseDate(DateTimeUtil.getSysDate());

        updateService.update(apiCommunicationData);
    }
}
