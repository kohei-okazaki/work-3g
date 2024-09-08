package jp.co.ha.db.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jp.co.ha.common.db.annotation.Entity;
import jp.co.ha.common.log.annotation.Ignore;

/**
 * お知らせ情報
 *
 * @version 1.0.0
 */
@Entity
public class NewsInfo extends NewsInfoKey implements Serializable {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column news_info.S3_KEY
     * 
     * @mbg.generated Sun Sep 08 19:54:40 GMT+09:00 2024
     */
    private String s3Key;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column news_info.UPDATE_DATE
     * 
     * @mbg.generated Sun Sep 08 19:54:40 GMT+09:00 2024
     */
    private LocalDateTime updateDate;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column news_info.REG_DATE
     * 
     * @mbg.generated Sun Sep 08 19:54:40 GMT+09:00 2024
     */
    private LocalDateTime regDate;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database table news_info
     * 
     * @mbg.generated Sun Sep 08 19:54:40 GMT+09:00 2024
     */
    @Ignore
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column news_info.S3_KEY
     * 
     * @return the value of news_info.S3_KEY
     * @mbg.generated Sun Sep 08 19:54:40 GMT+09:00 2024
     */
    public String getS3Key() {
        return s3Key;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column news_info.S3_KEY
     * 
     * @param s3Key
     *     the value for news_info.S3_KEY
     * @mbg.generated Sun Sep 08 19:54:40 GMT+09:00 2024
     */
    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column news_info.UPDATE_DATE
     * 
     * @return the value of news_info.UPDATE_DATE
     * @mbg.generated Sun Sep 08 19:54:40 GMT+09:00 2024
     */
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column news_info.UPDATE_DATE
     * 
     * @param updateDate
     *     the value for news_info.UPDATE_DATE
     * @mbg.generated Sun Sep 08 19:54:40 GMT+09:00 2024
     */
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column news_info.REG_DATE
     * 
     * @return the value of news_info.REG_DATE
     * @mbg.generated Sun Sep 08 19:54:40 GMT+09:00 2024
     */
    public LocalDateTime getRegDate() {
        return regDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column news_info.REG_DATE
     * 
     * @param regDate
     *     the value for news_info.REG_DATE
     * @mbg.generated Sun Sep 08 19:54:40 GMT+09:00 2024
     */
    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table news_info
     * 
     * @mbg.generated Sun Sep 08 19:54:40 GMT+09:00 2024
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", s3Key=").append(s3Key);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", regDate=").append(regDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table news_info
     * 
     * @mbg.generated Sun Sep 08 19:54:40 GMT+09:00 2024
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        NewsInfo other = (NewsInfo) that;
        return (this.getSeqNewsInfoId() == null ? other.getSeqNewsInfoId() == null
                : this.getSeqNewsInfoId().equals(other.getSeqNewsInfoId()))
                && (this.getS3Key() == null ? other.getS3Key() == null
                        : this.getS3Key().equals(other.getS3Key()))
                && (this.getUpdateDate() == null ? other.getUpdateDate() == null
                        : this.getUpdateDate().equals(other.getUpdateDate()))
                && (this.getRegDate() == null ? other.getRegDate() == null
                        : this.getRegDate().equals(other.getRegDate()));
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table news_info
     * 
     * @mbg.generated Sun Sep 08 19:54:40 GMT+09:00 2024
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((getSeqNewsInfoId() == null) ? 0 : getSeqNewsInfoId().hashCode());
        result = prime * result + ((getS3Key() == null) ? 0 : getS3Key().hashCode());
        result = prime * result
                + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getRegDate() == null) ? 0 : getRegDate().hashCode());
        return result;
    }
}