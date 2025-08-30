package jp.co.ha.db.entity;

import org.springframework.data.domain.Pageable;

/**
 * MyBatisのExampleクラスの基底Exampleクラス<br>
 * 検索上限数等すべてのTableに対し、共通で処理すべきものを定義する<br>
 * TODO ロック処理はまだどのMapper.xmlに反映させていない<br>
 * TODO ここで定義したオプションを使う場合、各Mapper.xmlにその記述を入れること<br>
 *
 * @version 1.0.0
 */
public abstract class BaseExample {

    /** 検索上限数 */
    protected Integer limit;
    /** ロック有無 */
    protected Boolean lock;
    /** Pageable */
    protected Pageable pageable;

    /**
     * 検索上限数を返す
     *
     * @return limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * 検索上限数を設定する
     *
     * @param limit
     *     検索上限数
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * ロック有無を返す
     *
     * @return lock
     */
    public Boolean getLock() {
        return lock;
    }

    /**
     * ロック有無を設定する
     *
     * @param lock
     *     ロック有無
     */
    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    /**
     * ページャブルを返す
     *
     * @return pageable
     */
    public Pageable getPageable() {
        return pageable;
    }

    /**
     * ページャブルを設定する
     *
     * @param pageable
     *     ページャブル
     */
    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

}
