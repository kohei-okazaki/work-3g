package jp.co.ha.common.io.encodeanddecode;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.type.Algorithm;
import jp.co.ha.common.type.Charset;

/**
 * ハッシュ化処理インターフェース
 *
 * @version 1.0.0
 */
public interface HashEncoder {

    /**
     * ハッシュ化を行う
     *
     * @param password
     *     パスワード
     * @param salt
     *     ソルト
     * @return パスワード
     * @throws BaseException
     *     基底例外
     */
    String encode(String password, String salt) throws BaseException;

    /**
     * デフォルトで指定した値からハッシュ化した値を返す
     *
     * @param password
     *     パスワード
     * @param salt
     *     ソルト
     * @param algorithm
     *     アルゴリズム
     * @return ハッシュ化後の文字列
     * @throws NoSuchAlgorithmException
     *     指定したアルゴリズムが存在しない場合
     * @throws UnsupportedEncodingException
     *     指定した文字コードが存在しない場合
     */
    public default String encodeDefault(String password, String salt, Algorithm algorithm)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messsageDigest = MessageDigest.getInstance(algorithm.getValue());
        String str = password + salt;
        messsageDigest.update(str.getBytes(Charset.UTF_8.getValue()));
        byte[] cipherByte = messsageDigest.digest();
        StringBuilder sb = new StringBuilder(2 * cipherByte.length);
        for (byte b : cipherByte) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

}
