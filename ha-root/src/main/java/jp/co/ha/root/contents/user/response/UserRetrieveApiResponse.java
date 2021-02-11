package jp.co.ha.root.contents.user.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.root.base.BaseRootApiResponse;
import jp.co.ha.web.form.BaseApiResponse;

/**
 * ユーザ情報取得APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class UserRetrieveApiResponse extends BaseRootApiResponse
        implements BaseApiResponse {

    /** ログインID */
    @JsonProperty("seq_login_id")
    private Integer seqLoginId;
    /** 権限リスト */
    @JsonProperty("roles")
    private List<String> roles;

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

    /**
     * rolesを返す
     *
     * @return roles
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * rolesを設定する
     *
     * @param roles
     *     権限リスト
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
