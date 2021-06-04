package jp.co.ha.root.contents.healthcheck.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.healthcheck.HealthCheckApiResponse;
import jp.co.ha.root.contents.healthcheck.request.HealthCheckApiRequest;

/**
 * ヘルスチェックAPIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class HealthCheckApiController
        extends BaseRootApiController<HealthCheckApiRequest, HealthCheckApiResponse> {

    /**
     * ヘルスチェック
     *
     * @param request
     *     ヘルスチェックAPIリクエスト
     * @return ヘルスチェックAPIレスポンス
     */
    @GetMapping(value = "healthcheck", produces = { MediaType.APPLICATION_JSON_VALUE })
    public HealthCheckApiResponse index(HealthCheckApiRequest request) {
        return getSuccessResponse();
    }

    @Override
    protected HealthCheckApiResponse getResponse() {
        return new HealthCheckApiResponse();
    }

}
