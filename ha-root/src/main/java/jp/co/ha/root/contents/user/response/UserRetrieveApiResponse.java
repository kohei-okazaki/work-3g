package jp.co.ha.root.contents.user.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.root.base.BaseRootApiResponse;
import jp.co.ha.root.base.JsonEntity;
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
    private List<Role> roles;

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
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * rolesを設定する
     *
     * @param roles
     *     権限リスト
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * 権限情報
     *
     * @version 1.0.0
     */
    public static class Role extends JsonEntity {

        /** 値 */
        @JsonProperty("value")
        private String value;
        /** ラベル */
        @JsonProperty("label")
        private String label;

        /**
         * valueを返す
         *
         * @return value
         */
        public String getValue() {
            return value;
        }

        /**
         * valueを設定する
         *
         * @param value
         *     値
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * labelを返す
         *
         * @return label
         */
        public String getLabel() {
            return label;
        }

        /**
         * labelを設定する
         *
         * @param label
         *     ラベル
         */
        public void setLabel(String label) {
            this.label = label;
        }

    }

}
