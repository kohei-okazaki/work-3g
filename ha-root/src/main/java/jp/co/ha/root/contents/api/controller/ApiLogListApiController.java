package jp.co.ha.root.contents.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.common.validator.annotation.Decimal;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.api.request.ApiLogListApiRequest;
import jp.co.ha.root.contents.api.response.ApiLogListApiResponse;
import jp.co.ha.root.contents.api.service.ApiLogService;

/**
 * API通信ログ一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class ApiLogListApiController
        extends BaseRootApiController<ApiLogListApiRequest, ApiLogListApiResponse> {

    /** API通信ログ検索サービス */
    @Autowired
    private ApiLogService apiLogService;

    /**
     * 一覧取得
     *
     * @param request
     *     API通信ログ一覧取得APIリクエスト
     * @param page
     *     取得対象ページ
     * @return API通信ログ一覧取得APIレスポンス
     */
    @GetMapping(value = "apidata")
    public ResponseEntity<ApiLogListApiResponse> list(ApiLogListApiRequest request,
            @RequestParam(name = "page", required = true, defaultValue = "0") @Decimal(min = "0", message = "page is positive") Integer page) {

        // ページング情報を取得(1ページあたりの表示件数はapplication-${env}.ymlより取得)
        Pageable pageable = PagingViewFactory.getPageable(page,
                applicationProperties.getApiPage());

        ApiLogListApiResponse response = getSuccessResponse();
        response.setApiLogList(apiLogService.getApiLogList(pageable));
        response.setPaging(apiLogService.getPagingView(pageable));

        return ResponseEntity.ok(response);
    }

    @Override
    protected ApiLogListApiResponse getResponse() {
        return new ApiLogListApiResponse();
    }
}
