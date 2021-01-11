package jp.co.ha.root.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.io.encodeanddecode.annotation.Sha256;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * パスワードエンコードクラス<br>
 * SHA-256でのハッシュ化を行い、SpringSecurityで利用する
 *
 * @version 1.0.0
 */
@Service("passwordEncoderImpl")
public class PasswordEncoderImpl implements PasswordEncoder {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(PasswordEncoderImpl.class);
    /** SHA-256ハッシュ化 */
    @Sha256
    @Autowired
    private HashEncoder encoder;

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            String encValue = encoder.encode(rawPassword.toString(), "");
            return encValue;
        } catch (BaseException e) {
            LOG.error("パスワードのハッシュ化に失敗");
            return null;
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return this.encode(rawPassword).equals(encodedPassword);
    }

}
