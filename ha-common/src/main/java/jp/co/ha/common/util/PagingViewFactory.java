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
     */
    public static PagingView getPageView(Pageable pageable, String path, long count) {

        LOG.debug(
                // 要求しているページ番号
                "pageable.getPageNumber()=" + pageable.getPageNumber()
                // 表示件数
                        + ", pageable.getPageSize()=" + pageable.getPageSize()
                        // ↑2つの積
                        + ", pageable.getOffset()=" + pageable.getOffset());

        PagingView pv = new PagingView();

        pv.setCanGoFirst(pageable.getPageNumber() != 0);
        pv.setCanGoPrevious(pageable.getPageNumber() != 0);

        // (1ページあたりに表示できる件数 * n) と総レコード数が等しいかどうか
        boolean isFullePage = count % pageable.getPageSize() == 0;
        if (isFullePage) {
            pv.setCanGoLast(false);
            pv.setCanGoNext(false);
        } else {
            pv.setCanGoLast(
                    pageable.getPageNumber() < (count / pageable.getPageSize()));
            pv.setCanGoNext(
                    pageable.getPageNumber() < (count / pageable.getPageSize()));
        }

        pv.setCurrentPageNum(pageable.getPageNumber());
        pv.setFirstHref(path + "=" + 0);

        int fromRecordNum;
        if (count == 0) {
            fromRecordNum = 0;
        } else if (count < pageable.getOffset()) {
            fromRecordNum = (int) (count / pageable.getPageSize()
                    * pageable.getPageSize());
        } else {
            fromRecordNum = pageable.getPageSize() * pageable.getPageNumber() + 1;
        }

        pv.setFromRecordNum(fromRecordNum);
        pv.setLastHref(
                path + "=" + (count / pageable.getPageSize()));
        pv.setNextHref(
                path + "=" + (pageable.getPageNumber() + 1));
        pv.setPreviousHref(
                path + "=" + (pageable.getPageNumber() - 1));
        pv.setRecordPerPage(5);

        long toRecordNum = pageable.getPageSize() * pageable.getPageNumber()
                + pageable.getPageSize();
        toRecordNum = count < toRecordNum ? count : toRecordNum;
        pv.setToRecordNum(toRecordNum);
        pv.setTotalRecordNum(count);

        return pv;
    }
}
