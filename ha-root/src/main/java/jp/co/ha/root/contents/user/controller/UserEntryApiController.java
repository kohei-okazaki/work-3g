package jp.co.ha.root.contents.user.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.user.request.UserEntryApiRequest;
import jp.co.ha.root.contents.user.response.UserEntryApiResponse;
import jp.co.ha.root.type.RootApiResult;

/**
 * ユーザ登録APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class UserEntryApiController
        extends BaseRootApiController<UserEntryApiRequest, UserEntryApiResponse> {

    /**
     * 処理
     *
     * @param request
     *     ユーザ登録APIリクエスト
     * @return ユーザ登録APIレスポンス
     */
    @PostMapping(value = "user/entry", produces = { MediaType.APPLICATION_JSON_VALUE })
    public UserEntryApiResponse entry(@RequestBody UserEntryApiRequest request) {
        // TODO 要実装

        UserEntryApiResponse response = new UserEntryApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);

        return response;
    }

}
