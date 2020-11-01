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
    private Integer seqUserId;

    /**
     * seqUserIdを返す
     *
     * @return seqUserId
     */
    public Integer getSeqUserId() {
        return seqUserId;
    }

    /**
     * seqUserIdを設定する
     *
     * @param seqUserId
     *     ユーザID
     */
    public void setSeqUserId(Integer seqUserId) {
        this.seqUserId = seqUserId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", seqUserId=").append(seqUserId);
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
        return (this.getSeqUserId() == null ? other.getSeqUserId() == null
                : this.getSeqUserId().equals(other.getSeqUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((getSeqUserId() == null) ? 0 : getSeqUserId().hashCode());
        return result;
    }

}
