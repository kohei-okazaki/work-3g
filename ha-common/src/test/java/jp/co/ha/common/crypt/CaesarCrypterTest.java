package jp.co.ha.common.crypt;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import jp.co.ha.common.BaseCommonTest;

/**
 * {@linkplain CaesarCrypter} のjUnit
 *
 * @version 1.0.0
 */
public class CaesarCrypterTest extends BaseCommonTest {

    /** Crypter */
    @Autowired
    @Qualifier("caesarCrypter")
    private Crypter crypter;

    /**
     * 暗号化復号テスト
     */
    @Test
    public void cryptTest() {
        String value = "abcdef";
        String encryptValue = crypter.encrypt(value);
        String decryptValue = crypter.decrypt(encryptValue);
        assertEquals(value, decryptValue);
    }

}
