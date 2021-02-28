package jp.co.ha.root.contents.healthinfo.controller;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.healthinfo.request.HealthInfoEditApiRequest;
import jp.co.ha.root.contents.healthinfo.response.HealthInfoEditApiResponse;

/**
 * 健康情報編集APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class HealthInfoEditApiController extends
        BaseRootApiController<HealthInfoEditApiRequest, HealthInfoEditApiResponse> {

    /**
     * 健康情報編集
     *
     * @param id
     *     健康情報ID
     * @param request
     *     健康情報編集APIリクエスト
     * @return 健康情報編集APIレスポンス
     */
    @PutMapping(value = "healthinfo/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public HealthInfoEditApiResponse edit(
            @PathVariable(name = "id", required = false) Optional<String> id,
            @RequestBody HealthInfoEditApiRequest request) {

        HealthInfoEditApiResponse response = getSuccessResponse();
        return response;
    }

    @Override
    protected HealthInfoEditApiResponse getResponse() {
        return new HealthInfoEditApiResponse();
    }
}
