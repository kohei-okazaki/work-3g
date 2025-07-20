package jp.co.ha.dashboard.user.service.impl;

import java.util.Optional;

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
import jp.co.ha.business.healthInfo.type.GenderType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.io.encodeanddecode.Sha256HashEncoder;
import jp.co.ha.common.io.encodeanddecode.annotation.Sha256;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.dashboard.user.form.UserSettingForm;
import jp.co.ha.dashboard.user.service.UserSettingService;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.db.entity.User;
import jp.co.ha.db.entity.composite.CompositeUser;

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
    public UserSettingForm getForm(Long seqUserId) {

        // ユーザ情報取得
        Optional<CompositeUser> entity = userSearchService
                .findCompositUserById(seqUserId);

        UserSettingForm form = new UserSettingForm();
        BeanUtil.copy(entity.get(), form, (src, dest) -> {

            CompositeUser srcUser = (CompositeUser) src;
            UserSettingForm destForm = (UserSettingForm) dest;

            destForm.setPasswordExpire(DateTimeUtil
                    .toString(srcUser.getPasswordExpire(), DateFormatType.YYYYMMDD));
            destForm.setGenderType(GenderType.of(srcUser.getGenderType()).getValue());
            destForm.setBirthDate(DateTimeUtil
                    .toString(srcUser.getBirthDate(), DateFormatType.YYYYMMDD));
            destForm.setDeleteFlag(srcUser.isDeleteFlag() ? CommonFlag.TRUE.getValue()
                    : CommonFlag.FALSE.getValue());
            destForm.setHeaderFlag(srcUser.isHeaderFlag() ? CommonFlag.TRUE.getValue()
                    : CommonFlag.FALSE.getValue());
            destForm.setFooterFlag(srcUser.isFooterFlag() ? CommonFlag.TRUE.getValue()
                    : CommonFlag.FALSE.getValue());
            destForm.setMaskFlag(srcUser.isMaskFlag() ? CommonFlag.TRUE.getValue()
                    : CommonFlag.FALSE.getValue());
            destForm.setEnclosureCharFlag(
                    srcUser.isEnclosureCharFlag() ? CommonFlag.TRUE.getValue()
                            : CommonFlag.FALSE.getValue());
        });

        return form;
    }

    @Override
    public void execute(UserDto dto) throws BaseException {

        // トランザクション開始
        TransactionStatus status = transactionManager
                .getTransaction(defaultTransactionDefinition);

        try {

            // ユーザ情報を検索し、Dtoの内容をマージする
            User currentUser = userSearchService.findById(dto.getSeqUserId()).get();
            mergeUser(dto, currentUser);
            // ユーザ情報を更新する
            userUpdateService.update(currentUser);

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
    private void mergeUser(UserDto dto, User user) throws BaseException {

        BeanUtil.copy(dto, user, "seqUserId");
        user.setPasswordExpire(
                DateTimeUtil.toLocalDate(dto.getPasswordExpire(),
                        DateFormatType.YYYYMMDD_STRICT));
        user.setGenderType(GenderType.of(dto.getGenderType()).getIntValue());
        user.setBirthDate(DateTimeUtil.toLocalDate(dto.getBirthDate(),
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
