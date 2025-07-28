package jp.co.ha.dashboard.user.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import jp.co.ha.business.component.UserComponent;
import jp.co.ha.business.db.crud.create.UserHealthGoalCreateService;
import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.business.db.crud.read.UserHealthGoalSelectService;
import jp.co.ha.business.db.crud.read.UserSearchService;
import jp.co.ha.business.db.crud.update.HealthInfoFileSettingUpdateService;
import jp.co.ha.business.db.crud.update.UserHealthGoalUpdateService;
import jp.co.ha.business.db.crud.update.UserUpdateService;
import jp.co.ha.business.dto.UserDto;
import jp.co.ha.business.healthInfo.type.GenderType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.dashboard.user.form.UserSettingForm;
import jp.co.ha.dashboard.user.service.UserSettingService;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.db.entity.User;
import jp.co.ha.db.entity.UserHealthGoal;
import jp.co.ha.db.entity.composite.CompositeUser;

/**
 * ユーザ設定サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class UserSettingServiceImpl implements UserSettingService {

    /** ユーザ情報検索サービス */
    @Autowired
    private UserSearchService userSearchService;
    /** ユーザ情報更新サービス */
    @Autowired
    private UserUpdateService userUpdateService;
    /** 健康情報ファイル設定検索サービス */
    @Autowired
    private HealthInfoFileSettingSearchService healthInfoFileSettingSearchService;
    /** 健康情報ファイル設定更新サービス */
    @Autowired
    private HealthInfoFileSettingUpdateService healthInfoFileSettingUpdateService;
    /** ユーザ健康目標情報検索サービス */
    @Autowired
    private UserHealthGoalSelectService userHealthGoalSelectService;
    /** ユーザ健康目標情報作成サービス */
    @Autowired
    private UserHealthGoalCreateService userHealthGoalCreateService;
    /** ユーザ健康目標情報更新サービス */
    @Autowired
    private UserHealthGoalUpdateService userHealthGoalUpdateService;
    /** トランザクション管理クラス */
    @Autowired
    private PlatformTransactionManager transactionManager;
    /** トランザクション定義 */
    @Autowired
    @Qualifier("transactionDefinition")
    private DefaultTransactionDefinition defaultTransactionDefinition;
    /** ユーザ情報Component */
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

            destForm.setGenderType(GenderType.of(srcUser.getGenderType()).getValue());
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

            // ユーザ情報を更新する
            updateUser(dto);

            // 健康情報ファイル設定情報を更新する
            updateHealthInfoFileSetting(dto);

            // ユーザ健康目標情報を更新する
            updateUserHealthGoal(dto);

            // 正常にDB更新出来た場合、コミット
            transactionManager.commit(status);

        } catch (Exception e) {

            // 登録処理中にエラーが起きた場合、ロールバック
            transactionManager.rollback(status);
            throw e;
        }
    }

    /**
     * ユーザ情報を更新する
     * 
     * @param dto
     *     ユーザ情報DTO
     * @throws BaseException
     *     基底例外
     */
    private void updateUser(UserDto dto) throws BaseException {

        // ユーザ情報を検索
        User currentUser = userSearchService.findById(dto.getSeqUserId()).get();

        BeanUtil.copy(dto, currentUser, "seqUserId", "reg_date");
        currentUser.setGenderType(GenderType.of(dto.getGenderType()).getIntValue());
        currentUser.setPassword(
                userComponent.getHashPassword(dto.getPassword(),
                        dto.getMailAddress()));

        // ユーザ情報を更新する
        userUpdateService.update(currentUser);
    }

    /**
     * 健康情報ファイル設定情報を更新する
     * 
     * @param dto
     *     ユーザ情報DTO
     */
    private void updateHealthInfoFileSetting(UserDto dto) {

        // 健康情報ファイル設定情報を検索
        HealthInfoFileSetting healthInfoFileSetting = healthInfoFileSettingSearchService
                .findById(dto.getSeqUserId()).get();

        BeanUtil.copy(dto, healthInfoFileSetting, "seqUserId", "reg_date");

        // 健康情報ファイル設定情報を更新する
        healthInfoFileSettingUpdateService.update(healthInfoFileSetting);
    }

    /**
     * ユーザ健康目標情報を更新する<br>
     * PキーがユーザIDでないため、検索しPキー取得後論理削除する<br>
     * その後新規登録を行う
     * 
     * @param dto
     *     ユーザ情報DTO
     */
    private void updateUserHealthGoal(UserDto dto) {

        // Pキー取得のため、検索
        UserHealthGoal currentGoal = userHealthGoalSelectService
                .findEnableById(dto.getSeqUserId())
                .get();

        // 論理削除
        userHealthGoalUpdateService.updateDeleteFlag(currentGoal);

        // 登録
        UserHealthGoal goal = new UserHealthGoal();
        goal.setSeqUserId(dto.getSeqUserId());
        goal.setWeight(dto.getGoalWeight());
        goal.setDeleteFlag(CommonFlag.FALSE.get());

        userHealthGoalCreateService.create(goal);

    }

}
