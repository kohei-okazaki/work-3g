package jp.co.ha.business.db.crud.read;

import java.util.Optional;

import jp.co.ha.db.entity.MailInfo;

/**
 * メール情報検索サービスインターフェース
 *
 * @version 1.0.0
 */
public interface MailInfoSearchService {

    /**
     * ユーザIDからメール情報を取得する
     *
     * @param userId
     *     ユーザID
     * @return メール情報
     */
    Optional<MailInfo> findById(String userId);

}
