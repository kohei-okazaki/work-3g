package jp.co.ha.business.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.BreathingCapacityCalcApi;
import jp.co.ha.business.api.node.TokenApi;
import jp.co.ha.business.api.node.request.BreathingCapacityCalcRequest;
import jp.co.ha.business.api.node.request.TokenRequest;
import jp.co.ha.business.api.node.response.BaseNodeResponse.Result;
import jp.co.ha.business.api.node.response.BreathingCapacityCalcResponse;
import jp.co.ha.business.api.node.response.TokenResponse;
import jp.co.ha.business.api.node.type.NodeApiType;
import jp.co.ha.business.dto.BreathingCapacityDto;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.web.api.ApiConnectInfo;

/**
 * 肺活量計算Component
 *
 * @version 1.0.0
 */
@Component
public class BreathingCapacityComponent {

    /** API通信情報Component */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;
    /** トークン発行API */
    @Autowired
    private TokenApi tokenApi;
    /** 肺活量計算API */
    @Autowired
    private BreathingCapacityCalcApi breathingCapacityCalcApi;
    /** 健康情報関連プロパティ */
    @Autowired
    private HealthInfoProperties prop;

    /**
     * 肺活量計算処理を行う
     *
     * @param dto
     *     {@linkplain BreathingCapacityDto}
     * @param seqUserId
     *     ユーザID
     * @return {@linkplain BreathingCapacityDto}
     * @throws BaseException
     *     肺活量計算APIの処理が成功以外
     */
    public BreathingCapacityDto calc(BreathingCapacityDto dto, Integer seqUserId)
            throws BaseException {

        // API通信情報.トランザクションIDを採番
        Integer transactionId = apiCommunicationDataComponent.getTransactionId();

        TokenResponse tokenApiResponse = callTokenApi(seqUserId, transactionId);

        BreathingCapacityCalcResponse apiResponse = callBreathingCapacityCalcApi(dto,
                tokenApiResponse.getToken(), seqUserId, transactionId);
        BeanUtil.copy(apiResponse.getBreathingCapacityCalcResult(), dto);

        return dto;
    }

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
    private TokenResponse callTokenApi(Integer seqUserId, Integer transactionId)
            throws BaseException {

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(tokenApi.getApiName(), seqUserId, transactionId);

        TokenRequest request = new TokenRequest();
        request.setSeqUserId(seqUserId);
        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthinfoNodeApiUrl()
                        + NodeApiType.TOKEN.getValue());
        TokenResponse response = tokenApi.callApi(request, connectInfo);

        // API通信情報を更新
        apiCommunicationDataComponent.update(apiCommunicationData, connectInfo, response);

        if (Result.SUCCESS != response.getResult()) {
            // Token発行APIの処理が成功以外の場合
            throw new ApiException(BusinessErrorCode.TOKEN_API_CONNECT_ERROR,
                    response.getDetail());
        }

        return response;
    }

    /**
     * 肺活量計算APIを呼び出す
     *
     * @param dto
     *     肺活量計算Dto
     * @param token
     *     トークン
     * @param seqUserId
     *     ユーザID
     * @param transactionId
     *     トランザクションID
     * @return 肺活量計算APIレスポンス
     * @throws BaseException
     *     API通信に失敗した場合
     */
    private BreathingCapacityCalcResponse callBreathingCapacityCalcApi(
            BreathingCapacityDto dto, String token, Integer seqUserId,
            Integer transactionId) throws BaseException {

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(breathingCapacityCalcApi.getApiName(), seqUserId, transactionId);

        BreathingCapacityCalcRequest request = new BreathingCapacityCalcRequest();
        BeanUtil.copy(dto, request);

        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthinfoNodeApiUrl()
                        + NodeApiType.BREATHING_CAPACITY.getValue())
                .withHeader(ApiConnectInfo.X_NODE_TOKEN, token);

        BreathingCapacityCalcResponse response = breathingCapacityCalcApi.callApi(request,
                connectInfo);

        // API通信情報を更新
        apiCommunicationDataComponent.update(apiCommunicationData,
                connectInfo, response);

        if (Result.SUCCESS != response.getResult()) {
            // 肺活量計算APIの処理が成功以外の場合
            throw new ApiException(BusinessErrorCode.BREATHING_API_CONNECT_ERROR,
                    response.getDetail());
        }

        return response;
    }

}
