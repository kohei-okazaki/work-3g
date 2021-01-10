package jp.co.ha.root.contents.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.create.RootLoginInfoCreateService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.io.encodeanddecode.annotation.Sha256;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.db.entity.RootLoginInfo;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.user.request.UserEntryApiRequest;
import jp.co.ha.root.contents.user.response.UserEntryApiResponse;
import jp.co.ha.root.type.RootApiResult;
import jp.co.ha.root.type.RootRoleType;

/**
 * ユーザ登録APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class UserEntryApiController
        extends BaseRootApiController<UserEntryApiRequest, UserEntryApiResponse> {

    /** 管理者サイトユーザログイン情報作成サービス */
    @Autowired
    private RootLoginInfoCreateService createService;
    /** SHA-256ハッシュ化 */
    @Sha256
    @Autowired
    private HashEncoder hashEncoder;

    /**
     * ユーザ登録処理
     *
     * @param request
     *     ユーザ登録APIリクエスト
     * @return ユーザ登録APIレスポンス
     * @throws BaseException
     *     ハッシュ化に失敗した場合
     */
    @PostMapping(value = "user/entry", produces = { MediaType.APPLICATION_JSON_VALUE })
    public UserEntryApiResponse entry(
            @RequestBody MultiValueMap<String, Object> request) throws BaseException {

        // TODO 妥当性チェックを追加

        RootLoginInfo entity = new RootLoginInfo();
        entity.setRole(RootRoleType.REF.getValue());
        entity.setPassword(hashEncoder.encode(request.get("password").toString(), ""));
        entity.setPasswordExpire(
                DateTimeUtil.addMonth(DateTimeUtil.getSysDate().toLocalDate(), 6));
        entity.setDeleteFlag("0");

        createService.create(entity);

        UserEntryApiResponse response = new UserEntryApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);
        response.setSeqLoginId(entity.getSeqRootLoginInfoId());

        return response;
    }

}
