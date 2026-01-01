package jp.co.ha.common.db;

/**
 * Entityの暗号/復号インターフェース
 *
 * @version 1.0.0
 */
public interface EntityCrypter {

    /**
     * 指定されたEntityクラスの暗号化を行う
     *
     * @param entity
     *     Entityクラス
     */
    void encrypt(Object entity);

    /**
     * 指定されたEntityクラスの復号を行う
     *
     * @param entity
     *     Entityクラス
     */
    void decrypt(Object entity);
}
