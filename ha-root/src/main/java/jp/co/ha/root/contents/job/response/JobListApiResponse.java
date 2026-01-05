package jp.co.ha.root.contents.job.response;

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
 * Job履歴情報一覧取得APIレスポンス
 * 
 * @version 1.0.0
 */
public class JobListApiResponse extends BaseRootApiResponse implements BaseApiResponse {

    /** Job履歴情報リスト */
    @JsonProperty("job_list")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Job> jobList;

    /**
     * Job履歴情報リストを返す
     * 
     * @return jobList
     */
    public List<Job> getJobList() {
        return jobList;
    }

    /**
     * Job履歴情報リストを設定する
     * 
     * @param jobList
     *     Job履歴情報リスト
     */
    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

    /**
     * Job履歴
     * 
     * @version 1.0.0
     */
    public static class Job extends JsonEntity {

        /** jobInstanceId */
        @JsonProperty("job_instance_id")
        private long jobInstanceId;
        /** 開始日時 */
        @JsonProperty("start_time")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
        private LocalDateTime startTime;
        /** 終了日時 */
        @JsonProperty("end_time")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
        private LocalDateTime endTime;
        /** ステータス */
        @JsonProperty("status")
        private String status;
        /** job名 */
        @JsonProperty("job_name")
        private String jobName;
        /** jobパラメータリスト */
        @JsonProperty("parameter_list")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private List<JobParameter> parameterList;

        /**
         * jobInstanceIdを返す
         * 
         * @return jobInstanceId
         */
        public long getJobInstanceId() {
            return jobInstanceId;
        }

        /**
         * jobInstanceIdを設定する
         * 
         * @param jobInstanceId
         *     jobInstanceId
         */
        public void setJobInstanceId(long jobInstanceId) {
            this.jobInstanceId = jobInstanceId;
        }

        /**
         * 開始日時を返す
         * 
         * @return startTime
         */
        public LocalDateTime getStartTime() {
            return startTime;
        }

        /**
         * 開始日時を設定する
         * 
         * @param startTime
         *     開始日時
         */
        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        /**
         * 終了日時を返す
         * 
         * @return endTime
         */
        public LocalDateTime getEndTime() {
            return endTime;
        }

        /**
         * 終了日時を設定する
         * 
         * @param endTime
         *     終了日時
         */
        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }

        /**
         * ステータスを返す
         * 
         * @return status
         */
        public String getStatus() {
            return status;
        }

        /**
         * ステータスを設定する
         * 
         * @param status
         *     ステータス
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * job名を返す
         * 
         * @return jobName
         */
        public String getJobName() {
            return jobName;
        }

        /**
         * job名を設定する
         * 
         * @param jobName
         *     job名
         */
        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        /**
         * jobパラメータリストを返す
         * 
         * @return parameterList
         */
        public List<JobParameter> getParameterList() {
            return parameterList;
        }

        /**
         * jobパラメータリストを設定する
         * 
         * @param parameterList
         *     jobパラメータリスト
         */
        public void setParameterList(List<JobParameter> parameterList) {
            this.parameterList = parameterList;
        }

    }

    /**
     * Jobのパラメータクラス
     * 
     * @version 1.0.0
     */
    public static class JobParameter extends JsonEntity {

        /** パラメータ名 */
        @JsonProperty("name")
        private String name;
        /** パラメータ値 */
        @JsonProperty("value")
        private String value;

        /**
         * パラメータ名を返す
         * 
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * パラメータ名を設定する
         * 
         * @param name
         *     パラメータ名
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * パラメータ値を返す
         * 
         * @return value
         */
        public String getValue() {
            return value;
        }

        /**
         * パラメータ値を設定する
         * 
         * @param value
         *     パラメータ値
         */
        public void setValue(String value) {
            this.value = value;
        }

    }
}
