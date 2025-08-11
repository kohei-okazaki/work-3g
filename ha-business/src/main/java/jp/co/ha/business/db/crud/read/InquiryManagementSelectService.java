package jp.co.ha.business.db.crud.read;

import java.util.List;

import jp.co.ha.common.db.SelectOption;
import jp.co.ha.db.entity.composite.CompositeInquiry;

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
    List<CompositeInquiry> findAll(SelectOption selectOption);

    /**
     * 問い合わせ管理情報の件数を返す
     * 
     * @return 件数
     */
    long count();

    /**
     * 指定した問い合わせ管理IDが存在するか判定する
     * 
     * @param seqInquiryMngId
     *     問い合わせ管理ID
     * @return 判定結果
     */
    boolean isExistBySeqInquiryMngId(long seqInquiryMngId);
}
