package jp.co.ha.business.component;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.io.encodeanddecode.annotation.Sha256;
import jp.co.ha.common.util.DateTimeUtil;

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
                    "birthDate > システム日付のため、データ不正");
        }
        return Period.between(birthDate, sysDate).getYears();
    }

}
