package jp.co.ha.root.contents.healthinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.healthinfo.request.HealthInfoListApiRequest;
import jp.co.ha.root.contents.healthinfo.response.HealthInfoListApiResponse;
import jp.co.ha.root.type.RootApiResult;

/**
 * 健康情報一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class HealthInfoListApiController extends
        BaseRootApiController<HealthInfoListApiRequest, HealthInfoListApiResponse> {

    /** 健康情報検索サービス */
    @Autowired
    private HealthInfoSearchService healthInfoSearchService;

    /**
     * 健康情報一覧取得
     *
     * @param request
     *     健康情報一覧取得APIリクエスト
     * @return 健康情報一覧取得APIレスポンス
     */
    @GetMapping(value = "healthinfo", produces = { MediaType.APPLICATION_JSON_VALUE })
    public HealthInfoListApiResponse list(HealthInfoListApiRequest request) {
        // TODO 要実装

        HealthInfoListApiResponse response = new HealthInfoListApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);

        return response;
    }
}
