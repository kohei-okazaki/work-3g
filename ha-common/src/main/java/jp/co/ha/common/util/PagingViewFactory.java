package jp.co.ha.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * {@linkplain PagingView}のFactoryクラス
 *
 * @version 1.0.0
 */
public class PagingViewFactory {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(PagingViewFactory.class);

    /**
     * プライベートコンストラクタ
     */
    private PagingViewFactory() {
    }

    /**
     * {@linkplain Pageable}を返す
     *
     * @param strPage
     *     ページ数の文字列表現
     * @param strSize
     *     1ページあたりに表示させる件数の文字列表現
     * @return Pageable
     * @see #getPageable(int, int)
     */
    public static Pageable getPageable(String strPage, String strSize) {
        int page = (strPage == null) ? 0 : Integer.parseInt(strPage);
        int size = (strSize == null) ? 10 : Integer.parseInt(strSize);
        return getPageable(page, size);
    }

    /**
     * {@linkplain Pageable}を返す
     *
     * @param page
     *     ページ数
     * @param size
     *     1ページあたりに表示させる件数
     * @return Pageable
     */
    public static Pageable getPageable(int page, int size) {
        return PageRequest.of(page, size);
    }

    /**
     * {@linkplain PagingView}を返す
     *
     * @param pageable
     *     {@linkplain Pageable}
     * @param path
     *     URLパス
     * @param count
     *     レコード件数
     * @return PagingView
     * @throws IllegalArgumentException
     *     ページングなし、またはレコード件数が負の場合
     */
    public static PagingView getPageView(Pageable pageable, String path, long count) {

        if (pageable.isUnpaged()) {
            throw new IllegalArgumentException("pageable must be paged");
        }
        if (count < 0) {
            throw new IllegalArgumentException("count must not be negative");
        }

        int currentPage = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        long offset = pageable.getOffset();

        LOG.debug(
                // 要求しているページ番号, 表示件数, 2つの積
                "page_number=%s, page_size=%s, offset=%s".formatted(
                        currentPage, pageSize, offset));

        // 総ページ数と、0始まりの最終ページ番号を区別する。
        long totalPages = count / pageSize + (count % pageSize == 0 ? 0 : 1);
        long lastPage = Math.max(totalPages - 1, 0);
        boolean hasPrevious = count > 0 && currentPage > 0;
        boolean hasNext = currentPage < lastPage;

        PagingView pv = new PagingView();
        pv.setCanGoFirst(hasPrevious);
        pv.setCanGoPrevious(hasPrevious);
        pv.setCanGoNext(hasNext);
        pv.setCanGoLast(hasNext);
        pv.setTotalPage(totalPages);
        pv.setCurrentPageNum(currentPage);
        pv.setRecordPerPage(pageSize);
        pv.setFirstHref(path + "=" + 0);
        pv.setLastHref(path + "=" + lastPage);

        // 範囲外から戻る場合も、実在するページをリンク先とする。
        long previousPage = hasPrevious ? Math.min((long) currentPage - 1, lastPage) : 0;
        long nextPage = hasNext ? (long) currentPage + 1 : lastPage;
        pv.setPreviousHref(path + "=" + previousPage);
        pv.setNextHref(path + "=" + nextPage);

        // 取得済みデータとのずれを避け、要求ページ自体は補正しない。
        // 0件・範囲外ページでは表示するレコードがないため0～0件とする。
        boolean hasPageRecords = offset < count;
        pv.setFromRecordNum(hasPageRecords ? offset + 1 : 0);
        pv.setToRecordNum(hasPageRecords
                ? offset + Math.min((long) pageSize, count - offset)
                : 0);

        pv.setTotalRecordNum(count);

        return pv;
    }
}
