package jp.co.ha.api.healthcheck.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.healthcheck.request.HealthCheckRequest;
import jp.co.ha.business.api.healthcheck.response.HealthCheckResponse;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.web.controller.BaseRestController;

/**
 * ヘルスチェックAPIコントローラ
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/api/healthcheck")
public class HealthCheckController
        extends BaseRestController<HealthCheckRequest, HealthCheckResponse> {

    /**
     * ヘルスチェックAPIを受け付ける
     *
     * @return レスポンス
     */
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public HealthCheckResponse doGet() {
        HealthCheckResponse response = new HealthCheckResponse();
        return response;
    }

    @Override
    @Deprecated
    public void accept(HealthCheckRequest request, HealthCheckResponse response)
            throws BaseException {
        // ヘルスチェックAPIではリクエストを受け付けてそのまま返却するだけ
    }
}
