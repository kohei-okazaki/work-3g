package jp.co.ha.root.contents.user.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.create.RootUserRoleDetailMtCreateService;
import jp.co.ha.business.db.crud.delete.RootUserRoleDetailMtDeleteService;
import jp.co.ha.business.db.crud.read.RootLoginInfoSearchService;
import jp.co.ha.business.db.crud.read.RootRoleMtSearchService;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.db.entity.RootRoleMt;
import jp.co.ha.db.entity.RootUserRoleDetailMt;
import jp.co.ha.db.entity.composite.CompositeRootUserInfo;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.base.BaseRootApiResponse.ErrorData;
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

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(UserEditApiController.class);

    /** 管理者サイトユーザログイン情報検索サービス */
    @Autowired
    private RootLoginInfoSearchService rootLoginInfoSearchService;
    /** 管理者サイトユーザ権限詳細マスタ削除サービス */
    @Autowired
    private RootUserRoleDetailMtDeleteService rootUserRoleDetailMtDeleteService;
    /** 管理者サイトユーザ権限詳細マスタ作成サービス */
    @Autowired
    private RootUserRoleDetailMtCreateService rootUserRoleDetailMtCreateService;
    /** 管理者サイト権限マスタ検索サービス */
    @Autowired
    private RootRoleMtSearchService rootRoleMtSearchService;
    /** トランザクション管理クラス */
    @Autowired
    private PlatformTransactionManager transactionManager;
    /** トランザクションクラス */
    @Autowired
    @Qualifier("transactionDefinition")
    private DefaultTransactionDefinition defaultTransactionDefinition;

    /**
     * 編集
     *
     * @param id
     *     ログインID
     * @param request
     *     ユーザ編集APIリクエスト
     * @return ユーザ編集APIレスポンス
     */
    @PutMapping(value = "user/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public UserEditApiResponse edit(
            @PathVariable(name = "id", required = false) Optional<String> id,
            @RequestBody UserEditApiRequest request) {
        // TODO 要実装

        Integer seqLoginId = Integer.valueOf(id.get());

        // トランザクション開始
        TransactionStatus status = transactionManager
                .getTransaction(defaultTransactionDefinition);

        try {

            List<CompositeRootUserInfo> userRoleList = rootLoginInfoSearchService
                    .findCompositeUserById(seqLoginId);
            // 管理者サイトユーザ権限管理マスタID
            Integer seqRootUserRoleMngMtId = userRoleList.get(0)
                    .getSeqRootUserRoleMngMtId();
            if (seqRootUserRoleMngMtId == null) {
                // エラーレスポンスを返却
                return getErrorResponse("data illegal error");
            }

            // ユーザの詳細マスタを削除
            rootUserRoleDetailMtDeleteService.deleteByUser(seqRootUserRoleMngMtId);

            // ユーザの詳細マスタを登録
            List<RootRoleMt> mtList = rootRoleMtSearchService
                    .findByRoles(request.getRoles()
                            .stream()
                            .map(e -> e.getValue())
                            .collect(Collectors.toList()));
            for (RootRoleMt roleMt : mtList) {
                RootUserRoleDetailMt mt = new RootUserRoleDetailMt();
                mt.setSeqRootUserRoleMngMtId(seqRootUserRoleMngMtId);
                mt.setSeqRootRoleMtId(roleMt.getSeqRootRoleMtId());
                rootUserRoleDetailMtCreateService.create(mt);
            }

            // 正常にDB登録出来た場合、コミット
            transactionManager.commit(status);

        } catch (Exception e) {

            LOG.error("データの更新に失敗しました", e);

            // 登録処理中にエラーが起きた場合、ロールバック
            transactionManager.rollback(status);

            // エラーレスポンスを返却
            return getErrorResponse("data update error");
        }

        UserEditApiResponse response = new UserEditApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);

        return response;
    }

    /**
     * エラーレスポンスを返す
     *
     * @param message
     *     エラーメッセージ
     * @return エラーレスポンス
     */
    private UserEditApiResponse getErrorResponse(String message) {
        UserEditApiResponse response = new UserEditApiResponse();
        response.setRootApiResult(RootApiResult.FAILURE);
        ErrorData errorData = new ErrorData();
        errorData.setMessage(message);
        response.setErrorData(errorData);
        return response;
    }
}
