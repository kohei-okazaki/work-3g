package jp.co.ha.db.entity;

/**
 * MyBatisのExampleクラスの基底Exampleクラス<br>
 * 検索上限数等すべてのTableに対し、共通で処理すべきものを定義する<br>
 * TODO ロック処理はまだどのMapper.xmlに反映させていない<br>
 * TODO ここで定義したオプションを使う場合、各Mapper.xmlにその記述を入れること<br>
 *
 * @since 1.0
 */
public abstract class BaseExample {

    /** 検索上限数 */
    protected Integer limit;
    /** ロック有無 */
    protected Boolean lock;

    /**
     * limitを返す
     *
     * @return limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * limitを設定する
     *
     * @param limit
     *     検索上限数
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * lockを返す
     *
     * @return lock
     */
    public Boolean getLock() {
        return lock;
    }

    /**
     * lockを設定する
     *
     * @param lock
     *     ロック有無
     */
    public void setLock(Boolean lock) {
        this.lock = lock;
    }

}
