package jp.co.ha.root.contents.auth.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.auth.request.LoginApiRequest;
import jp.co.ha.root.contents.auth.response.LoginApiResponse;
import jp.co.ha.root.type.RootApiResult;

/**
 * ログインAPIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class LoginApiController
        extends BaseRootApiController<LoginApiRequest, LoginApiResponse> {

    /**
     * ログインAPI受付
     *
     * @param request
     *     ログインAPIリクエスト
     * @return ログインAPIレスポンス
     */
    @PostMapping(value = "login", produces = { MediaType.APPLICATION_JSON_VALUE })
    public LoginApiResponse login(@RequestBody LoginApiRequest request) {
        // TODO 要実装
        LoginApiResponse response = new LoginApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);

        return response;
    }

}
