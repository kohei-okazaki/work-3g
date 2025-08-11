package jp.co.ha.business.db.crud.read;

import java.util.List;

import jp.co.ha.common.db.SelectOption;
import jp.co.ha.db.entity.InquiryTypeMt;

/**
 * 問い合わせ種別マスタ検索サービスインターフェース
 * 
 * @version 1.0.0
 */
public interface InquiryTypeMtSearchService {

    /**
     * 問い合わせ種別マスタの一覧を取得する
     * 
     * @param selectOption
     *     SelectOption
     * @return 問い合わせ種別マスタの一覧
     */
    List<InquiryTypeMt> findAll(SelectOption selectOption);
}
