package jp.co.ha.common.io.encodeanddecode;

import static jp.co.ha.common.exception.CommonErrorCode.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.type.Algorithm;
import jp.co.ha.common.type.Charset;

/**
 * ハッシュ化処理基底クラス
 *
 * @version 1.0.0
 */
public abstract class HashEncoder {

    /**
     * ハッシュ化した値を返す
     *
     * @param password
     *     パスワード
     * @param salt
     *     ソルト
     * @return ハッシュ化後の文字列
     * @throws BaseException
     *     指定したアルゴリズムが存在しない場合
     */
    public String encode(String password, String salt) throws BaseException {

        try {

            MessageDigest messsageDigest = MessageDigest
                    .getInstance(getAlgorithm().getValue());
            String str = password + salt;
            messsageDigest.update(str.getBytes(Charset.UTF_8.getValue()));
            byte[] cipherByte = messsageDigest.digest();
            StringBuilder sb = new StringBuilder(2 * cipherByte.length);
            for (byte b : cipherByte) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new SystemException(ALGORITH_ERROR, e);
        } catch (UnsupportedEncodingException e) {
            throw new SystemException(UNEXPECTED_ERROR, e);
        }

    }

    /**
     * {@linkplain Algorithm}を返す
     *
     * @return {@linkplain Algorithm}
     */
    protected abstract Algorithm getAlgorithm();

}
