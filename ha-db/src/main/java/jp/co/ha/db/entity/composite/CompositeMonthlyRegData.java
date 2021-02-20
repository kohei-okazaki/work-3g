package jp.co.ha.db.entity.composite;

import java.io.Serializable;
import java.time.LocalDate;

import jp.co.ha.common.log.annotation.Ignore;

/**
 * 月ごとの健康情報登録件数情報
 *
 * @version 1.0.0
 */
public class CompositeMonthlyRegData implements Serializable {

    /** serialVersionUID */
    @Ignore
    private static final long serialVersionUID = -5290848974397984465L;

    /** 登録件数 */
    private Integer count;
    /** 登録日 */
    private LocalDate date;

    /**
     * countを返す
     *
     * @return count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * countを設定する
     *
     * @param count
     *     登録件数
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * dateを返す
     *
     * @return date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * dateを設定する
     *
     * @param date
     *     登録日
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

}
