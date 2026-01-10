package jp.co.ha.db.entity.custom;

import java.time.LocalDateTime;

import jp.co.ha.common.db.annotation.Entity;
import jp.co.ha.common.log.annotation.Ignore;

/**
 * Batch起動履歴テーブル<br>
 * 以下のテーブルを結合して取得
 * <ul>
 * <li>BATCH_JOB_INSTANCE</li>
 * <li>BATCH_JOB_EXECUTION</li>
 * <li>BATCH_JOB_EXECUTION_PARAMS</li>
 * </ul>
 * 
 * @version 1.0.0
 */
@Entity
public class CustomJobData {

    /** serialVersionUID */
    @Ignore
    private static final long serialVersionUID = 1L;

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
