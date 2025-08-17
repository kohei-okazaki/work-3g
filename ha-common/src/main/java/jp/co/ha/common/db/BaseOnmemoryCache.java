package jp.co.ha.common.db;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.DateTimeUtil;

/**
 * 基底オンメモリキャッシュクラス
 *
 * @param <T>
 *     キャッシュ対象するクラス
 * @version 1.0.0
 */
public abstract class BaseOnmemoryCache<T> {

    /** LOG */
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    /** キャッシュリスト */
    private List<T> list;
    /** キャッシュ登録日時 */
    private LocalDateTime cacheDate;

    /**
     * 以下の内容でデータを返す<br>
     * <ul>
     * <li>キャッシュにデータが存在しない場合、新規取得データ</li>
     * <li>キャッシュにデータが存在し有効期限外の場合、新規取得データ</li>
     * <li>キャッシュにデータが存在し有効期限内の場合、キャッシュデータ</li>
     * </ul>
     *
     * @param select
     *     データ取得処理
     * @return キャッシュデータ
     */
    public List<T> get(Supplier<List<T>> select) {

        if (isSelected()) {
            // 新規データ取得対象の場合
            this.cacheDate = DateTimeUtil.getSysDate();
            this.list = select.get();
        }
        return this.list;
    }

    /**
     * キャッシュの削除処理を行う
     */
    public void delete() {
        this.cacheDate = null;
        this.list = null;
    }

    /**
     * 新規データ取得対象判定処理
     *
     * @return 新規データ取得する場合True、それ以外の場合False
     */
    protected boolean isSelected() {

        if (this.cacheDate == null) {
            // キャッシュされていない場合
            LOG.debug(getTableName() + " is no cache.");
            return true;
        } else if (this.cacheDate.plusSeconds(getExpireSeconds() / 1000)
                .compareTo(LocalDateTime.now()) < 0) {
            // キャッシュの有効期限を超過している場合
            LOG.debug(getTableName() + " is expired.");
            return true;
        }
        LOG.debug(getTableName() + "'s cache is enable.");
        return false;
    }

    /**
     * キャッシュ有効期限を返す
     *
     * @return キャッシュ有効期限
     */
    protected abstract long getExpireSeconds();

    /**
     * テーブル名を返す
     *
     * @return テーブル名
     */
    protected abstract String getTableName();

}
