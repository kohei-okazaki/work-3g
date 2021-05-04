package jp.co.ha.common.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.web.form.JsonEntity;

/**
 * ページングViewクラス<br>
 * 画面のページングに必要な情報を保持するクラス
 *
 * @version 1.0.0
 */
public class PagingView extends JsonEntity {

    /** 合計レコード数 */
    @JsonProperty("total_record_num")
    private long totalRecordNum;
    /** レコード数(from) */
    @JsonProperty("from_record_num")
    private int fromRecordNum;
    /** レコード数(to) */
    @JsonProperty("to_record_num")
    private long toRecordNum;
    /** 総ページ数 */
    @JsonProperty("total_page")
    private long totalPage;

    /** 現在のページ数 */
    @JsonProperty("current_page_num")
    private int currentPageNum;
    /** 1ページあたりのレコード数 */
    @JsonProperty("record_per_page")
    private int recordPerPage;

    /** 次リンク押下できるかどうか */
    @JsonProperty("can_go_next")
    private boolean canGoNext;
    /** 次リンクのhref属性 */
    @JsonProperty("next_href")
    private String nextHref;
    /** 前リンク押下できるかどうか */
    @JsonProperty("can_go_previous")
    private boolean canGoPrevious;
    /** 前リンクのhref属性 */
    @JsonProperty("previous_href")
    private String previousHref;

    /** 最後ページまでのリンクを押下できるかどうか */
    @JsonProperty("can_go_last")
    private boolean canGoLast;
    /** 最後ページリンクのhref属性 */
    @JsonProperty("last_href")
    private String lastHref;
    /** 先頭ページまでのリンクを押下できるかどうか */
    @JsonProperty("can_go_first")
    private boolean canGoFirst;
    /** 先頭ページリンクのhref属性 */
    @JsonProperty("first_href")
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
     * totalPageを返す
     *
     * @return totalPage
     */
    public long getTotalPage() {
        return totalPage;
    }

    /**
     * totalPageを設定する
     *
     * @param totalPage
     *     総ページ数
     */
    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
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
