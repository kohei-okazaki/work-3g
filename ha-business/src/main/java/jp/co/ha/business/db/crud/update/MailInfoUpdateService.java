package jp.co.ha.business.db.crud.update;

import jp.co.ha.db.entity.MailInfo;

/**
 * メール情報更新サービスインターフェース
 *
 * @since 1.0
 */
public interface MailInfoUpdateService {

    /**
     * 指定したメール情報を更新する
     *
     * @param entity
     *     メール情報
     */
    void update(MailInfo entity);

}
