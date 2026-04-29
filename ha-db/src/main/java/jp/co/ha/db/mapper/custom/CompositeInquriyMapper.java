package jp.co.ha.db.mapper.custom;

import java.util.List;

import jp.co.ha.db.entity.InquiryManagementExample;
import jp.co.ha.db.entity.custom.CompositeInquiry;

/**
 * 以下のテーブルの複合EntityのMapper
 * <ul>
 * <li>inquiry_management</li>
 * <li>inquiry_status_mt</li>
 * <li>inquiry_type_mt</li>
 * </ul>
 *
 * @version 1.0.0
 */
public interface CompositeInquriyMapper {

    /**
     * 問い合わせ情報の一覧を検索する
     * 
     * @param example
     *     InquiryManagementExample
     * @return 問い合わせ情報
     */
    List<CompositeInquiry> selectAll(InquiryManagementExample example);
}
