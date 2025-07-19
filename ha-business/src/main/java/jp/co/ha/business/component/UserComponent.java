package jp.co.ha.business.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.io.encodeanddecode.Sha256HashEncoder;
import jp.co.ha.common.io.encodeanddecode.annotation.Sha256;

/**
 * ユーザ関連のComponent
 *
 * @version 1.0.0
 */
@Component
public class UserComponent {

    /** {@linkplain Sha256HashEncoder} */
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

}
