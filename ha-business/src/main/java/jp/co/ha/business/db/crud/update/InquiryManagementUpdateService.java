package jp.co.ha.business.db.crud.update;

import jp.co.ha.db.entity.InquiryManagement;

/**
 * 問い合わせ管理情報更新サービスインターフェース
 * 
 * @version 1.0.0
 */
public interface InquiryManagementUpdateService {

    /**
     * 問い合わせ管理情報のステータスを更新する
     * 
     * @param entity
     *     問い合わせ管理情報
     */
    void updateStatusById(InquiryManagement entity);

}
