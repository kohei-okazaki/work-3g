package jp.co.ha.business.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.TokenApi;
import jp.co.ha.business.api.node.request.TokenApiRequest;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse.Result;
import jp.co.ha.business.api.node.response.TokenApiResponse;
import jp.co.ha.business.api.node.type.NodeApiType;
import jp.co.ha.business.dto.ApiCommunicationDataQueuePayload;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.web.api.ApiConnectInfo;

/**
 * トークン発行APIのComponentクラス<br>
 * APIクラスを直接呼ばずに本クラスを経由して呼び出すこと
 *
 * @version 1.0.0
 */
@Component
public class TokenApiComponent {

    /** トークン発行API */
    @Autowired
    private TokenApi tokenApi;
    /** 健康情報設定ファイル */
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
    @Deprecated
    public TokenApiResponse callTokenApi(Long seqUserId, String transactionId)
            throws BaseException {

        TokenApiRequest request = new TokenApiRequest();
        request.setSeqUserId(seqUserId);
        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthinfoNodeApiUrl()
                        + NodeApiType.TOKEN.getValue());

        TokenApiResponse response = tokenApi.callApi(request, connectInfo);

        ApiCommunicationDataQueuePayload payload = apiCommunicationDataComponent
                .getPayload4NodeApi(tokenApi, connectInfo, request, response,
                        transactionId);
        apiCommunicationDataComponent.registQueue(payload);

        if (Result.SUCCESS != response.getResult()) {
            // Token発行APIの処理が成功以外の場合
            throw new ApiException(BusinessErrorCode.TOKEN_API_CONNECT_ERROR,
                    response.getDetail());
        }

        return response;
    }

}
