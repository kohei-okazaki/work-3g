package jp.co.ha.root.contents.auth.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.auth.request.LogoutApiRequest;
import jp.co.ha.root.contents.auth.response.LogoutApiResponse;
import jp.co.ha.root.type.RootApiResult;

/**
 * ログアウトAPIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class LogoutApiController
        extends BaseRootApiController<LogoutApiRequest, LogoutApiResponse> {

    /**
     * ログアウト処理
     *
     * @param request
     *     ログアウトAPIリクエストクラス
     * @return ログアウトAPIレスポンスクラス
     */
    @GetMapping(value = "logout", produces = { MediaType.APPLICATION_JSON_VALUE })
    public LogoutApiResponse logout(LogoutApiRequest request) {
        // TODO 要実装

        LogoutApiResponse response = new LogoutApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);
        return response;
    }
}
