package jp.co.ha.common.crypt;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import jp.co.ha.common.BaseCommonTest;

/**
 * {@linkplain AesCrypter} のjUnit
 *
 * @version 1.0.0
 */
public class AesCrypterTest extends BaseCommonTest {

    /** Crypter */
    @Autowired
    @Qualifier("aesCrypter")
    private Crypter crypter;

    /**
     * 暗号化復号テスト
     */
    @Test
    public void cryptTest() {
        String value = "あいうえお12345";
        String encryptValue = crypter.encrypt(value);
        String decryptValue = crypter.decrypt(encryptValue);
        assertEquals(value, decryptValue);
    }

}
