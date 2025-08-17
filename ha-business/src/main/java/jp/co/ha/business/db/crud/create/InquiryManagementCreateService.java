package jp.co.ha.business.db.crud.create;

import jp.co.ha.db.entity.InquiryManagement;

/**
 * 問い合わせ管理情報作成サービスインターフェース
 * 
 * @version 1.0.0
 */
public interface InquiryManagementCreateService {

    /**
     * 問い合わせ管理情報を登録する
     * 
     * @param entity
     *     問い合わせ管理情報
     */
    void create(InquiryManagement entity);

}
