package jp.co.ha.db.entity.custom;

import java.io.Serializable;
import java.time.LocalDate;

import jp.co.ha.common.db.annotation.Entity;
import jp.co.ha.common.log.annotation.Ignore;

/**
 * 月ごとの健康情報登録件数情報
 *
 * @version 1.0.0
 */
@Entity
public class CompositeMonthlyRegData implements Serializable {

    /** serialVersionUID */
    @Ignore
    private static final long serialVersionUID = -5290848974397984465L;

    /** 登録件数 */
    private Integer count;
    /** 登録日 */
    private LocalDate date;

    /**
     * 登録件数を返す
     *
     * @return count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 登録件数を設定する
     *
     * @param count
     *     登録件数
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 登録日を返す
     *
     * @return date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * 登録日を設定する
     *
     * @param date
     *     登録日
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

}
