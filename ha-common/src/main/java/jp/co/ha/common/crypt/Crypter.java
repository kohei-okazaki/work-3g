package jp.co.ha.common.crypt;

/**
 * 暗号化復号インターフェース
 *
 * @version 1.0.0
 */
public interface Crypter {

    /**
     * 暗号化処理を行う
     *
     * @param str
     *     暗号化したい文字列
     * @return 暗号化後文字列
     */
    String encrypt(String str);

    /**
     * 復号処理を行う
     *
     * @param str
     *     復号したい文字列
     * @return 復号後文字列
     */
    String decrypt(String str);
}
