package jp.co.ha.dashboard.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import jp.co.ha.business.component.UserComponent;
import jp.co.ha.business.db.crud.create.HealthInfoFileSettingCreateService;
import jp.co.ha.business.db.crud.create.UserCreateService;
import jp.co.ha.business.db.crud.create.impl.HealthInfoFileSettingCreateServiceImpl;
import jp.co.ha.business.db.crud.create.impl.UserCreateServiceImpl;
import jp.co.ha.business.dto.UserDto;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.io.encodeanddecode.Sha256HashEncoder;
import jp.co.ha.common.io.encodeanddecode.annotation.Sha256;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.dashboard.user.service.UserRegistService;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.db.entity.User;

/**
 * ユーザ作成サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class UserRegistServiceImpl implements UserRegistService {

    /** {@linkplain UserCreateServiceImpl} */
    @Autowired
    private UserCreateService userCreateService;
    /** {@linkplain HealthInfoFileSettingCreateServiceImpl} */
    @Autowired
    private HealthInfoFileSettingCreateService healthInfoFileSettingCreateService;
    /** {@linkplain Sha256HashEncoder} */
    @Sha256
    @Autowired
    private HashEncoder encoder;
    /** {@linkplain UserComponent} */
    @Autowired
    private UserComponent userComponent;
    /** {@linkplain PlatformTransactionManager} */
    @Autowired
    private PlatformTransactionManager transactionManager;
    /** {@linkplain DefaultTransactionDefinition} */
    @Autowired
    @Qualifier("transactionDefinition")
    private DefaultTransactionDefinition transactionDefinition;

    @Override
    public void regist(UserDto dto) throws BaseException {

        // トランザクション開始
        TransactionStatus status = transactionManager
                .getTransaction(transactionDefinition);

        try {

            // ユーザ情報を作成
            User user = toUser(dto);
            userCreateService.create(user);
            // 健康情報ファイル設定情報を作成
            healthInfoFileSettingCreateService.create(toHealthInfoFileSetting(user));

            // 正常にDB登録出来た場合、コミット
            transactionManager.commit(status);

        } catch (Exception e) {

            // 登録処理中にエラーが起きた場合、ロールバック
            transactionManager.rollback(status);
            throw e;
        }

    }

    /**
     * ユーザ情報Entityに変換する
     *
     * @param dto
     *     ユーザ登録DTO
     * @return ユーザ情報Entity
     * @throws BaseException
     *     基底例外
     */
    private User toUser(UserDto dto) throws BaseException {
        User account = new User();
        BeanUtil.copy(dto, account);
        account.setPassword(userComponent.getHashPassword(dto.getPassword(),
                dto.getMailAddress()));
        account.setDeleteFlag(CommonFlag.FALSE.get());
        account.setPasswordExpire(DateTimeUtil
                .addMonth(DateTimeUtil.toLocalDate(DateTimeUtil.getSysDate()), 6));
        account.setApiKey(encoder.encode(dto.getPassword(), DateTimeUtil.toString(
                DateTimeUtil.getSysDate(), DateFormatType.YYYYMMDDHHMMSS_NOSEP)));
        return account;
    }

    /**
     * 健康情報ファイル設定Entityに変換する
     *
     * @param account
     *     ユーザ登録
     * @return 健康情報ファイル設定
     */
    private HealthInfoFileSetting toHealthInfoFileSetting(User account) {
        HealthInfoFileSetting entity = new HealthInfoFileSetting();
        entity.setSeqUserId(account.getSeqUserId());
        entity.setEnclosureCharFlag(CommonFlag.FALSE.get());
        entity.setHeaderFlag(CommonFlag.FALSE.get());
        entity.setFooterFlag(CommonFlag.FALSE.get());
        entity.setMaskFlag(CommonFlag.FALSE.get());
        return entity;
    }

}
