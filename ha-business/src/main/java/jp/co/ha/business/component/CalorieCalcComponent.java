package jp.co.ha.business.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.CalorieCalcApi;
import jp.co.ha.business.api.node.NodeApiType;
import jp.co.ha.business.api.node.TokenApi;
import jp.co.ha.business.api.node.request.CalorieCalcRequest;
import jp.co.ha.business.api.node.request.TokenRequest;
import jp.co.ha.business.api.node.response.BaseNodeResponse.Result;
import jp.co.ha.business.api.node.response.CalorieCalcResponse;
import jp.co.ha.business.api.node.response.TokenResponse;
import jp.co.ha.business.dto.CalorieCalcDto;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.web.api.ApiConnectInfo;

/**
 * カロリー計算Component
 *
 * @version 1.0.0
 */
@Component
public class CalorieCalcComponent {

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
     * @param userId
     *     ユーザID
     * @return {@linkplain CalorieCalcDto}
     * @throws BaseException
     *     カロリー計算APIの処理が成功以外
     */
    public CalorieCalcDto calc(CalorieCalcDto dto, String userId) throws BaseException {

        TokenResponse tokenApiResponse = callTokenApi(userId);

        CalorieCalcResponse apiResponse = callCalorieCalcApi(dto,
                tokenApiResponse.getToken());
        BeanUtil.copy(apiResponse.getCalorieCalcResult(), dto);

        return dto;
    }

    /**
     * Token発行APIを呼び出す
     *
     * @param userId
     *     ユーザID
     * @return Token発行APIのレスポンス
     * @throws BaseException
     */
    private TokenResponse callTokenApi(String userId) throws BaseException {

        TokenRequest request = new TokenRequest();
        request.setUserId(userId);
        ApiConnectInfo connectInfo = new ApiConnectInfo();
        connectInfo.setUrlSupplier(
                () -> prop.getHealthinfoNodeApiUrl() + NodeApiType.TOKEN.getValue());
        TokenResponse response = tokenApi.callApi(request, connectInfo);

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
     * @return カロリー計算APIレスポンス
     * @throws BaseException
     *     API通信に失敗した場合
     */
    private CalorieCalcResponse callCalorieCalcApi(CalorieCalcDto dto, String token)
            throws BaseException {

        CalorieCalcRequest request = new CalorieCalcRequest();
        BeanUtil.copy(dto, request);

        ApiConnectInfo connectInfo = new ApiConnectInfo();
        connectInfo.setUrlSupplier(
                () -> prop.getHealthinfoNodeApiUrl() + NodeApiType.CALORIE.getValue());
        connectInfo.addHeader("X-NODE-TOKEN", token);
        CalorieCalcResponse response = calorieCalcApi.callApi(request, connectInfo);

        if (Result.SUCCESS != response.getResult()) {
            // カロリー計算APIの処理が成功以外の場合
            throw new ApiException(BusinessErrorCode.CALORIE_API_CONNECT_ERROR,
                    response.getDetail());
        }

        return response;
    }
}
