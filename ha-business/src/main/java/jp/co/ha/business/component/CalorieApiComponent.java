package jp.co.ha.business.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.CalorieCalcApi;
import jp.co.ha.business.api.node.request.CalorieCalcApiRequest;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse.Result;
import jp.co.ha.business.api.node.response.CalorieCalcApiResponse;
import jp.co.ha.business.api.node.response.TokenApiResponse;
import jp.co.ha.business.api.node.type.NodeApiType;
import jp.co.ha.business.dto.CalorieCalcDto;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.db.entity.ApiCommunicationData;

/**
 * カロリー計算APIのコンポーネントクラス<br>
 * APIクラスを直接呼ばずに本クラスを経由して呼び出すこと
 *
 * @version 1.0.0
 */
@Component
public class CalorieApiComponent {

    /** {@linkplain ApiCommunicationDataComponent} */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;
    /** {@linkplain TokenApiComponent} */
    @Autowired
    private TokenApiComponent tokenApiComponent;
    /** {@linkplain CalorieCalcApi} */
    @Autowired
    private CalorieCalcApi calorieCalcApi;
    /** {@linkplain HealthInfoProperties} */
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
    public CalorieCalcDto calc(CalorieCalcDto dto, Long seqUserId) throws BaseException {

        // API通信情報.トランザクションIDを採番
        Long transactionId = apiCommunicationDataComponent.getTransactionId();

        CalorieCalcApiResponse apiResponse;
        if (prop.isHealthinfoNodeApiMigrateFlg()) {

            // カロリー計算API実施
            apiResponse = callCalorieCalcApi(dto, transactionId);

        } else {

            // トークン発行API実施
            @SuppressWarnings("deprecation")
            TokenApiResponse tokenApiResponse = tokenApiComponent.callTokenApi(seqUserId,
                    transactionId);

            // カロリー計算API実施
            apiResponse = callCalorieCalcApi(dto,
                    tokenApiResponse.getToken(), transactionId);

        }
        BeanUtil.copy(apiResponse.getCalorieCalcResult(), dto);

        return dto;
    }

    /**
     * カロリー計算APIを呼び出す
     *
     * @param dto
     *     カロリー計算DTO
     * @param transactionId
     *     トランザクションID
     * @return カロリー計算APIレスポンス
     * @throws BaseException
     *     API通信に失敗した場合
     */
    private CalorieCalcApiResponse callCalorieCalcApi(CalorieCalcDto dto,
            Long transactionId) throws BaseException {

        CalorieCalcApiRequest request = new CalorieCalcApiRequest();
        BeanUtil.copy(dto, request);

        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthinfoNodeApiUrl()
                        + NodeApiType.CALORIE.getValue());

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(calorieCalcApi.getApiName(), transactionId,
                        calorieCalcApi.getHttpMethod(),
                        calorieCalcApi.getUri(connectInfo, request), request);

        CalorieCalcApiResponse response = calorieCalcApi.callApi(request, connectInfo);

        // API通信情報を更新
        apiCommunicationDataComponent.update(apiCommunicationData, connectInfo, response);

        if (Result.SUCCESS != response.getResult()) {
            // カロリー計算APIの処理が成功以外の場合
            throw new ApiException(BusinessErrorCode.CALORIE_API_CONNECT_ERROR,
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
     * @param transactionId
     *     トランザクションID
     * @return カロリー計算APIレスポンス
     * @throws BaseException
     *     API通信に失敗した場合
     */
    @Deprecated
    private CalorieCalcApiResponse callCalorieCalcApi(CalorieCalcDto dto, String token,
            Long transactionId) throws BaseException {

        CalorieCalcApiRequest request = new CalorieCalcApiRequest();
        BeanUtil.copy(dto, request);

        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthinfoNodeApiUrl()
                        + NodeApiType.CALORIE.getValue())
                .withHeader(ApiConnectInfo.X_NODE_TOKEN, token);

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(calorieCalcApi.getApiName(), transactionId,
                        calorieCalcApi.getHttpMethod(),
                        calorieCalcApi.getUri(connectInfo, request), request);

        CalorieCalcApiResponse response = calorieCalcApi.callApi(request, connectInfo);

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
