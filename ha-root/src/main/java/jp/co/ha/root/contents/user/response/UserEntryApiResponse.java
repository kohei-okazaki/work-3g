package jp.co.ha.root.contents.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.root.base.BaseRootApiResponse;
import jp.co.ha.web.form.BaseApiResponse;

/**
 * ユーザ登録APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class UserEntryApiResponse extends BaseRootApiResponse implements BaseApiResponse {

    /** ログインID */
    @JsonProperty("seq_login_id")
    private Integer seqLoginId;

    /**
     * seqLoginIdを返す
     *
     * @return seqLoginId
     */
    public Integer getSeqLoginId() {
        return seqLoginId;
    }

    /**
     * seqLoginIdを設定する
     *
     * @param seqLoginId
     *     ログインID
     */
    public void setSeqLoginId(Integer seqLoginId) {
        this.seqLoginId = seqLoginId;
    }

}
