package jp.co.ha.db.entity.composite;

import java.io.Serializable;

import jp.co.ha.common.log.annotation.Ignore;

/**
 * アカウント情報とメール情報と健康情報ファイル設定の複合EntityのPrimaryKey
 *
 * @version 1.0.0
 */
public class CompositeAccountKey implements Serializable {

    /** serialVersionUID */
    @Ignore
    private static final long serialVersionUID = 1L;
    /** ユーザID */
    private String userId;

    /**
     * userIdを返す
     *
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * userIdを設定する
     *
     * @param userId
     *     ユーザID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

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
        CompositeAccountKey other = (CompositeAccountKey) that;
        return (this.getUserId() == null ? other.getUserId() == null
                : this.getUserId().equals(other.getUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        return result;
    }

}
