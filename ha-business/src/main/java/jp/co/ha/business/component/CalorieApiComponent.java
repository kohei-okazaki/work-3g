package jp.co.ha.business.component;

import static jp.co.ha.business.exception.BusinessErrorCode.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.CalorieCalcApi;
import jp.co.ha.business.api.node.request.CalorieCalcApiRequest;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse.Result;
import jp.co.ha.business.api.node.response.CalorieCalcApiResponse;
import jp.co.ha.business.api.node.type.NodeApiType;
import jp.co.ha.business.dto.ApiLogQueuePayload;
import jp.co.ha.business.dto.CalorieCalcParam;
import jp.co.ha.business.dto.CalorieCalcResult;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
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
    /** カロリー計算API */
    @Autowired
    private CalorieCalcApi api;
    /** 健康情報設定ファイル */
    @Autowired
    private HealthInfoProperties prop;

    /**
     * カロリー計算処理を行う
     *
     * @param param
     *     {@linkplain CalorieCalcParam}
     * @return {@linkplain CalorieCalcDto}
     * @throws BaseException
     *     カロリー計算APIの処理が成功以外
     */
    public CalorieCalcResult calc(CalorieCalcParam param)
            throws BaseException {

        // API通信ログ.トランザクションIDを採番
        String transactionId = apiLogComponent.transactionId();

        // カロリー計算API実施
        CalorieCalcApiResponse apiResponse = callCalorieCalcApi(param,
                transactionId);

        return new CalorieCalcResult(
                param.age(),
                param.gender(),
                param.height(),
                param.weight(),
                param.lifeWorkMetabolism(),
                apiResponse.getCalorieCalcResult().getBaseMetabolism(),
                apiResponse.getCalorieCalcResult().getLostCaloriePerDay());
    }

    /**
     * カロリー計算APIを呼び出す
     *
     * @param param
     *     {@linkplain CalorieCalcParam}
     * @param transactionId
     *     トランザクションID
     * @return カロリー計算APIレスポンス
     * @throws BaseException
     *     API通信に失敗した場合
     */
    private CalorieCalcApiResponse callCalorieCalcApi(CalorieCalcParam param,
            String transactionId) throws BaseException {

        CalorieCalcApiRequest request = new CalorieCalcApiRequest();
        request.setAge(param.age());
        request.setGenderType(param.gender());
        request.setHeight(param.height());
        request.setWeight(param.height());
        request.setLifeWorkMetabolism(param.lifeWorkMetabolism());

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

}
