package jp.co.ha.dashboard.inquiry.service;

import java.util.List;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.dashboard.inquiry.form.InquiryForm;
import jp.co.ha.db.entity.InquiryTypeMt;

/**
 * 問い合わせ情報サービスインターフェース
 * 
 * @version 1.0.0
 */
public interface InquiryService {

    /**
     * 問い合わせ種別リストを返す
     * 
     * @return 問い合わせ種別リスト
     */
    List<InquiryTypeMt> getInquiryTypeMtList();

    /**
     * 問い合わせ管理情報を登録する
     * 
     * @param seqUserId
     *     ユーザID
     * @param inquiryForm
     *     問い合わせForm
     * @throws BaseException
     *     問い合わせ情報の登録に失敗した場合
     */
    void regist(Long seqUserId, InquiryForm inquiryForm) throws BaseException;

    /**
     * 問い合わせ完了メールを送信する
     * 
     * @throws BaseException
     *     メール送信に失敗した場合
     */
    void sendMail() throws BaseException;

}
