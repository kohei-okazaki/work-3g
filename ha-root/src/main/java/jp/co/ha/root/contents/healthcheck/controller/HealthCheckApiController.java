package jp.co.ha.root.contents.healthcheck.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.healthcheck.request.HealthCheckApiRequest;
import jp.co.ha.root.contents.healthcheck.response.HealthCheckApiResponse;

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
    @GetMapping(value = "healthcheck")
    public ResponseEntity<HealthCheckApiResponse> index(HealthCheckApiRequest request) {
        return ResponseEntity.ok(getSuccessResponse());
    }

    @Override
    protected HealthCheckApiResponse getResponse() {
        return new HealthCheckApiResponse();
    }

}
