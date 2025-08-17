package jp.co.ha.common.crypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.common.db.CryptProperties;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.Algorithm;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.util.StringUtil;

/**
 * AES可逆暗号化クラス
 *
 * @version 1.0.0
 */
@Component("aesCrypter")
public class AesCrypter implements Crypter {

    /** LOG */
    private static Logger LOG = LoggerFactory.getLogger(AesCrypter.class);
    /** 暗号化設定ファイル情報 */
    @Autowired
    private CryptProperties cryptConfig;

    @Override
    public String encrypt(String str) {

        if (StringUtil.isEmpty(str)) {
            return str;
        }

        try {

            byte[] input = str.getBytes(Charset.UTF_8.getValue());
            byte[] encrypted = getCipher(Cipher.ENCRYPT_MODE).doFinal(input);
            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            LOG.error("暗号化処理が失敗しました", e);
            return null;
        }
    }

    @Override
    public String decrypt(String str) {

        if (StringUtil.isEmpty(str)) {
            return str;
        }

        try {

            byte[] input = Base64.getDecoder().decode(str);
            byte[] decrypted = getCipher(Cipher.DECRYPT_MODE).doFinal(input);
            return new String(decrypted, Charset.UTF_8.getValue());

        } catch (Exception e) {
            LOG.error("復号処理が失敗しました", e);
            return null;
        }
    }

    /**
     * {@linkplain Cipher}を返す
     *
     * @param mode
     *     モード
     * @return Cipher
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    private Cipher getCipher(int mode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

        SecretKeySpec sks = new SecretKeySpec(getKey(), Algorithm.AES.getValue());
        Cipher c = Cipher.getInstance(cryptConfig.getMode());
        c.init(mode, sks);

        return c;
    }

    /**
     * 秘密鍵を返す
     *
     * @return 秘密鍵
     */
    private byte[] getKey() {
        try {
            return cryptConfig.getKey().getBytes(Charset.UTF_8.getValue());
        } catch (UnsupportedEncodingException e) {
            // 文字コードはUTF-8なので発生しない想定
            return null;
        }
    }

}
