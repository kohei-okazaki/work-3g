package jp.co.ha.root.contents.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import jp.co.ha.business.db.crud.update.RootLoginInfoUpdateService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.io.encodeanddecode.annotation.Sha256;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.db.entity.RootLoginInfo;
import jp.co.ha.db.entity.RootRoleMt;
import jp.co.ha.db.entity.RootUserRoleDetailMt;
import jp.co.ha.db.entity.composite.CompositeRootUserInfo;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.user.request.UserEditApiRequest;
import jp.co.ha.root.contents.user.response.UserEditApiResponse;

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
    /** 管理者サイトユーザログイン情報更新サービス */
    @Autowired
    private RootLoginInfoUpdateService rootLoginInfoUpdateService;
    /** 管理者サイトユーザ権限詳細マスタ削除サービス */
    @Autowired
    private RootUserRoleDetailMtDeleteService rootUserRoleDetailMtDeleteService;
    /** 管理者サイトユーザ権限詳細マスタ作成サービス */
    @Autowired
    private RootUserRoleDetailMtCreateService rootUserRoleDetailMtCreateService;
    /** 管理者サイト権限マスタ検索サービス */
    @Autowired
    private RootRoleMtSearchService rootRoleMtSearchService;
    /** SHA-256ハッシュ化 */
    @Sha256
    @Autowired
    private HashEncoder hashEncoder;
    /** トランザクション管理クラス */
    @Autowired
    private PlatformTransactionManager transactionManager;
    /** トランザクション定義 */
    @Autowired
    @Qualifier("transactionDefinition")
    private DefaultTransactionDefinition defaultTransactionDefinition;

    /**
     * 編集
     *
     * @param seqLoginId
     *     ログインID
     * @param request
     *     ユーザ編集APIリクエスト
     * @return ユーザ編集APIレスポンス
     */
    @PutMapping(value = "user/{seq_login_id}", produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserEditApiResponse> edit(
            @PathVariable(name = "seq_login_id", required = true) Long seqLoginId,
            @Valid @RequestBody UserEditApiRequest request) {

        // トランザクション開始
        TransactionStatus status = transactionManager
                .getTransaction(defaultTransactionDefinition);

        try {

            List<CompositeRootUserInfo> userRoleList = rootLoginInfoSearchService
                    .findCompositeUserById(seqLoginId);
            if (CollectionUtil.isEmpty(userRoleList)) {
                // 登録データが存在しない場合
                return ResponseEntity.badRequest()
                        .body(getErrorResponse("data illegal error"));
            }

            // 管理者サイトユーザ権限管理マスタID
            Long seqRootUserRoleMngMtId = userRoleList.get(0)
                    .getSeqRootUserRoleMngMtId();

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

            // 管理者サイトユーザログイン情報を更新
            // 結合しているので権限情報以外はここから取得する
            CompositeRootUserInfo userRole = userRoleList.get(0);
            updateRootLoginInfo(userRole, request);

            // 正常にDB登録出来た場合、コミット
            transactionManager.commit(status);

            return ResponseEntity.ok(getSuccessResponse());

        } catch (Exception e) {

            LOG.error("データの更新に失敗しました", e);

            // 登録処理中にエラーが起きた場合、ロールバック
            transactionManager.rollback(status);

            // エラーレスポンスを返却
            return ResponseEntity.badRequest()
                    .body(getErrorResponse("data update error"));
        }
    }

    @Override
    protected UserEditApiResponse getResponse() {
        return new UserEditApiResponse();
    }

    /**
     * 管理者サイト用ログイン情報を更新する
     *
     * @param beforeData
     *     更新前データ
     * @param request
     *     ユーザ編集APIリクエスト
     * @throws BaseException
     *     パスワードの更新に失敗した場合
     */
    private void updateRootLoginInfo(CompositeRootUserInfo beforeData,
            UserEditApiRequest request) throws BaseException {

        RootLoginInfo entity = new RootLoginInfo();
        BeanUtil.copy(beforeData, entity);
        BeanUtil.copy(request, entity);

        if (request.getPassword() != null) {
            entity.setPassword(hashEncoder.encode(request.getPassword(), ""));
        }
        if (request.isDeleteFlag()) {
            entity.setDeleteFlag(CommonFlag.TRUE.get());
        }
        rootLoginInfoUpdateService.update(entity);
    }

}
