package jp.co.ha.root.contents.tools.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.web.form.BaseApiResponse;
import jp.co.ha.common.web.form.JsonEntity;
import jp.co.ha.root.base.BaseRootApiResponse;

/**
 * 権限マスタリスト取得APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class RoleMtListApiResponse extends BaseRootApiResponse
        implements BaseApiResponse {

    /** 権限リスト */
    @JsonProperty("roles")
    private List<Role> roles;

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
     * ユーザ権限情報
     *
     * @version 1.0.0
     */
    public static class Role extends JsonEntity {

        /** 権限名 */
        @JsonProperty("label")
        private String label;
        /** 値 */
        @JsonProperty("value")
        private String value;

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
         *     権限名
         */
        public void setLabel(String label) {
            this.label = label;
        }

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

    }
}
