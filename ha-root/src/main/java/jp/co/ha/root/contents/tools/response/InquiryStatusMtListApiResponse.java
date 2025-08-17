package jp.co.ha.root.contents.tools.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.web.form.BaseApiResponse;
import jp.co.ha.common.web.form.JsonEntity;
import jp.co.ha.root.base.BaseRootApiResponse;

/**
 * 問い合わせステータスマスタリスト取得APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class InquiryStatusMtListApiResponse extends BaseRootApiResponse
        implements BaseApiResponse {

    /** 問い合わせステータスリスト */
    @JsonProperty("statuses")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Status> statuses;

    /**
     * 問い合わせステータスリストを返す
     *
     * @return statuses
     */
    public List<Status> getStatuses() {
        return statuses;
    }

    /**
     * 問い合わせステータスリストを設定する
     *
     * @param statuses
     *     問い合わせステータスリスト
     */
    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    /**
     * 問い合わせステータス情報
     *
     * @version 1.0.0
     */
    public static class Status extends JsonEntity {

        /** ステータス名 */
        @JsonProperty("label")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String label;
        /** 値 */
        @JsonProperty("value")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String value;

        /**
         * ステータス名を返す
         *
         * @return 権限名
         */
        public String getLabel() {
            return label;
        }

        /**
         * ステータス名を設定する
         *
         * @param label
         *     ステータス名
         */
        public void setLabel(String label) {
            this.label = label;
        }

        /**
         * 値を返す
         *
         * @return value
         */
        public String getValue() {
            return value;
        }

        /**
         * 値を設定する
         *
         * @param value
         *     値
         */
        public void setValue(String value) {
            this.value = value;
        }

    }
}
