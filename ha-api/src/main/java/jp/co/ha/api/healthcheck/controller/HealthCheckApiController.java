package jp.co.ha.api.healthcheck.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.healthcheck.request.HealthCheckApiRequest;
import jp.co.ha.business.api.healthcheck.response.HealthCheckApiResponse;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.web.controller.BaseRestController;

/**
 * ヘルスチェックAPIコントローラ
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/api/healthcheck")
public class HealthCheckApiController
        extends BaseRestController<HealthCheckApiRequest, HealthCheckApiResponse> {

    /**
     * ヘルスチェックAPIを受け付ける
     *
     * @return レスポンス
     */
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public HealthCheckApiResponse doGet() {
        HealthCheckApiResponse response = new HealthCheckApiResponse();
        return response;
    }

    @Override
    @Deprecated
    public void accept(HealthCheckApiRequest request, HealthCheckApiResponse response)
            throws BaseException {
        // ヘルスチェックAPIではリクエストを受け付けてそのまま返却するだけ
    }
}
