package jp.co.ha.business.component;

import static jp.co.ha.business.exception.BusinessErrorCode.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.CalorieCalcApi;
import jp.co.ha.business.api.node.request.CalorieCalcApiRequest;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse.Result;
import jp.co.ha.business.api.node.response.CalorieCalcApiResponse;
import jp.co.ha.business.api.node.response.TokenApiResponse;
import jp.co.ha.business.api.node.type.NodeApiType;
import jp.co.ha.business.dto.ApiLogQueuePayload;
import jp.co.ha.business.dto.CalorieCalcDto;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.api.ApiConnectInfo;

/**
 * カロリー計算APIのComponentクラス<br>
 * APIクラスを直接呼ばずに本クラスを経由して呼び出すこと
 *
 * @version 1.0.0
 */
@Component
public class CalorieApiComponent {

    /** API通信ログComponent */
    @Autowired
    private ApiLogComponent apiLogComponent;
    /** トークン発行APIComponent */
    @Autowired
    private TokenApiComponent tokenApiComponent;
    /** カロリー計算API */
    @Autowired
    private CalorieCalcApi api;
    /** 健康情報設定ファイル */
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
        String transactionId = apiLogComponent.getTransactionId();

        CalorieCalcApiResponse apiResponse;
        if (prop.healthinfoNodeApiMigrateFlg()) {

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
            String transactionId) throws BaseException {

        CalorieCalcApiRequest request = new CalorieCalcApiRequest();
        BeanUtil.copy(dto, request);

        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.healthinfoNodeApiUrl()
                        + NodeApiType.CALORIE.getValue());

        CalorieCalcApiResponse response = api.callApi(request, connectInfo);

        ApiLogQueuePayload payload = apiLogComponent
                .getPayload4NodeApi(api, connectInfo, request, response, transactionId);
        apiLogComponent.registQueue(payload);

        if (Result.SUCCESS != response.getResult()) {
            // カロリー計算APIの処理が成功以外の場合
            throw new ApiException(CALORIE_API_CONNECT_ERROR, response.getDetail());
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
            String transactionId) throws BaseException {

        CalorieCalcApiRequest request = new CalorieCalcApiRequest();
        BeanUtil.copy(dto, request);

        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.healthinfoNodeApiUrl()
                        + NodeApiType.CALORIE.getValue())
                .withHeader(ApiConnectInfo.X_NODE_TOKEN, token);

        CalorieCalcApiResponse response = api.callApi(request, connectInfo);

        ApiLogQueuePayload payload = apiLogComponent
                .getPayload4NodeApi(api, connectInfo, request, response, transactionId);
        apiLogComponent.registQueue(payload);

        if (Result.SUCCESS != response.getResult()) {
            // カロリー計算APIの処理が成功以外の場合
            throw new ApiException(CALORIE_API_CONNECT_ERROR, response.getDetail());
        }

        return response;
    }

}
