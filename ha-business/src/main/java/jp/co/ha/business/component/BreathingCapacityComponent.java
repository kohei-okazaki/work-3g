package jp.co.ha.business.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.BreathingCapacityCalcApi;
import jp.co.ha.business.api.node.NodeApiType;
import jp.co.ha.business.api.node.TokenApi;
import jp.co.ha.business.api.node.request.BreathingCapacityCalcRequest;
import jp.co.ha.business.api.node.request.TokenRequest;
import jp.co.ha.business.api.node.response.BaseNodeResponse;
import jp.co.ha.business.api.node.response.BaseNodeResponse.Result;
import jp.co.ha.business.api.node.response.BreathingCapacityCalcResponse;
import jp.co.ha.business.api.node.response.TokenResponse;
import jp.co.ha.business.db.crud.create.ApiCommunicationDataCreateService;
import jp.co.ha.business.db.crud.update.ApiCommunicationDataUpdateService;
import jp.co.ha.business.dto.BreathingCapacityDto;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.web.api.ApiConnectInfo;

/**
 * 肺活量計算Component
 *
 * @version 1.0.0
 */
@Component
public class BreathingCapacityComponent {

    /** API通信情報作成サービス */
    @Autowired
    private ApiCommunicationDataCreateService apiCommunicationDataCreateService;
    /** API通信情報更新サービス */
    @Autowired
    private ApiCommunicationDataUpdateService apiCommunicationDataUpdateService;
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

        TokenResponse tokenApiResponse = callTokenApi(seqUserId);

        BreathingCapacityCalcResponse apiResponse = callApi(dto,
                tokenApiResponse.getToken());
        BeanUtil.copy(apiResponse.getBreathingCapacityCalcResult(), dto);

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
        ApiCommunicationData apiCommunicationData = createApiCommunicationData(
                NodeApiType.TOKEN.getName(), seqUserId);

        TokenRequest request = new TokenRequest();
        request.setSeqUserId(seqUserId);
        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthinfoNodeApiUrl()
                        + NodeApiType.TOKEN.getValue());
        TokenResponse response = tokenApi.callApi(request, connectInfo);

        // API通信情報を更新
        updateApiCommunicationData(apiCommunicationData, connectInfo, response);

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
     * @return 肺活量計算APIレスポンス
     * @throws BaseException
     *     API通信に失敗した場合
     */
    private BreathingCapacityCalcResponse callApi(BreathingCapacityDto dto, String token)
            throws BaseException {

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = createApiCommunicationData(
                NodeApiType.BREATHING_CAPACITY.getName(), null);

        BreathingCapacityCalcRequest request = new BreathingCapacityCalcRequest();
        BeanUtil.copy(dto, request);

        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthinfoNodeApiUrl()
                        + NodeApiType.BREATHING_CAPACITY.getValue())
                .withHeader("X-NODE-TOKEN", token);

        BreathingCapacityCalcResponse response = breathingCapacityCalcApi.callApi(request,
                connectInfo);

        // API通信情報を更新
        updateApiCommunicationData(apiCommunicationData, connectInfo, response);

        if (Result.SUCCESS != response.getResult()) {
            // 肺活量計算APIの処理が成功以外の場合
            throw new ApiException(BusinessErrorCode.BREATHING_API_CONNECT_ERROR,
                    response.getDetail());
        }

        return response;
    }

    /**
     * API通信情報を登録する
     *
     * @param apiName
     *     API名
     * @param seqUserId
     *     ユーザID
     * @return API通信情報
     */
    private ApiCommunicationData createApiCommunicationData(String apiName,
            Integer seqUserId) {

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = new ApiCommunicationData();
        apiCommunicationData.setApiName(apiName);
        apiCommunicationData.setSeqUserId(seqUserId);
        apiCommunicationData.setRequestDate(DateTimeUtil.getSysDate());
        apiCommunicationDataCreateService.create(apiCommunicationData);

        return apiCommunicationData;
    }

    /**
     * API通信情報を更新する
     *
     * @param apiCommunicationData
     *     API通信情報
     * @param connectInfo
     *     API接続情報
     * @param response
     *     APIレスポンス情報
     */
    private void updateApiCommunicationData(ApiCommunicationData apiCommunicationData,
            ApiConnectInfo connectInfo, BaseNodeResponse response) {

        apiCommunicationData.setHttpStatus(String.valueOf(connectInfo.getHttpStatus()));
        apiCommunicationData.setResult(response.getResult().getValue());
        apiCommunicationData.setDetail(response.getDetail());
        apiCommunicationData.setResponseDate(DateTimeUtil.getSysDate());
        apiCommunicationDataUpdateService.update(apiCommunicationData);

    }

}
