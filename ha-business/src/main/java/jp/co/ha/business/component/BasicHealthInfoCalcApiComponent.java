package jp.co.ha.business.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.BasicHealthInfoCalcApi;
import jp.co.ha.business.api.node.request.BasicHealthInfoCalcApiRequest;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse.Result;
import jp.co.ha.business.api.node.response.BasicHealthInfoCalcApiResponse;
import jp.co.ha.business.api.node.type.NodeApiType;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.db.entity.ApiCommunicationData;

/**
 * 基礎健康情報計算APIComponentクラス<br>
 * APIクラスを直接呼ばずに本クラスを経由して呼び出すこと
 *
 * @version 1.0.0
 */
@Component
public class BasicHealthInfoCalcApiComponent {

    /** API通信情報Component */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;
    /** 基礎健康情報計算API */
    @Autowired
    private BasicHealthInfoCalcApi basicHealthInfoCalcApi;
    /** 健康情報設定ファイル */
    @Autowired
    private HealthInfoProperties prop;

    /**
     * 基礎健康情報計算APIを呼び出す
     *
     * @param apiRequest
     *     基礎健康情報計算APIリクエスト
     * @param transactionId
     *     トランザクションID
     * @return 基礎健康情報計算APIレスポンス
     * @throws BaseException
     *     API通信に失敗した場合
     */
    public BasicHealthInfoCalcApiResponse callBasicHealthInfoCalcApi(
            BasicHealthInfoCalcApiRequest apiRequest, Long transactionId)
            throws BaseException {

        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthinfoNodeApiUrl()
                        + NodeApiType.BASIC.getValue());

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(basicHealthInfoCalcApi.getApiName(), transactionId,
                        basicHealthInfoCalcApi.getHttpMethod(),
                        basicHealthInfoCalcApi.getUri(connectInfo, apiRequest),
                        apiRequest);

        BasicHealthInfoCalcApiResponse apiResponse = basicHealthInfoCalcApi
                .callApi(apiRequest, connectInfo);

        // API通信情報を更新
        apiCommunicationDataComponent.update(apiCommunicationData, connectInfo,
                apiResponse);

        if (Result.SUCCESS != apiResponse.getResult()) {
            // 基礎健康情報計算APIの処理が成功以外の場合
            throw new ApiException(BusinessErrorCode.BASIC_API_CONNECT_ERROR,
                    apiResponse.getDetail());
        }

        return apiResponse;

    }

    /**
     * 基礎健康情報計算APIを呼び出す
     *
     * @param apiRequest
     *     基礎健康情報計算APIリクエスト
     * @param token
     *     トークン
     * @param transactionId
     *     トランザクションID
     * @return 基礎健康情報計算APIレスポンス
     * @throws BaseException
     *     API通信に失敗した場合
     */
    @Deprecated
    public BasicHealthInfoCalcApiResponse callBasicHealthInfoCalcApi(
            BasicHealthInfoCalcApiRequest apiRequest, String token, Long transactionId)
            throws BaseException {

        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthinfoNodeApiUrl()
                        + NodeApiType.BASIC.getValue())
                .withHeader(ApiConnectInfo.X_NODE_TOKEN, token);

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(basicHealthInfoCalcApi.getApiName(), transactionId,
                        basicHealthInfoCalcApi.getHttpMethod(),
                        basicHealthInfoCalcApi.getUri(connectInfo, apiRequest),
                        apiRequest);

        BasicHealthInfoCalcApiResponse apiResponse = basicHealthInfoCalcApi
                .callApi(apiRequest, connectInfo);

        // API通信情報を更新
        apiCommunicationDataComponent.update(apiCommunicationData, connectInfo,
                apiResponse);

        if (Result.SUCCESS != apiResponse.getResult()) {
            // 基礎健康情報計算APIの処理が成功以外の場合
            throw new ApiException(BusinessErrorCode.BASIC_API_CONNECT_ERROR,
                    apiResponse.getDetail());
        }

        return apiResponse;

    }

}
