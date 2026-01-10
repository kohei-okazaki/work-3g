package jp.co.ha.business.io.file.csv.model;

import jp.co.ha.common.io.file.csv.model.BaseCsvModel;

/**
 * 日次バッチ起動ログデータ分析連携CSV Model
 *
 * @version 1.0.0
 */
public class DailyBatchLogCsvModel implements BaseCsvModel {

    /** JobInstanceId */
    private long jobInstanceId;
    /** 開始日時 */
    private String startDate;
    /** 終了日時 */
    private String endDate;
    /** Job名 */
    private String jobName;

    /**
     * JobInstanceIdを返す
     * 
     * @return jobInstanceId
     */
    public long getJobInstanceId() {
        return jobInstanceId;
    }

    /**
     * JobInstanceIdを設定する
     * 
     * @param jobInstanceId
     *     JobInstanceId
     */
    public void setJobInstanceId(long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

    /**
     * 開始日時を返す
     * 
     * @return startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 開始日時を設定する
     * 
     * @param startDate
     *     開始日時
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 終了日時を返す
     * 
     * @return endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 終了日時を設定する
     * 
     * @param endDate
     *     終了日時
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Job名を返す
     * 
     * @return jobName
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Job名を設定する
     * 
     * @param jobName
     *     Job名
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

}
