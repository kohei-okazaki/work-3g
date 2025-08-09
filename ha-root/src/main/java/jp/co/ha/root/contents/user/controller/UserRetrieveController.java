package jp.co.ha.root.contents.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.read.RootLoginInfoSearchService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.db.entity.composite.CompositeRootUserInfo;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.tools.response.RoleMtListApiResponse.Role;
import jp.co.ha.root.contents.user.request.UserRetrieveApiRequest;
import jp.co.ha.root.contents.user.response.UserRetrieveApiResponse;

/**
 * ユーザ取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class UserRetrieveController
        extends BaseRootApiController<UserRetrieveApiRequest, UserRetrieveApiResponse> {

    /** 管理者サイトユーザログイン情報検索サービス */
    @Autowired
    private RootLoginInfoSearchService searchService;

    /**
     * ユーザ取得処理
     *
     * @param request
     *     ユーザ情報取得APIリクエスト
     * @param seqLoginId
     *     ログインID
     * @return ユーザ情報取得APIレスポンス
     */
    @GetMapping(value = "user/{seq_login_id}", produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserRetrieveApiResponse> retrieve(
            UserRetrieveApiRequest request,
            @PathVariable(name = "seq_login_id", required = true) Long seqLoginId) {

        List<CompositeRootUserInfo> entityList = searchService
                .findCompositeUserById(seqLoginId);
        if (CollectionUtil.isEmpty(entityList)) {
            return ResponseEntity.badRequest()
                    .body(getErrorResponse("user data is not found"));
        }

        // 結合しているので権限情報以外はここから取得する
        CompositeRootUserInfo entity = entityList.get(0);

        UserRetrieveApiResponse response = getSuccessResponse();
        response.setSeqLoginId(seqLoginId);
        response.setRoles(entityList.stream().map(e -> {
            Role role = new Role();
            role.setValue(e.getRole());
            role.setLabel(e.getRoleName());
            return role;
        }).collect(Collectors.toList()));
        BeanUtil.copy(entity, response);

        return ResponseEntity.ok(response);
    }

    @Override
    protected UserRetrieveApiResponse getResponse() {
        return new UserRetrieveApiResponse();
    }
}
