package jp.co.ha.business.api.node.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.web.form.BaseApiRequest;

/**
 * Token発行APIのリクエストクラス
 *
 * @version 1.0.0
 */
public class TokenApiRequest extends BaseNodeApiRequest implements BaseApiRequest {

    /** ユーザID */
    @JsonProperty("seq_user_id")
    private Long seqUserId;

    /**
     * ユーザIDを返す
     *
     * @return seqUserId
     */
    public Long getSeqUserId() {
        return seqUserId;
    }

    /**
     * ユーザIDを設定する
     *
     * @param seqUserId
     */
    public void setSeqUserId(Long seqUserId) {
        this.seqUserId = seqUserId;
    }

}
