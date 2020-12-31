package jp.co.ha.business.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.CalorieCalcApi;
import jp.co.ha.business.api.node.TokenApi;
import jp.co.ha.business.api.node.request.CalorieCalcRequest;
import jp.co.ha.business.api.node.request.TokenRequest;
import jp.co.ha.business.api.node.response.BaseNodeResponse.Result;
import jp.co.ha.business.api.node.response.CalorieCalcResponse;
import jp.co.ha.business.api.node.response.TokenResponse;
import jp.co.ha.business.api.node.type.NodeApiType;
import jp.co.ha.business.dto.CalorieCalcDto;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.web.api.ApiConnectInfo;

/**
 * カロリー計算Component
 *
 * @version 1.0.0
 */
@Component
public class CalorieCalcComponent {

    /** API通信情報Component */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;
    /** トークン発行API */
    @Autowired
    private TokenApi tokenApi;
    /** カロリー計算API */
    @Autowired
    private CalorieCalcApi calorieCalcApi;
    /** 健康情報関連プロパティ */
    @Autowired
    private HealthInfoProperties prop;

    /**
     * カロリー計算処理を行う
     *
     * @param dto
     *     {@linkplain CalorieCalcDto}
     * @param seqUserId
     *     ユーザID
     * @return {@linkplain CalorieCalcDto}
     * @throws BaseException
     *     カロリー計算APIの処理が成功以外
     */
    public CalorieCalcDto calc(CalorieCalcDto dto, Integer seqUserId)
            throws BaseException {

        TokenResponse tokenApiResponse = callTokenApi(seqUserId);

        CalorieCalcResponse apiResponse = callCalorieCalcApi(dto,
                tokenApiResponse.getToken(), seqUserId);
        BeanUtil.copy(apiResponse.getCalorieCalcResult(), dto);

        return dto;
    }

    /**
     * Token発行APIを呼び出す
     *
     * @param seqUserId
     *     ユーザID
     * @return Token発行APIのレスポンス
     * @throws BaseException
     *     API通信に失敗した場合
     */
    private TokenResponse callTokenApi(Integer seqUserId) throws BaseException {

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(tokenApi.getApiName(), seqUserId);

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
     * カロリー計算APIを呼び出す
     *
     * @param dto
     *     カロリー計算DTO
     * @param token
     *     トークン
     * @param seqUserId
     *     ユーザID
     * @return カロリー計算APIレスポンス
     * @throws BaseException
     *     API通信に失敗した場合
     */
    private CalorieCalcResponse callCalorieCalcApi(CalorieCalcDto dto, String token,
            Integer seqUserId) throws BaseException {

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(calorieCalcApi.getApiName(), seqUserId);

        CalorieCalcRequest request = new CalorieCalcRequest();
        BeanUtil.copy(dto, request);

        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthinfoNodeApiUrl()
                        + NodeApiType.CALORIE.getValue())
                .withHeader(ApiConnectInfo.X_NODE_TOKEN, token);

        CalorieCalcResponse response = calorieCalcApi.callApi(request, connectInfo);

        // API通信情報を更新
        apiCommunicationDataComponent.update(apiCommunicationData, connectInfo, response);

        if (Result.SUCCESS != response.getResult()) {
            // カロリー計算APIの処理が成功以外の場合
            throw new ApiException(BusinessErrorCode.CALORIE_API_CONNECT_ERROR,
                    response.getDetail());
        }

        return response;
    }

}
