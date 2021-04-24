package jp.co.ha.business.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.TokenApi;
import jp.co.ha.business.api.node.request.TokenApiRequest;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse.Result;
import jp.co.ha.business.api.node.response.TokenApiResponse;
import jp.co.ha.business.api.node.type.NodeApiType;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.db.entity.ApiCommunicationData;

/**
 * トークン発行APIのコンポーネントクラス<br>
 * APIクラスを直接呼ばずに本クラスを経由して呼び出すこと
 *
 * @version 1.0.0
 */
@Component
public class TokenApiComponent {

    /** トークン発行API */
    @Autowired
    private TokenApi tokenApi;
    /** 健康情報関連プロパティ */
    @Autowired
    private HealthInfoProperties prop;
    /** API通信情報Component */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;

    /**
     * Token発行APIを呼び出す
     *
     * @param seqUserId
     *     ユーザID
     * @param transactionId
     *     トランザクションID
     * @return Token発行APIのレスポンス
     * @throws BaseException
     *     API通信に失敗した場合
     */
    public TokenApiResponse callTokenApi(Long seqUserId, Long transactionId)
            throws BaseException {

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(tokenApi.getApiName(), seqUserId, transactionId);

        TokenApiRequest request = new TokenApiRequest();
        request.setSeqUserId(seqUserId);
        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthinfoNodeApiUrl()
                        + NodeApiType.TOKEN.getValue());
        TokenApiResponse response = tokenApi.callApi(request, connectInfo);

        // API通信情報を更新
        apiCommunicationDataComponent.update(apiCommunicationData,
                connectInfo, response);

        if (Result.SUCCESS != response.getResult()) {
            // Token発行APIの処理が成功以外の場合
            throw new ApiException(BusinessErrorCode.TOKEN_API_CONNECT_ERROR,
                    response.getDetail());
        }

        return response;
    }

}
