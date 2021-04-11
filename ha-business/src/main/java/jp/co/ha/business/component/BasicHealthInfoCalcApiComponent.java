package jp.co.ha.business.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.BasicHealthInfoCalcApi;
import jp.co.ha.business.api.node.request.BasicHealthInfoCalcRequest;
import jp.co.ha.business.api.node.response.BaseNodeResponse.Result;
import jp.co.ha.business.api.node.response.BasicHealthInfoCalcResponse;
import jp.co.ha.business.api.node.type.NodeApiType;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.web.api.ApiConnectInfo;

/**
 * 基礎健康情報計算APIコンポーネントクラス<br>
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
    /** 健康情報関連プロパティ */
    @Autowired
    private HealthInfoProperties prop;

    /**
     * 基礎健康情報計算APIを呼び出す
     *
     * @param apiRequest
     *     基礎健康情報計算APIリクエスト
     * @param token
     *     トークン
     * @param seqUserId
     *     ユーザID
     * @param transactionId
     *     トランザクションID
     * @return 基礎健康情報計算APIレスポンス
     * @throws BaseException
     *     API通信に失敗した場合
     */
    public BasicHealthInfoCalcResponse callBasicHealthInfoCalcApi(
            BasicHealthInfoCalcRequest apiRequest, String token, Long seqUserId,
            Long transactionId) throws BaseException {

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(basicHealthInfoCalcApi.getApiName(), seqUserId, transactionId);

        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthinfoNodeApiUrl()
                        + NodeApiType.BASIC.getValue())
                .withHeader(ApiConnectInfo.X_NODE_TOKEN, token);

        BasicHealthInfoCalcResponse apiResponse = basicHealthInfoCalcApi
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
