package jp.co.ha.business.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse;
import jp.co.ha.business.api.root.response.BaseRootApiResponse;
import jp.co.ha.business.api.track.response.BaseTrackApiResponse;
import jp.co.ha.business.db.crud.create.ApiCommunicationDataCreateService;
import jp.co.ha.business.db.crud.create.impl.ApiCommunicationDataCreateServiceImpl;
import jp.co.ha.business.db.crud.read.ApiCommunicationDataSearchService;
import jp.co.ha.business.db.crud.read.impl.ApiCommunicationDataSearchServiceImpl;
import jp.co.ha.business.db.crud.update.ApiCommunicationDataUpdateService;
import jp.co.ha.business.db.crud.update.impl.ApiCommunicationDataUpdateServiceImpl;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.db.entity.ApiCommunicationData;

/**
 * API通信情報の共通Componentクラス
 *
 * @version 1.0.0
 */
@Component
public class ApiCommunicationDataComponent {

    /** {@linkplain ApiCommunicationDataCreateServiceImpl} */
    @Autowired
    private ApiCommunicationDataCreateService createService;
    /** {@linkplain ApiCommunicationDataUpdateServiceImpl} */
    @Autowired
    private ApiCommunicationDataUpdateService updateService;
    /** {@linkplain ApiCommunicationDataSearchServiceImpl} */
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
     * @param seqUserId
     *     ユーザID
     * @param transactionId
     *     トランザクションID
     * @return API通信情報
     */
    public ApiCommunicationData create(String apiName, Long seqUserId,
            Long transactionId) {

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
     *     {@linkplain BaseNodeApiResponse}
     */
    public void update(ApiCommunicationData apiCommunicationData,
            ApiConnectInfo connectInfo, BaseNodeApiResponse response) {

        apiCommunicationData
                .setHttpStatus(String.valueOf(connectInfo.getHttpStatus().value()));
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
     *     {@linkplain BaseAppApiResponse}
     */
    public void update(ApiCommunicationData apiCommunicationData,
            ApiConnectInfo connectInfo, BaseAppApiResponse response) {

        if (connectInfo.getHttpStatus() != null) {
            apiCommunicationData
                    .setHttpStatus(String.valueOf(connectInfo.getHttpStatus().value()));
        }
        apiCommunicationData.setResult(response.getResultType().getValue());
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

        apiCommunicationData
                .setHttpStatus(String.valueOf(connectInfo.getHttpStatus().value()));
        apiCommunicationData.setResult(response.getResult().getValue());
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
        apiCommunicationData.setResult(response.getResult().getValue());
        apiCommunicationData.setResponseDate(DateTimeUtil.getSysDate());

        updateService.update(apiCommunicationData);
    }

}
