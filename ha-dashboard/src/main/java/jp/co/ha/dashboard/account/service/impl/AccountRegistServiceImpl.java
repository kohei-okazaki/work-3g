package jp.co.ha.dashboard.account.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import jp.co.ha.business.db.crud.create.AccountCreateService;
import jp.co.ha.business.db.crud.create.HealthInfoFileSettingCreateService;
import jp.co.ha.business.dto.AccountDto;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.system.annotation.Sha256;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.dashboard.account.service.AccountRegistService;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * アカウント作成サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class AccountRegistServiceImpl implements AccountRegistService {

    /** アカウント作成サービス */
    @Autowired
    private AccountCreateService accountCreateService;
    /** 健康情報ファイル設定作成サービス */
    @Autowired
    private HealthInfoFileSettingCreateService healthInfoFileSettingCreateService;
    /** ハッシュ値作成クラス */
    @Sha256
    @Autowired
    private HashEncoder encoder;
    /** トランザクション管理クラス */
    @Autowired
    private PlatformTransactionManager transactionManager;
    /** トランザクションクラス */
    @Autowired
    private DefaultTransactionDefinition defaultTransactionDefinition;

    /**
     * {@inheritDoc}
     */
    @Override
    public void regist(AccountDto dto) throws BaseException {

        // トランザクション開始
        TransactionStatus status = transactionManager
                .getTransaction(defaultTransactionDefinition);

        try {

            // アカウント情報を作成
            accountCreateService.create(toAccount(dto));
            // 健康情報ファイル設定情報を作成
            healthInfoFileSettingCreateService.create(toHealthInfoFileSetting(dto));

            // 正常にDB登録出来た場合、コミット
            transactionManager.commit(status);

        } catch (Exception e) {

            // 登録処理中にエラーが起きた場合、ロールバック
            transactionManager.rollback(status);
            throw e;
        }

    }

    /**
     * アカウント情報Entityに変換する
     *
     * @param dto
     *     アカウント登録DTO
     * @return アカウント情報Entity
     * @throws BaseException
     *     基底例外
     */
    private Account toAccount(AccountDto dto) throws BaseException {
        Account account = new Account();
        BeanUtil.copy(dto, account);
        account.setDeleteFlag(CommonFlag.FALSE.getValue());
        account.setPasswordExpire(DateUtil.addMonth(DateUtil.getSysDate(), 6));
        account.setApiKey(encoder.encode(dto.getPassword(), dto.getUserId()));
        return account;
    }

    /**
     * 健康情報ファイル設定Entityに変換する
     *
     * @param dto
     *     アカウント登録DTO
     * @return 健康情報ファイル設定Entity
     */
    private HealthInfoFileSetting toHealthInfoFileSetting(AccountDto dto) {
        HealthInfoFileSetting entity = new HealthInfoFileSetting();
        entity.setUserId(dto.getUserId());
        entity.setEnclosureCharFlag(CommonFlag.FALSE.getValue());
        entity.setHeaderFlag(CommonFlag.FALSE.getValue());
        entity.setFooterFlag(CommonFlag.FALSE.getValue());
        entity.setMaskFlag(CommonFlag.FALSE.getValue());
        return entity;
    }

}
