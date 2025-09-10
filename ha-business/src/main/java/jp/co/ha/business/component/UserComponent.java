package jp.co.ha.business.component;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import jp.co.ha.business.db.crud.create.HealthInfoFileSettingCreateService;
import jp.co.ha.business.db.crud.create.UserCreateService;
import jp.co.ha.business.db.crud.create.UserHealthGoalCreateService;
import jp.co.ha.business.db.crud.read.UserSearchService;
import jp.co.ha.business.dto.UserDto;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.healthInfo.type.GenderType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.io.encodeanddecode.annotation.Sha256;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.db.entity.User;
import jp.co.ha.db.entity.UserHealthGoal;

/**
 * ユーザ関連のComponent
 *
 * @version 1.0.0
 */
@Component
public class UserComponent {

    /** Sha256ハッシュ化 */
    @Sha256
    @Autowired
    private HashEncoder encoder;
    /** ユーザ検索サービス */
    @Autowired
    private UserSearchService userSearchService;
    /** ユーザ情報作成サービス */
    @Autowired
    private UserCreateService userCreateService;
    /** 健康情報ファイル設定作成サービス */
    @Autowired
    private HealthInfoFileSettingCreateService healthInfoFileSettingCreateService;
    /** ユーザ健康目標情報作成サービス */
    @Autowired
    private UserHealthGoalCreateService userHealthGoalCreateService;
    /** トランザクション管理クラス */
    @Autowired
    private PlatformTransactionManager transactionManager;
    /** トランザクション定義 */
    @Autowired
    @Qualifier("transactionDefinition")
    private DefaultTransactionDefinition transactionDefinition;

    /**
     * 指定したパスワードとメールアドレスからハッシュ化したパスワードを返す
     *
     * @param password
     *     パスワード
     * @param mailAddress
     *     メールアドレス
     * @return ハッシュ化されたパスワード
     * @throws BaseException
     *     ハッシュ化に失敗した場合
     */
    public String getHashPassword(String password, String mailAddress)
            throws BaseException {
        return encoder.encode(password, mailAddress);
    }

    /**
     * 年齢を返す
     * 
     * @param birthDate
     *     誕生日
     * @return 年齢
     * @throws BusinessException
     */
    public int getAge(LocalDate birthDate) throws BusinessException {

        LocalDate sysDate = DateTimeUtil.getSysDate().toLocalDate();

        if (birthDate.isAfter(sysDate)) {
            // 誕生日 > sydateの場合
            throw new BusinessException(CommonErrorCode.RUNTIME_ERROR,
                    "birthDate > システム日付のため、データ不正. birthDate=" + birthDate + ", sysDate="
                            + sysDate);
        }
        return Period.between(birthDate, sysDate).getYears();
    }

    /**
     * 指定したメールアドレスが有効なユーザとして存在するかどうか判定する<br>
     * <ul>
     * <li>存在する場合、true</li>
     * <li>存在しない場合、false</li>
     * </ul>
     *
     * @param mailAddress
     *     メールアドレス
     * @return 判定結果
     */
    public boolean isExistByMailAddress(String mailAddress) {
        return userSearchService.isExistByMailAddress(mailAddress);
    }

    /**
     * ユーザ関連情報の登録処理を行う
     *
     * @param dto
     *     ユーザDTO
     * @throws BaseException
     *     登録処理に失敗した場合
     */
    public void registUser(UserDto dto) throws BaseException {

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
            throw new BusinessException(CommonErrorCode.RUNTIME_ERROR, "ユーザ登録に失敗しました。",
                    e);
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

        user.setPassword(getHashPassword(dto.getPassword(),
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
     * @param dto
     *     ユーザ情報DTO
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
