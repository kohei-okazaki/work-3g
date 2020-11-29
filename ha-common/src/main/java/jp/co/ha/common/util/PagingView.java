package jp.co.ha.common.util;

/**
 * ページングViewクラス<br>
 * 画面のページングに必要な情報を保持するクラス
 *
 * @version 1.0.0
 */
public class PagingView {

    /** 合計レコード数 */
    private long totalRecordNum;
    /** レコード数(from) */
    private int fromRecordNum;
    /** レコード数(to) */
    private long toRecordNum;

    /** 現在のページ数 */
    private int currentPageNum;
    /** 1ページあたりのレコード数 */
    private int recordPerPage;

    /** 次リンク押下できるかどうか */
    private boolean canGoNext;
    /** 次リンクのhref属性 */
    private String nextHref;
    /** 前リンク押下できるかどうか */
    private boolean canGoPrevious;
    /** 前リンクのhref属性 */
    private String previousHref;

    /** 最後ページまでのリンクを押下できるかどうか */
    private boolean canGoLast;
    /** 最後ページリンクのhref属性 */
    private String lastHref;
    /** 先頭ページまでのリンクを押下できるかどうか */
    private boolean canGoFirst;
    /** 先頭ページリンクのhref属性 */
    private String firstHref;

    /**
     * totalRecordNumを返す
     *
     * @return totalRecordNum
     */
    public long getTotalRecordNum() {
        return totalRecordNum;
    }

    /**
     * totalRecordNumを設定する
     *
     * @param totalRecordNum
     */
    public void setTotalRecordNum(long totalRecordNum) {
        this.totalRecordNum = totalRecordNum;
    }

    /**
     * fromRecordNumを返す
     *
     * @return fromRecordNum
     */
    public int getFromRecordNum() {
        return fromRecordNum;
    }

    /**
     * fromRecordNumを設定する
     *
     * @param fromRecordNum
     */
    public void setFromRecordNum(int fromRecordNum) {
        this.fromRecordNum = fromRecordNum;
    }

    /**
     * toRecordNumを返す
     *
     * @return toRecordNum
     */
    public long getToRecordNum() {
        return toRecordNum;
    }

    /**
     * toRecordNumを設定する
     *
     * @param toRecordNum
     */
    public void setToRecordNum(long toRecordNum) {
        this.toRecordNum = toRecordNum;
    }

    /**
     * currentPageNumを返す
     *
     * @return currentPageNum
     */
    public int getCurrentPageNum() {
        return currentPageNum;
    }

    /**
     * currentPageNumを設定する
     *
     * @param currentPageNum
     */
    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    /**
     * recordPerPageを返す
     *
     * @return recordPerPage
     */
    public int getRecordPerPage() {
        return recordPerPage;
    }

    /**
     * recordPerPageを設定する
     *
     * @param recordPerPage
     */
    public void setRecordPerPage(int recordPerPage) {
        this.recordPerPage = recordPerPage;
    }

    /**
     * canGoNextを返す
     *
     * @return canGoNext
     */
    public boolean isCanGoNext() {
        return canGoNext;
    }

    /**
     * canGoNextを設定する
     *
     * @param canGoNext
     */
    public void setCanGoNext(boolean canGoNext) {
        this.canGoNext = canGoNext;
    }

    /**
     * nextHrefを返す
     *
     * @return nextHref
     */
    public String getNextHref() {
        return nextHref;
    }

    /**
     * nextHrefを設定する
     *
     * @param nextHref
     */
    public void setNextHref(String nextHref) {
        this.nextHref = nextHref;
    }

    /**
     * canGoPreviousを返す
     *
     * @return canGoPrevious
     */
    public boolean isCanGoPrevious() {
        return canGoPrevious;
    }

    /**
     * canGoPreviousを設定する
     *
     * @param canGoPrevious
     */
    public void setCanGoPrevious(boolean canGoPrevious) {
        this.canGoPrevious = canGoPrevious;
    }

    /**
     * previousHrefを返す
     *
     * @return previousHref
     */
    public String getPreviousHref() {
        return previousHref;
    }

    /**
     * previousHrefを設定する
     *
     * @param previousHref
     */
    public void setPreviousHref(String previousHref) {
        this.previousHref = previousHref;
    }

    /**
     * canGoLastを返す
     *
     * @return canGoLast
     */
    public boolean isCanGoLast() {
        return canGoLast;
    }

    /**
     * canGoLastを設定する
     *
     * @param canGoLast
     */
    public void setCanGoLast(boolean canGoLast) {
        this.canGoLast = canGoLast;
    }

    /**
     * lastHrefを返す
     *
     * @return lastHref
     */
    public String getLastHref() {
        return lastHref;
    }

    /**
     * lastHrefを設定する
     *
     * @param lastHref
     */
    public void setLastHref(String lastHref) {
        this.lastHref = lastHref;
    }

    /**
     * canGoFirstを返す
     *
     * @return canGoFirst
     */
    public boolean isCanGoFirst() {
        return canGoFirst;
    }

    /**
     * canGoFirstを設定する
     *
     * @param canGoFirst
     */
    public void setCanGoFirst(boolean canGoFirst) {
        this.canGoFirst = canGoFirst;
    }

    /**
     * firstHrefを返す
     *
     * @return firstHref
     */
    public String getFirstHref() {
        return firstHref;
    }

    /**
     * firstHrefを設定する
     *
     * @param firstHref
     */
    public void setFirstHref(String firstHref) {
        this.firstHref = firstHref;
    }

}
