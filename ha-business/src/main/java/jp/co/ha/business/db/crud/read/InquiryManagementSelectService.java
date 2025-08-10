package jp.co.ha.business.db.crud.read;

import java.util.List;

import jp.co.ha.common.db.SelectOption;
import jp.co.ha.db.entity.InquiryManagement;

/**
 * 問い合わせ管理情報検索サービスインターフェース
 * 
 * @version 1.0.0
 */
public interface InquiryManagementSelectService {

    /**
     * 問い合わせ管理情報を全件検索する
     * 
     * @param selectOption
     *     検索オプション
     * @return 問い合わせ管理情報
     */
    List<InquiryManagement> findAll(SelectOption selectOption);
}
