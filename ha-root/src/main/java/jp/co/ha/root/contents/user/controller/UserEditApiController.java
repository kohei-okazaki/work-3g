package jp.co.ha.root.contents.user.controller;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.user.request.UserEditApiRequest;
import jp.co.ha.root.contents.user.response.UserEditApiResponse;
import jp.co.ha.root.type.RootApiResult;

/**
 * ユーザ編集APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class UserEditApiController
        extends BaseRootApiController<UserEditApiRequest, UserEditApiResponse> {

    /**
     * 編集
     *
     * @param id
     *     ログインID
     * @param request
     *     ユーザ編集APIリクエスト
     * @return ユーザ編集APIレスポンス
     */
    @PutMapping(value = "user/edit/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public UserEditApiResponse edit(
            @PathVariable(name = "id", required = false) Optional<String> id,
            @RequestBody UserEditApiRequest request) {
        // TODO 要実装

        UserEditApiResponse response = new UserEditApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);

        return response;
    }
}
