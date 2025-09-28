package jp.co.ha.root.contents.inquiry.response;

import jp.co.ha.common.web.form.BaseApiResponse;
import jp.co.ha.root.base.BaseRootApiResponse;

/**
 * 問い合わせ情報一覧通知APIレスポンス情報
 *
 * @version 1.0.0
 */
public class InquiryListNoticeResponse extends BaseRootApiResponse
        implements BaseApiResponse {

    /** 問い合わせ件数 */
    private long count;

    /**
     * 問い合わせ件数を返す
     *
     * @return count
     */
    public long getCount() {
        return count;
    }

    /**
     * 問い合わせ件数を設定する
     *
     * @param count
     *     問い合わせ件数
     */
    public void setCount(long count) {
        this.count = count;
    }

}
