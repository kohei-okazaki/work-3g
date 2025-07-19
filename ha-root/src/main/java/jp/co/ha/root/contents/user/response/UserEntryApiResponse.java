package jp.co.ha.root.contents.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.web.form.BaseApiResponse;
import jp.co.ha.root.base.BaseRootApiResponse;

/**
 * ユーザ登録APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class UserEntryApiResponse extends BaseRootApiResponse implements BaseApiResponse {

    /** ログインID */
    @JsonProperty("seq_login_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long seqLoginId;

    /**
     * seqLoginIdを返す
     *
     * @return seqLoginId
     */
    public Long getSeqLoginId() {
        return seqLoginId;
    }

    /**
     * seqLoginIdを設定する
     *
     * @param seqLoginId
     *     ログインID
     */
    public void setSeqLoginId(Long seqLoginId) {
        this.seqLoginId = seqLoginId;
    }

}
