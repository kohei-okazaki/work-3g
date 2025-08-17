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
import jp.co.ha.business.db.crud.create.UserHealthGoalCreateService;
import jp.co.ha.business.dto.UserDto;
import jp.co.ha.business.healthInfo.type.GenderType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.io.encodeanddecode.annotation.Sha256;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.dashboard.user.service.UserRegistService;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.db.entity.User;
import jp.co.ha.db.entity.UserHealthGoal;

/**
 * ユーザ作成サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class UserRegistServiceImpl implements UserRegistService {

    /** ユーザ情報作成サービス */
    @Autowired
    private UserCreateService userCreateService;
    /** 健康情報ファイル設定作成サービス */
    @Autowired
    private HealthInfoFileSettingCreateService healthInfoFileSettingCreateService;
    /** ユーザ健康目標情報作成サービス */
    @Autowired
    private UserHealthGoalCreateService userHealthGoalCreateService;
    /** SHA-256Hashクラス */
    @Sha256
    @Autowired
    private HashEncoder encoder;
    /** ユーザ情報Component */
    @Autowired
    private UserComponent userComponent;
    /** トランザクション管理クラス */
    @Autowired
    private PlatformTransactionManager transactionManager;
    /** トランザクション定義 */
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
            // ユーザ情報を作成
            userCreateService.create(user);

            // 健康情報ファイル設定情報を作成
            healthInfoFileSettingCreateService.create(toHealthInfoFileSetting(user));

            // ユーザ健康目標情報を作成
            userHealthGoalCreateService.create(toGoal(user, dto));

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

        User user = new User();

        BeanUtil.copy(dto, user);

        user.setPassword(userComponent.getHashPassword(dto.getPassword(),
                dto.getMailAddress()));
        user.setDeleteFlag(CommonFlag.FALSE.get());
        user.setPasswordExpire(DateTimeUtil
                .addMonth(DateTimeUtil.toLocalDate(DateTimeUtil.getSysDate()), 6));
        user.setApiKey(encoder.encode(dto.getPassword(), DateTimeUtil.toString(
                DateTimeUtil.getSysDate(), DateFormatType.YYYYMMDDHHMMSS_NOSEP)));
        user.setGenderType(GenderType.of(dto.getGenderType()).getIntValue());

        return user;
    }

    /**
     * 健康情報ファイル設定Entityに変換する
     *
     * @param user
     *     ユーザ情報
     * @return 健康情報ファイル設定
     */
    private HealthInfoFileSetting toHealthInfoFileSetting(User user) {
        HealthInfoFileSetting entity = new HealthInfoFileSetting();
        entity.setSeqUserId(user.getSeqUserId());
        entity.setEnclosureCharFlag(CommonFlag.FALSE.get());
        entity.setHeaderFlag(CommonFlag.FALSE.get());
        entity.setFooterFlag(CommonFlag.FALSE.get());
        entity.setMaskFlag(CommonFlag.FALSE.get());
        return entity;
    }

    /**
     * ユーザ健康目標情報Entityに変換する
     * 
     * @param user
     *     ユーザ情報
     * @return ユーザ健康目標情報
     */
    private UserHealthGoal toGoal(User user, UserDto dto) {
        UserHealthGoal entity = new UserHealthGoal();
        entity.setSeqUserId(user.getSeqUserId());
        entity.setWeight(dto.getGoalWeight());
        entity.setDeleteFlag(CommonFlag.FALSE.get());
        return entity;
    }

}
