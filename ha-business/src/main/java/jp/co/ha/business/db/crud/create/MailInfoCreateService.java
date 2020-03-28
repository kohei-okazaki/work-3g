package jp.co.ha.business.db.crud.create;

import jp.co.ha.db.entity.MailInfo;

/**
 * メール情報作成サービスインターフェース
 *
 * @version 1.0.0
 */
public interface MailInfoCreateService {

    /**
     * 指定したメール情報を登録する
     *
     * @param entity
     *     メール情報
     */
    void create(MailInfo entity);
}
