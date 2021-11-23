package jp.co.ha.root.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemRuntimeException;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.io.encodeanddecode.Sha256HashEncoder;
import jp.co.ha.common.io.encodeanddecode.annotation.Sha256;

/**
 * パスワードエンコードクラス<br>
 * SHA-256でのハッシュ化を行い、SpringSecurityで利用する
 *
 * @version 1.0.0
 */
@Service("passwordEncoderImpl")
public class PasswordEncoderImpl implements PasswordEncoder {

    /** {@linkplain Sha256HashEncoder} */
    @Sha256
    @Autowired
    private HashEncoder encoder;

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            return encoder.encode(rawPassword.toString(), "");
        } catch (BaseException e) {
            throw new SystemRuntimeException(e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return this.encode(rawPassword).equals(encodedPassword);
    }

}
