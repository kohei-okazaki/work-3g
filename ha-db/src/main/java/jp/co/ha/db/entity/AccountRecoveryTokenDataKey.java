package jp.co.ha.db.entity;

import java.io.Serializable;

import jp.co.ha.common.log.annotation.Ignore;

/**
 * アカウント回復トークン情報のPrimaryKey
 *
 * @version 1.0.0
 */
public class AccountRecoveryTokenDataKey implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column
     * account_recovery_token_data.SEQ_ACCOUNT_RECOVERY_TOKEN_ID
     *
     * @mbg.generated Sun Nov 29 14:26:32 GMT+09:00 2020
     */
    private Integer seqAccountRecoveryTokenId;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database table account_recovery_token_data
     *
     * @mbg.generated Sun Nov 29 14:26:32 GMT+09:00 2020
     */
    @Ignore
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column
     * account_recovery_token_data.SEQ_ACCOUNT_RECOVERY_TOKEN_ID
     *
     * @return the value of
     * account_recovery_token_data.SEQ_ACCOUNT_RECOVERY_TOKEN_ID
     *
     * @mbg.generated Sun Nov 29 14:26:32 GMT+09:00 2020
     */
    public Integer getSeqAccountRecoveryTokenId() {
        return seqAccountRecoveryTokenId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column
     * account_recovery_token_data.SEQ_ACCOUNT_RECOVERY_TOKEN_ID
     *
     * @param seqAccountRecoveryTokenId
     *     the value for
     *     account_recovery_token_data.SEQ_ACCOUNT_RECOVERY_TOKEN_ID
     *
     * @mbg.generated Sun Nov 29 14:26:32 GMT+09:00 2020
     */
    public void setSeqAccountRecoveryTokenId(Integer seqAccountRecoveryTokenId) {
        this.seqAccountRecoveryTokenId = seqAccountRecoveryTokenId;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table account_recovery_token_data
     *
     * @mbg.generated Sun Nov 29 14:26:32 GMT+09:00 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", seqAccountRecoveryTokenId=").append(seqAccountRecoveryTokenId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table account_recovery_token_data
     *
     * @mbg.generated Sun Nov 29 14:26:32 GMT+09:00 2020
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
        AccountRecoveryTokenDataKey other = (AccountRecoveryTokenDataKey) that;
        return (this.getSeqAccountRecoveryTokenId() == null
                ? other.getSeqAccountRecoveryTokenId() == null
                : this.getSeqAccountRecoveryTokenId()
                        .equals(other.getSeqAccountRecoveryTokenId()));
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table account_recovery_token_data
     *
     * @mbg.generated Sun Nov 29 14:26:32 GMT+09:00 2020
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSeqAccountRecoveryTokenId() == null) ? 0
                : getSeqAccountRecoveryTokenId().hashCode());
        return result;
    }
}