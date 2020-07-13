package jp.co.ha.dashboard.calorie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.api.node.CalorieCalcApi;
import jp.co.ha.business.api.node.request.CalorieCalcRequest;
import jp.co.ha.business.api.node.response.CalorieCalcResponse;
import jp.co.ha.business.dto.CalorieCalcDto;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.dashboard.calorie.service.CalorieCalcService;
import jp.co.ha.web.form.BaseNodeResponse.Result;

/**
 * カロリー計算サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class CalorieCalcServiceImpl implements CalorieCalcService {

    /** カロリー計算API */
    @Autowired
    private CalorieCalcApi calorieCalcApi;

    /**
     * {@inheritDoc}
     */
    @Override
    public CalorieCalcDto calc(CalorieCalcDto dto) throws BaseException {

        CalorieCalcResponse apiResponse = callCalorieCalcApi(dto);
        BeanUtil.copy(apiResponse.getCalorieCalcResult(), dto);

        return dto;
    }

    /**
     * カロリー計算APIを呼び出す
     *
     * @param dto
     *     カロリー計算DTO
     * @return カロリー計算APIレスポンス
     * @throws BaseException
     *     API通信に失敗した場合
     */
    private CalorieCalcResponse callCalorieCalcApi(CalorieCalcDto dto)
            throws BaseException {

        CalorieCalcRequest request = new CalorieCalcRequest();
        BeanUtil.copy(dto, request);
        CalorieCalcResponse response = calorieCalcApi.callApi(request);

        if (Result.SUCCESS != response.getResult()) {
            // カロリー計算APIの処理が成功以外の場合
            throw new ApiException(BusinessErrorCode.CALORIE_API_CONNERR,
                    response.getDetail());
        }

        return response;
    }

}
