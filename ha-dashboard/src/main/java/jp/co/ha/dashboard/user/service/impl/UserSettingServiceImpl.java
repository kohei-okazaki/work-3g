package jp.co.ha.dashboard.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import jp.co.ha.business.component.UserComponent;
import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.business.db.crud.read.UserSearchService;
import jp.co.ha.business.db.crud.read.impl.HealthInfoFileSettingSearchServiceImpl;
import jp.co.ha.business.db.crud.read.impl.UserSearchServiceImpl;
import jp.co.ha.business.db.crud.update.HealthInfoFileSettingUpdateService;
import jp.co.ha.business.db.crud.update.UserUpdateService;
import jp.co.ha.business.db.crud.update.impl.HealthInfoFileSettingUpdateServiceImpl;
import jp.co.ha.business.db.crud.update.impl.UserUpdateServiceImpl;
import jp.co.ha.business.dto.UserDto;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.io.encodeanddecode.Sha256HashEncoder;
import jp.co.ha.common.io.encodeanddecode.annotation.Sha256;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.dashboard.user.service.UserSettingService;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.db.entity.User;

/**
 * ユーザ設定サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class UserSettingServiceImpl implements UserSettingService {

    /** {@linkplain UserSearchServiceImpl} */
    @Autowired
    private UserSearchService userSearchService;
    /** {@linkplain UserUpdateServiceImpl} */
    @Autowired
    private UserUpdateService userUpdateService;
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
    /** {@linkplain UserComponent} */
    @Autowired
    private UserComponent userComponent;

    @Override
    public void execute(UserDto dto) throws BaseException {

        // トランザクション開始
        TransactionStatus status = transactionManager
                .getTransaction(defaultTransactionDefinition);

        try {

            // ユーザ情報を検索し、Dtoの内容をマージする
            User befUser = userSearchService.findById(dto.getSeqUserId()).get();
            mergeAccount(dto, befUser);
            // ユーザ情報を更新する
            userUpdateService.update(befUser);

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
     * ユーザDTOをユーザ情報にマージする
     *
     * @param dto
     *     ユーザDTO
     * @param user
     *     ユーザ情報
     * @throws BaseException
     *     基底例外
     */
    private void mergeAccount(UserDto dto, User user) throws BaseException {

        BeanUtil.copy(dto, user, "seqUserId");
        user.setPasswordExpire(
                DateTimeUtil.toLocalDate(dto.getPasswordExpire(),
                        DateFormatType.YYYYMMDD_STRICT));
        user.setPassword(
                userComponent.getHashPassword(dto.getPassword(),
                        dto.getMailAddress()));
    }

    /**
     * ユーザDTOを健康情報ファイル設定情報にマージする
     *
     * @param dto
     *     ユーザDTO
     * @param healthInfoFileSetting
     *     健康情報ファイル設定情報
     */
    private void mergeHealthInfoFileSetting(UserDto dto,
            HealthInfoFileSetting healthInfoFileSetting) {

        BeanUtil.copy(dto, healthInfoFileSetting, "seqUserId");
    }

}
