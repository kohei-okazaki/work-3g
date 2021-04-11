package jp.co.ha.db.entity.composite;

import java.io.Serializable;

import jp.co.ha.common.log.annotation.Ignore;

/**
 * 健康情報とBMI範囲マスタの複合EntityのPrimaryKey
 *
 * @version 1.0.0
 */
public class CompositeHealthInfoKey implements Serializable {

    /** serialVersionUID */
    @Ignore
    private static final long serialVersionUID = 1L;

    /** 健康情報ID */
    private Long seqHealthInfoId;
    /** ユーザID */
    private Long seqUserId;

    /**
     * seqHealthInfoIdを返す
     *
     * @return seqHealthInfoId
     */
    public Long getSeqHealthInfoId() {
        return seqHealthInfoId;
    }

    /**
     * seqHealthInfoIdを設定する
     *
     * @param seqHealthInfoId
     *     健康情報ID
     */
    public void setSeqHealthInfoId(Long seqHealthInfoId) {
        this.seqHealthInfoId = seqHealthInfoId;
    }

    /**
     * seqUserIdを返す
     *
     * @return seqUserId
     */
    public Long getSeqUserId() {
        return seqUserId;
    }

    /**
     * seqUserIdを設定する
     *
     * @param seqUserId
     *     ユーザID
     */
    public void setSeqUserId(Long seqUserId) {
        this.seqUserId = seqUserId;
    }

}
