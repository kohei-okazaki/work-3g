package jp.co.ha.root.contents.appuser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.common.validator.annotation.Decimal;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.appuser.request.AppUserListApiRequest;
import jp.co.ha.root.contents.appuser.response.AppUserListApiResponse;
import jp.co.ha.root.contents.appuser.service.AppUserService;

/**
 * ユーザ情報一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class AppUserListApiController
        extends BaseRootApiController<AppUserListApiRequest, AppUserListApiResponse> {

    /** ユーザ検索サービス */
    @Autowired
    private AppUserService service;

    /**
     * ユーザ情報一覧取得
     *
     * @param request
     *     ユーザ情報一覧取得APIリクエスト
     * @param page
     *     取得対象ページ
     * @return ユーザ情報一覧取得APIレスポンス
     */
    @GetMapping(value = "account")
    public ResponseEntity<AppUserListApiResponse> list(AppUserListApiRequest request,
            @RequestParam(name = "page", defaultValue = "0") @Decimal(min = "0", message = "page is positive") Integer page) {

        // ページング情報を取得(1ページあたりの表示件数はapplication-${env}.ymlより取得)
        Pageable pageable = PagingViewFactory.getPageable(page,
                applicationProperties.getAccountPage());

        AppUserListApiResponse response = getSuccessResponse();
        response.setAccountResponseList(service.getUserList(pageable));
        response.setPaging(service.getPagingView(pageable));

        return ResponseEntity.ok(response);
    }

    @Override
    protected AppUserListApiResponse getResponse() {
        return new AppUserListApiResponse();
    }

}
