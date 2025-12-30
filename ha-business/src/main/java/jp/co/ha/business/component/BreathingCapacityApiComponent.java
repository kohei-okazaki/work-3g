package jp.co.ha.business.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.BreathingCapacityCalcApi;
import jp.co.ha.business.api.node.request.BreathingCapacityCalcApiRequest;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse.Result;
import jp.co.ha.business.api.node.response.BreathingCapacityCalcApiResponse;
import jp.co.ha.business.api.node.response.TokenApiResponse;
import jp.co.ha.business.api.node.type.NodeApiType;
import jp.co.ha.business.dto.ApiLogQueuePayload;
import jp.co.ha.business.dto.BreathingCapacityDto;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.api.ApiConnectInfo;

/**
 * 肺活量計算APIのComponentクラス<br>
 * APIクラスを直接呼ばずに本クラスを経由して呼び出すこと
 *
 * @version 1.0.0
 */
@Component
public class BreathingCapacityApiComponent {

    /** API通信ログComponent */
    @Autowired
    private ApiLogComponent apiLogComponent;
    /** トークン発行APIComponent */
    @Autowired
    private TokenApiComponent tokenApiComponent;
    /** 肺活量計算API */
    @Autowired
    private BreathingCapacityCalcApi api;
    /** 健康情報設定ファイル */
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
    public BreathingCapacityDto calc(BreathingCapacityDto dto, Long seqUserId)
            throws BaseException {

        // API通信情報.トランザクションIDを採番
        String transactionId = apiLogComponent.getTransactionId();

        BreathingCapacityCalcApiResponse apiResponse;
        if (prop.isHealthinfoNodeApiMigrateFlg()) {

            // 肺活量計算API実施
            apiResponse = callBreathingCapacityCalcApi(dto, transactionId);

        } else {

            // トークン発行API実施
            @SuppressWarnings("deprecation")
            TokenApiResponse tokenApiResponse = tokenApiComponent.callTokenApi(seqUserId,
                    transactionId);

            // 肺活量計算API実施
            apiResponse = callBreathingCapacityCalcApi(dto,
                    tokenApiResponse.getToken(), transactionId);
        }

        BeanUtil.copy(apiResponse.getBreathingCapacityCalcResult(), dto);

        return dto;
    }

    /**
     * 肺活量計算APIを呼び出す
     *
     * @param dto
     *     肺活量計算Dto
     * @param transactionId
     *     トランザクションID
     * @return 肺活量計算APIレスポンス
     * @throws BaseException
     *     API通信に失敗した場合
     */
    private BreathingCapacityCalcApiResponse callBreathingCapacityCalcApi(
            BreathingCapacityDto dto, String transactionId) throws BaseException {

        BreathingCapacityCalcApiRequest request = new BreathingCapacityCalcApiRequest();
        BeanUtil.copy(dto, request);

        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthinfoNodeApiUrl()
                        + NodeApiType.BREATHING_CAPACITY.getValue());

        BreathingCapacityCalcApiResponse response = api.callApi(request, connectInfo);

        ApiLogQueuePayload payload = apiLogComponent
                .getPayload4NodeApi(api, connectInfo, request, response, transactionId);
        apiLogComponent.registQueue(payload);

        if (Result.SUCCESS != response.getResult()) {
            // 肺活量計算APIの処理が成功以外の場合
            throw new ApiException(BusinessErrorCode.BREATHING_API_CONNECT_ERROR,
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
     * @param transactionId
     *     トランザクションID
     * @return 肺活量計算APIレスポンス
     * @throws BaseException
     *     API通信に失敗した場合
     */
    @Deprecated
    private BreathingCapacityCalcApiResponse callBreathingCapacityCalcApi(
            BreathingCapacityDto dto, String token, String transactionId)
            throws BaseException {

        BreathingCapacityCalcApiRequest request = new BreathingCapacityCalcApiRequest();
        BeanUtil.copy(dto, request);

        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthinfoNodeApiUrl()
                        + NodeApiType.BREATHING_CAPACITY.getValue())
                .withHeader(ApiConnectInfo.X_NODE_TOKEN, token);

        BreathingCapacityCalcApiResponse response = api.callApi(request, connectInfo);

        ApiLogQueuePayload payload = apiLogComponent
                .getPayload4NodeApi(api, connectInfo, request, response, transactionId);
        apiLogComponent.registQueue(payload);

        if (Result.SUCCESS != response.getResult()) {
            // 肺活量計算APIの処理が成功以外の場合
            throw new ApiException(BusinessErrorCode.BREATHING_API_CONNECT_ERROR,
                    response.getDetail());
        }

        return response;
    }

}
