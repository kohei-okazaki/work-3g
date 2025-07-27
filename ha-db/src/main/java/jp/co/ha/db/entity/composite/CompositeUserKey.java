package jp.co.ha.db.entity.composite;

import java.io.Serializable;

import jp.co.ha.common.log.annotation.Ignore;

/**
 * ユーザ情報と健康情報ファイル設定の複合EntityのPrimaryKey
 *
 * @version 1.0.0
 */
public class CompositeUserKey implements Serializable {

    /** serialVersionUID */
    @Ignore
    private static final long serialVersionUID = 1L;
    /** ユーザID */
    private Long seqUserId;

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
