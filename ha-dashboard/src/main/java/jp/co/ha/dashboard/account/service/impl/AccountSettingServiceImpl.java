package jp.co.ha.dashboard.account.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import jp.co.ha.business.component.AccountComponent;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.business.db.crud.read.impl.AccountSearchServiceImpl;
import jp.co.ha.business.db.crud.read.impl.HealthInfoFileSettingSearchServiceImpl;
import jp.co.ha.business.db.crud.update.AccountUpdateService;
import jp.co.ha.business.db.crud.update.HealthInfoFileSettingUpdateService;
import jp.co.ha.business.db.crud.update.impl.AccountUpdateServiceImpl;
import jp.co.ha.business.db.crud.update.impl.HealthInfoFileSettingUpdateServiceImpl;
import jp.co.ha.business.dto.AccountDto;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.io.encodeanddecode.Sha256HashEncoder;
import jp.co.ha.common.io.encodeanddecode.annotation.Sha256;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.dashboard.account.service.AccountSettingService;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * アカウント設定サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class AccountSettingServiceImpl implements AccountSettingService {

    /** {@linkplain AccountSearchServiceImpl} */
    @Autowired
    private AccountSearchService accountSearchService;
    /** {@linkplain AccountUpdateServiceImpl} */
    @Autowired
    private AccountUpdateService accountUpdateService;
    /** {@linkplain HealthInfoFileSettingSearchServiceImpl} */
    @Autowired
    private HealthInfoFileSettingSearchService healthInfoFileSettingSearchService;
    /** {@linkplain HealthInfoFileSettingUpdateServiceImpl} */
    @Autowired
    private HealthInfoFileSettingUpdateService healthInfoFileSettingUpdateService;
    /** {@linkplain PlatformTransactionManager} */
    @Autowired
    private PlatformTransactionManager transactionManager;
    /** {@linkplain DefaultTransactionDefinition} */
    @Autowired
    @Qualifier("transactionDefinition")
    private DefaultTransactionDefinition defaultTransactionDefinition;
    /** {@linkplain Sha256HashEncoder} */
    @Sha256
    @Autowired
    private HashEncoder encoder;
    /** {@linkplain AccountComponent} */
    @Autowired
    private AccountComponent accountComponent;

    @Override
    public void execute(AccountDto dto) throws BaseException {

        // トランザクション開始
        TransactionStatus status = transactionManager
                .getTransaction(defaultTransactionDefinition);

        try {

            // アカウント情報を検索し、Dtoの内容をマージする
            Account befAccount = accountSearchService.findById(dto.getSeqUserId()).get();
            mergeAccount(dto, befAccount);
            // アカウント情報を更新する
            accountUpdateService.update(befAccount);

            // 健康情報ファイル設定情報を検索し、Dtoの内容をマージする
            HealthInfoFileSetting healthInfoFileSetting = healthInfoFileSettingSearchService
                    .findById(dto.getSeqUserId()).get();
            mergeHealthInfoFileSetting(dto, healthInfoFileSetting);
            // 健康情報ファイル設定情報を更新する
            healthInfoFileSettingUpdateService.update(healthInfoFileSetting);

            // 正常にDB更新出来た場合、コミット
            transactionManager.commit(status);

        } catch (Exception e) {

            // 登録処理中にエラーが起きた場合、ロールバック
            transactionManager.rollback(status);
            throw e;
        }
    }

    /**
     * アカウントDTOをアカウント情報にマージする
     *
     * @param dto
     *     アカウントDTO
     * @param account
     *     アカウント情報
     * @throws BaseException
     *     基底例外
     */
    private void mergeAccount(AccountDto dto, Account account) throws BaseException {

        BeanUtil.copy(dto, account, Arrays.asList("seqUserId"));
        account.setPasswordExpire(
                DateTimeUtil.toLocalDate(dto.getPasswordExpire(),
                        DateFormatType.YYYYMMDD_STRICT));
        account.setPassword(
                accountComponent.getHashPassword(dto.getPassword(),
                        dto.getMailAddress()));
    }

    /**
     * アカウントDTOを健康情報ファイル設定情報にマージする
     *
     * @param dto
     *     アカウントDTO
     * @param healthInfoFileSetting
     *     健康情報ファイル設定情報
     */
    private void mergeHealthInfoFileSetting(AccountDto dto,
            HealthInfoFileSetting healthInfoFileSetting) {

        BeanUtil.copy(dto, healthInfoFileSetting, Arrays.asList("seqUserId"));
    }

}
