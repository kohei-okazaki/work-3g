package jp.co.ha.root.contents.job.response;

import java.time.LocalDateTime;
import java.util.List;

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
        private long jobInstanceId;
        /** 開始日時 */
        private LocalDateTime startTime;
        /** 終了日時 */
        private LocalDateTime endTime;
        /** ステータス */
        private String status;
        /** job名 */
        private String jobName;
        /** パラメータ名 */
        private String parameterName;
        /** パラメータ値 */
        private String parameterValue;

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
         * パラメータ名を返す
         * 
         * @return parameterName
         */
        public String getParameterName() {
            return parameterName;
        }

        /**
         * パラメータ名を設定する
         * 
         * @param parameterName
         *     パラメータ名
         */
        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }

        /**
         * パラメータ値を返す
         * 
         * @return parameterValue
         */
        public String getParameterValue() {
            return parameterValue;
        }

        /**
         * パラメータ値を設定する
         * 
         * @param parameterValue
         *     パラメータ値
         */
        public void setParameterValue(String parameterValue) {
            this.parameterValue = parameterValue;
        }
    }
}
