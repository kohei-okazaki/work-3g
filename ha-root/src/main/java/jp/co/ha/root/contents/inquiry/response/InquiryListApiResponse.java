package jp.co.ha.root.contents.inquiry.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jp.co.ha.common.web.form.BaseApiResponse;
import jp.co.ha.common.web.form.JsonEntity;
import jp.co.ha.root.base.BaseRootApiResponse;

/**
 * 問い合わせ情報一覧取得APIレスポンス情報
 *
 * @version 1.0.0
 */
public class InquiryListApiResponse extends BaseRootApiResponse
        implements BaseApiResponse {

    /** 問い合わせ情報リスト */
    @JsonProperty("inquriy_list")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Inquiry> inquriyList;

    /**
     * 問い合わせ情報リストを返す
     *
     * @return inquriyList
     */
    public List<Inquiry> getInquriyList() {
        return inquriyList;
    }

    /**
     * 問い合わせ情報リストを設定する
     *
     * @param inquriyList
     *     問い合わせ情報リスト
     */
    public void setInquriyList(List<Inquiry> inquriyList) {
        this.inquriyList = inquriyList;
    }

    /**
     * 問い合わせ情報
     *
     * @version 1.0.0
     */
    public static class Inquiry extends JsonEntity {

        /** 問い合わせ管理情報ID */
        @JsonProperty("seq_inquriy_mng_id")
        private long seqInquriyMngId;
        /** ユーザID */
        @JsonProperty("seq_user_id")
        private long seqUserId;
        /** 対応者ログインID */
        @JsonProperty("responder_login_id")
        private Long seqLoginId;
        /** 問い合わせステータス */
        @JsonProperty("status")
        private Status status;
        /** 問い合わせ種別 */
        @JsonProperty("type")
        private Type type;
        /** 問い合わせ件名 */
        @JsonProperty("title")
        private String title;
        /** 問い合わせ内容 */
        @JsonProperty("body")
        private String body;
        /** 登録日時 */
        @JsonProperty("reg_date")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
        private LocalDateTime regDate;
        /** 更新日時 */
        @JsonProperty("update_date")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
        private LocalDateTime updateDate;

        /**
         * 問い合わせ管理情報IDを返す
         *
         * @return seqInquriyMngId
         */
        public long getSeqInquriyMngId() {
            return seqInquriyMngId;
        }

        /**
         * 問い合わせ管理情報IDを設定する
         *
         * @param seqInquriyMngId
         *     問い合わせ管理情報ID
         */
        public void setSeqInquriyMngId(long seqInquriyMngId) {
            this.seqInquriyMngId = seqInquriyMngId;
        }

        /**
         * ユーザIDを返す
         *
         * @return seqUserId
         */
        public long getSeqUserId() {
            return seqUserId;
        }

        /**
         * ユーザIDを設定する
         *
         * @param seqUserId
         *     ユーザID
         */
        public void setSeqUserId(long seqUserId) {
            this.seqUserId = seqUserId;
        }

        /**
         * 対応者ログインIDを返す
         *
         * @return seqLoginId
         */
        public Long getSeqLoginId() {
            return seqLoginId;
        }

        /**
         * 対応者ログインIDを設定する
         *
         * @param seqLoginId
         *     対応者ログインID
         */
        public void setSeqLoginId(Long seqLoginId) {
            this.seqLoginId = seqLoginId;
        }

        /**
         * 問い合わせステータスを返す
         *
         * @return status
         */
        public Status getStatus() {
            return status;
        }

        /**
         * 問い合わせステータスを設定する
         *
         * @param status
         *     問い合わせステータス
         */
        public void setStatus(Status status) {
            this.status = status;
        }

        /**
         * 問い合わせ種別を返す
         *
         * @return type
         */
        public Type getType() {
            return type;
        }

        /**
         * 問い合わせ種別を設定する
         *
         * @param type
         *     問い合わせ種別
         */
        public void setType(Type type) {
            this.type = type;
        }

        /**
         * 問い合わせ内容を返す
         *
         * @return title
         */
        public String getTitle() {
            return title;
        }

        /**
         * 問い合わせ内容を設定する
         *
         * @param title
         *     問い合わせ内容
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * 問い合わせ内容を返す
         *
         * @return body
         */
        public String getBody() {
            return body;
        }

        /**
         * 問い合わせ内容を設定する
         *
         * @param body
         *     問い合わせ内容
         */
        public void setBody(String body) {
            this.body = body;
        }

        /**
         * 登録日時を返す
         *
         * @return regDate
         */
        public LocalDateTime getRegDate() {
            return regDate;
        }

        /**
         * 登録日時を設定する
         *
         * @param regDate
         *     登録日時
         */
        public void setRegDate(LocalDateTime regDate) {
            this.regDate = regDate;
        }

        /**
         * 更新日時を返す
         *
         * @return updateDate
         */
        public LocalDateTime getUpdateDate() {
            return updateDate;
        }

        /**
         * 更新日時を設定する
         *
         * @param updateDate
         *     更新日時
         */
        public void setUpdateDate(LocalDateTime updateDate) {
            this.updateDate = updateDate;
        }

    }

    /**
     * 問い合わせステータス情報
     *
     * @version 1.0.0
     */
    public static class Status extends JsonEntity {

        /** 表示名 */
        @JsonProperty("label")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String label;
        /** 値 */
        @JsonProperty("value")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String value;

        /**
         * 表示名を返す
         *
         * @return label
         */
        public String getLabel() {
            return label;
        }

        /**
         * 表示名を設定する
         *
         * @param label
         *     表示名
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

    /**
     * 問い合わせタイプ情報
     *
     * @version 1.0.0
     */
    public static class Type extends JsonEntity {

        /** 表示名 */
        @JsonProperty("label")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String label;
        /** 値 */
        @JsonProperty("value")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String value;

        /**
         * 表示名を返す
         *
         * @return label
         */
        public String getLabel() {
            return label;
        }

        /**
         * 表示名を設定する
         *
         * @param label
         *     表示名
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
