package jp.co.ha.business.component;

import static jp.co.ha.business.exception.BusinessErrorCode.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.BreathingCapacityCalcApi;
import jp.co.ha.business.api.node.request.BreathingCapacityCalcApiRequest;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse.Result;
import jp.co.ha.business.api.node.response.BreathingCapacityCalcApiResponse;
import jp.co.ha.business.api.node.response.BreathingCapacityCalcApiResponse.BreathingCapacityCalcResult;
import jp.co.ha.business.api.node.type.NodeApiType;
import jp.co.ha.business.dto.ApiLogQueuePayload;
import jp.co.ha.business.dto.BreathingCapacityParam;
import jp.co.ha.business.dto.BreathingCapacityResult;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
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
    /** 肺活量計算API */
    @Autowired
    private BreathingCapacityCalcApi api;
    /** 健康情報設定ファイル */
    @Autowired
    private HealthInfoProperties prop;

    /**
     * 肺活量計算処理を行う
     *
     * @param param
     *     {@linkplain BreathingCapacityParam}
     * @return {@linkplain BreathingCapacityResult}
     * @throws BaseException
     *     肺活量計算APIの処理が成功以外
     */
    public BreathingCapacityResult calc(BreathingCapacityParam param)
            throws BaseException {

        // API通信ログ.トランザクションIDを採番
        String transactionId = apiLogComponent.transactionId();

        // 肺活量計算API実施
        BreathingCapacityCalcApiResponse apiResponse = callBreathingCapacityCalcApi(param,
                transactionId);

        BreathingCapacityCalcResult result = apiResponse.getBreathingCapacityCalcResult();

        return new BreathingCapacityResult(
                param.age(),
                param.gender(),
                param.height(),
                result.getPredictBreathingCapacity(),
                result.getBreathingCapacityPercentage());
    }

    /**
     * 肺活量計算APIを呼び出す
     *
     * @param param
     *     肺活量計算画面入力パラメータ
     * @param transactionId
     *     トランザクションID
     * @return 肺活量計算APIレスポンス
     * @throws BaseException
     *     API通信に失敗した場合
     */
    private BreathingCapacityCalcApiResponse callBreathingCapacityCalcApi(
            BreathingCapacityParam param, String transactionId) throws BaseException {

        BreathingCapacityCalcApiRequest request = new BreathingCapacityCalcApiRequest();
        request.setAge(param.age());
        request.setGenderType(param.gender());
        request.setHeight(param.height());

        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.healthinfoNodeApiUrl()
                        + NodeApiType.BREATHING_CAPACITY.getValue());

        BreathingCapacityCalcApiResponse response = api.callApi(request, connectInfo);

        ApiLogQueuePayload payload = apiLogComponent
                .getPayload4NodeApi(api, connectInfo, request, response, transactionId);
        apiLogComponent.registQueue(payload);

        if (Result.SUCCESS != response.getResult()) {
            // 肺活量計算APIの処理が成功以外の場合
            throw new ApiException(BREATHING_API_CONNECT_ERROR, response.getDetail());
        }

        return response;
    }

}
