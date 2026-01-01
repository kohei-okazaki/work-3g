package jp.co.ha.root.contents.healthinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.common.validator.annotation.Decimal;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.healthinfo.request.HealthInfoListApiRequest;
import jp.co.ha.root.contents.healthinfo.response.HealthInfoListApiResponse;
import jp.co.ha.root.contents.healthinfo.service.HealthInfoService;

/**
 * 健康情報一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class HealthInfoListApiController extends
        BaseRootApiController<HealthInfoListApiRequest, HealthInfoListApiResponse> {

    /** 健康情報サービス */
    @Autowired
    private HealthInfoService service;

    /**
     * 健康情報一覧取得
     *
     * @param request
     *     健康情報一覧取得APIリクエスト
     * @param page
     *     取得対象ページ
     * @return 健康情報一覧取得APIレスポンス
     */
    @GetMapping(value = "healthinfo")
    public ResponseEntity<HealthInfoListApiResponse> list(
            HealthInfoListApiRequest request,
            @RequestParam(name = "page", required = true, defaultValue = "0") @Decimal(min = "0", message = "page is positive") Integer page) {

        // ページング情報を取得(1ページあたりの表示件数はapplication-${env}.ymlより取得)
        Pageable pageable = PagingViewFactory.getPageable(page,
                applicationProperties.getHealthinfoPage());

        HealthInfoListApiResponse response = getSuccessResponse();
        response.setHealthInfoResponseList(service.getHealthInfoList(pageable));
        response.setPaging(service.getPagingView(pageable));

        return ResponseEntity.ok(response);
    }

    @Override
    protected HealthInfoListApiResponse getResponse() {
        return new HealthInfoListApiResponse();
    }

}
