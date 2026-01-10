package jp.co.ha.root.contents.inquiry.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.PagingView;
import jp.co.ha.root.contents.inquiry.request.InquiryEditApiRequest;
import jp.co.ha.root.contents.inquiry.response.InquiryListApiResponse;

/**
 * 問い合わせサービスインターフェース
 * 
 * @version 1.0.0
 */
public interface InquiryService {

    /**
     * 問い合わせ情報リストを返す
     * 
     * @param pageable
     *     Pageable
     * @return 問い合わせ情報リスト
     * @throws BaseException
     *     S3からデータの取得に失敗した場合
     */
    List<InquiryListApiResponse.Inquiry> getInquiryList(Pageable pageable)
            throws BaseException;

    /**
     * PagingViewを返す
     * 
     * @param pageable
     *     PagingView
     * @return PagingView
     */
    PagingView getPagingView(Pageable pageable);

    /**
     * 指定された問い合わせ管理IDの問い合わせ情報が存在チェックを行う
     * 
     * @param seqInquriyMngId
     *     問い合わせ管理ID
     * @return 存在する場合true、それ以外の場合false
     */
    boolean existInquiryManagement(Long seqInquriyMngId);

    /**
     * 指定された問い合わせ管理IDの問い合わせ情報を更新する
     * 
     * @param seqInquriyMngId
     *     問い合わせ管理ID
     * @param request
     *     リクエスト情報
     */
    void updateStatusById(Long seqInquriyMngId, InquiryEditApiRequest request);

    /**
     * 指定された問い合わせステータスの問い合わせ情報の件数を返す
     * 
     * @param status
     *     問い合わせステータス
     * @return 件数
     */
    long countByStatus(String status);
}
