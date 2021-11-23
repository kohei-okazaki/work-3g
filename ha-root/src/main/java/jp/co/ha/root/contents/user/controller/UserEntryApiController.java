package jp.co.ha.root.contents.user.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.create.RootLoginInfoCreateService;
import jp.co.ha.business.db.crud.create.RootUserRoleDetailMtCreateService;
import jp.co.ha.business.db.crud.create.RootUserRoleMngMtCreateService;
import jp.co.ha.business.db.crud.create.impl.RootLoginInfoCreateServiceImpl;
import jp.co.ha.business.db.crud.create.impl.RootUserRoleDetailMtCreateServiceImpl;
import jp.co.ha.business.db.crud.create.impl.RootUserRoleMngMtCreateServiceImpl;
import jp.co.ha.business.db.crud.read.RootRoleMtSearchService;
import jp.co.ha.business.db.crud.read.impl.RootRoleMtSearchServiceImpl;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.io.encodeanddecode.Sha256HashEncoder;
import jp.co.ha.common.io.encodeanddecode.annotation.Sha256;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.db.entity.RootLoginInfo;
import jp.co.ha.db.entity.RootRoleMt;
import jp.co.ha.db.entity.RootUserRoleDetailMt;
import jp.co.ha.db.entity.RootUserRoleMngMt;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.user.request.UserEntryApiRequest;
import jp.co.ha.root.contents.user.response.UserEntryApiResponse;
import jp.co.ha.root.type.RootRoleType;

/**
 * ユーザ登録APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class UserEntryApiController
        extends BaseRootApiController<UserEntryApiRequest, UserEntryApiResponse> {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(UserEntryApiController.class);

    /** {@linkplain RootRoleMtSearchServiceImpl} */
    @Autowired
    private RootRoleMtSearchService rootRoleMtSearchService;
    /** {@linkplain RootUserRoleMngMtCreateServiceImpl} */
    @Autowired
    private RootUserRoleMngMtCreateService rootUserRoleMngMtCreateService;
    /** {@linkplain RootUserRoleDetailMtCreateServiceImpl} */
    @Autowired
    private RootUserRoleDetailMtCreateService rootUserRoleDetailMtCreateService;
    /** {@linkplain RootLoginInfoCreateServiceImpl} */
    @Autowired
    private RootLoginInfoCreateService createService;
    /** {@linkplain Sha256HashEncoder} */
    @Sha256
    @Autowired
    private HashEncoder hashEncoder;
    /** {@linkplain PlatformTransactionManager} */
    @Autowired
    private PlatformTransactionManager transactionManager;
    /** {@linkplain DefaultTransactionDefinition} */
    @Autowired
    @Qualifier("transactionDefinition")
    private DefaultTransactionDefinition defaultTransactionDefinition;

    /**
     * ユーザ登録処理
     *
     * @param request
     *     ユーザ登録APIリクエスト
     * @return ユーザ登録APIレスポンス
     * @throws BaseException
     *     ハッシュ化に失敗した場合
     */
    @PostMapping(value = "user", produces = { MediaType.APPLICATION_JSON_VALUE })
    public UserEntryApiResponse entry(@RequestBody UserEntryApiRequest request)
            throws BaseException {

        // TODO 妥当性チェックを追加

        // トランザクション開始
        TransactionStatus status = transactionManager
                .getTransaction(defaultTransactionDefinition);

        Long seqLoginId = null;
        try {

            // 照会権限のマスタを取得
            RootRoleMt RefRoleMt = rootRoleMtSearchService
                    .findByRoles(Arrays.asList(RootRoleType.REF.getValue())).get(0);

            // 管理者サイトユーザ権限管理マスタを登録
            RootUserRoleMngMt mngMt = new RootUserRoleMngMt();
            rootUserRoleMngMtCreateService.create(mngMt);

            // 管理者サイトユーザ権限詳細マスタを登録
            RootUserRoleDetailMt detailMt = new RootUserRoleDetailMt();
            detailMt.setSeqRootUserRoleMngMtId(mngMt.getSeqRootUserRoleMngMtId());
            detailMt.setSeqRootRoleMtId(RefRoleMt.getSeqRootRoleMtId());
            rootUserRoleDetailMtCreateService.create(detailMt);

            // 管理者サイトユーザログイン情報を登録
            RootLoginInfo entity = new RootLoginInfo();
            entity.setSeqRootUserRoleMngMtId(mngMt.getSeqRootUserRoleMngMtId());
            entity.setPassword(
                    hashEncoder.encode(request.getPassword(), ""));
            entity.setPasswordExpire(
                    DateTimeUtil.addMonth(DateTimeUtil.getSysDate().toLocalDate(), 6));
            entity.setDeleteFlag("0");

            createService.create(entity);

            // 正常にDB登録出来た場合、コミット
            transactionManager.commit(status);

            seqLoginId = entity.getSeqRootLoginInfoId();

        } catch (Exception e) {

            LOG.error("データの登録に失敗しました", e);
            // 登録処理中にエラーが起きた場合、ロールバック
            transactionManager.rollback(status);
            throw e;
        }

        UserEntryApiResponse response = getSuccessResponse();
        response.setSeqLoginId(seqLoginId);

        return response;
    }

    @Override
    protected UserEntryApiResponse getResponse() {
        return new UserEntryApiResponse();
    }

}
