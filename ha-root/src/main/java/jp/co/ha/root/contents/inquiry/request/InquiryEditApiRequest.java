package jp.co.ha.root.contents.inquiry.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.business.component.InquiryComponent.Status;
import jp.co.ha.common.validator.annotation.Min;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseApiRequest;
import jp.co.ha.root.base.BaseRootApiRequest;

/**
 * 問い合わせ情報編集APIリクエスト情報
 *
 * @version 1.0.0
 */
public class InquiryEditApiRequest extends BaseRootApiRequest implements BaseApiRequest {

    /** ステータス */
    @JsonProperty("status")
    @Required(message = "status is required")
    private Status status;

    /** 管理者サイトログイン情報ID */
    @JsonProperty("seq_login_id")
    @Required(message = "seq_login_id is required")
    @Min(size = 0, message = "seq_login_id is positive")
    private Long seqRootLoginInfoId;

    /**
     * ステータスを返す
     *
     * @return status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * ステータスを設定する
     *
     * @param status
     *     ステータス
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * 管理者サイトログイン情報IDを返す
     *
     * @return seqRootLoginInfoId
     */
    public Long getSeqRootLoginInfoId() {
        return seqRootLoginInfoId;
    }

    /**
     * 管理者サイトログイン情報IDを設定する
     *
     * @param seqRootLoginInfoId
     *     管理者サイトログイン情報ID
     */
    public void setSeqRootLoginInfoId(Long seqRootLoginInfoId) {
        this.seqRootLoginInfoId = seqRootLoginInfoId;
    }

}
