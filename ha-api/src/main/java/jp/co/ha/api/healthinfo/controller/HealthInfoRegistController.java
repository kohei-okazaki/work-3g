package jp.co.ha.api.healthinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.healthinfo.HealthInfoRegistApi;
import jp.co.ha.business.api.healthinfo.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoRegistResponse;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.web.controller.BaseUserAuthPostRestController;

/**
 * 健康情報登録コントローラ
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/api/{userId}/healthinfo")
public class HealthInfoRegistController extends
        BaseUserAuthPostRestController<HealthInfoRegistRequest, HealthInfoRegistResponse> {

    /** 健康情報登録API */
    @Autowired
    private HealthInfoRegistApi api;

    /**
     * {@inheritDoc}
     */
    @Override
    public HealthInfoRegistResponse getApiResponse() {
        return new HealthInfoRegistResponse();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(HealthInfoRegistRequest request, HealthInfoRegistResponse response)
            throws BaseException {

        api.execute(request, response);

    }

}
