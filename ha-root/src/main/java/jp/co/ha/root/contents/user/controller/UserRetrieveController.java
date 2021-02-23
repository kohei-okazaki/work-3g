package jp.co.ha.root.contents.user.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.read.RootLoginInfoSearchService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.db.entity.composite.CompositeRootUserInfo;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.base.BaseRootApiResponse.ErrorData;
import jp.co.ha.root.contents.tools.response.RoleMtListApiResponse.Role;
import jp.co.ha.root.contents.user.request.UserRetrieveApiRequest;
import jp.co.ha.root.contents.user.response.UserRetrieveApiResponse;
import jp.co.ha.root.type.RootApiResult;

/**
 * ユーザ情報取得APIコントローラ
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
     * 取得
     *
     * @param request
     *     ユーザ情報取得APIリクエスト
     * @param id
     *     ログインID
     * @return ユーザ情報取得APIレスポンス
     */
    @GetMapping(value = "user/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public UserRetrieveApiResponse retrieve(UserRetrieveApiRequest request,
            @PathVariable(name = "id", required = false) Optional<String> id) {

        // TODO 妥当性チェック処理
        Integer seqLoginId = Integer.valueOf(id.get());

        List<CompositeRootUserInfo> entityList = searchService
                .findCompositeUserById(seqLoginId);
        if (CollectionUtil.isEmpty(entityList)) {
            UserRetrieveApiResponse response = new UserRetrieveApiResponse();
            response.setRootApiResult(RootApiResult.FAILURE);
            ErrorData error = new ErrorData();
            error.setMessage("user data is not found");
            response.setErrorData(error);
            return response;
        }

        // 結合しているので権限情報以外はここから取得する
        CompositeRootUserInfo entity = entityList.get(0);

        UserRetrieveApiResponse response = new UserRetrieveApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);
        response.setSeqLoginId(seqLoginId);
        response.setRoles(entityList.stream().map(e -> {
            Role role = new Role();
            role.setValue(e.getRole());
            role.setLabel(e.getRoleName());
            return role;
        }).collect(Collectors.toList()));
        BeanUtil.copy(entity, response);

        return response;
    }
}
