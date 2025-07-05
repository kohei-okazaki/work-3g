package jp.co.ha.business.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.BreathingCapacityCalcApi;
import jp.co.ha.business.api.node.request.BreathingCapacityCalcApiRequest;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse.Result;
import jp.co.ha.business.api.node.response.BreathingCapacityCalcApiResponse;
import jp.co.ha.business.api.node.response.TokenApiResponse;
import jp.co.ha.business.api.node.type.NodeApiType;
import jp.co.ha.business.dto.BreathingCapacityDto;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.db.entity.ApiCommunicationData;

/**
 * 肺活量計算APIのコンポーネントクラス<br>
 * APIクラスを直接呼ばずに本クラスを経由して呼び出すこと
 *
 * @version 1.0.0
 */
@Component
public class BreathingCapacityApiComponent {

    /** {@linkplain ApiCommunicationDataComponent} */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;
    /** {@linkplain TokenApiComponent} */
    @Autowired
    private TokenApiComponent tokenApiComponent;
    /** {@linkplain BreathingCapacityCalcApi} */
    @Autowired
    private BreathingCapacityCalcApi breathingCapacityCalcApi;
    /** {@linkplain HealthInfoProperties} */
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
        Long transactionId = apiCommunicationDataComponent.getTransactionId();

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
            BreathingCapacityDto dto, Long transactionId)
            throws BaseException {

        BreathingCapacityCalcApiRequest request = new BreathingCapacityCalcApiRequest();
        BeanUtil.copy(dto, request);

        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthinfoNodeApiUrl()
                        + NodeApiType.BREATHING_CAPACITY.getValue());

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent.create(
                breathingCapacityCalcApi.getApiName(), transactionId,
                breathingCapacityCalcApi.getHttpMethod(),
                breathingCapacityCalcApi.getUri(connectInfo, request), request);

        BreathingCapacityCalcApiResponse response = breathingCapacityCalcApi
                .callApi(request, connectInfo);

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
            BreathingCapacityDto dto, String token, Long transactionId)
            throws BaseException {

        BreathingCapacityCalcApiRequest request = new BreathingCapacityCalcApiRequest();
        BeanUtil.copy(dto, request);

        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthinfoNodeApiUrl()
                        + NodeApiType.BREATHING_CAPACITY.getValue())
                .withHeader(ApiConnectInfo.X_NODE_TOKEN, token);

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent.create(
                breathingCapacityCalcApi.getApiName(), transactionId,
                breathingCapacityCalcApi.getHttpMethod(),
                breathingCapacityCalcApi.getUri(connectInfo, request), request);

        BreathingCapacityCalcApiResponse response = breathingCapacityCalcApi
                .callApi(request, connectInfo);

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
