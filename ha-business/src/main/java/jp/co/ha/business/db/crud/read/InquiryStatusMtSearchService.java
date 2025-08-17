package jp.co.ha.business.db.crud.read;

import java.util.List;

import jp.co.ha.common.db.SelectOption;
import jp.co.ha.db.entity.InquiryStatusMt;

/**
 * 問い合わせステータスマスタ検索サービスインターフェース
 * 
 * @version 1.0.0
 */
public interface InquiryStatusMtSearchService {

    /**
     * 問い合わせステータスマスタの一覧を取得する
     * 
     * @param selectOption
     *     SelectOption
     * @return 問い合わせステータスマスタの一覧
     */
    List<InquiryStatusMt> findAll(SelectOption selectOption);

}
