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
     * 合計レコード数を返す
     *
     * @return totalRecordNum
     */
    public long getTotalRecordNum() {
        return totalRecordNum;
    }

    /**
     * 合計レコード数を設定する
     *
     * @param totalRecordNum
     *     合計レコード数
     */
    public void setTotalRecordNum(long totalRecordNum) {
        this.totalRecordNum = totalRecordNum;
    }

    /**
     * レコード数(from)を返す
     *
     * @return fromRecordNum
     */
    public int getFromRecordNum() {
        return fromRecordNum;
    }

    /**
     * レコード数(from)を設定する
     *
     * @param fromRecordNum
     *     レコード数(from)
     */
    public void setFromRecordNum(int fromRecordNum) {
        this.fromRecordNum = fromRecordNum;
    }

    /**
     * レコード数(to)を返す
     *
     * @return toRecordNum
     */
    public long getToRecordNum() {
        return toRecordNum;
    }

    /**
     * レコード数(to)を設定する
     *
     * @param toRecordNum
     *     レコード数(to)
     */
    public void setToRecordNum(long toRecordNum) {
        this.toRecordNum = toRecordNum;
    }

    /**
     * 総ページ数を返す
     *
     * @return totalPage
     */
    public long getTotalPage() {
        return totalPage;
    }

    /**
     * 総ページ数を設定する
     *
     * @param totalPage
     *     総ページ数
     */
    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * 現在のページ数を返す
     *
     * @return currentPageNum
     */
    public int getCurrentPageNum() {
        return currentPageNum;
    }

    /**
     * 現在のページ数を設定する
     *
     * @param currentPageNum
     *     現在のページ数
     */
    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    /**
     * 1ページあたりのレコード数を返す
     *
     * @return recordPerPage
     */
    public int getRecordPerPage() {
        return recordPerPage;
    }

    /**
     * 1ページあたりのレコード数を設定する
     *
     * @param recordPerPage
     *     1ページあたりのレコード数
     */
    public void setRecordPerPage(int recordPerPage) {
        this.recordPerPage = recordPerPage;
    }

    /**
     * 次リンク押下できるかどうかを返す
     *
     * @return canGoNext
     */
    public boolean isCanGoNext() {
        return canGoNext;
    }

    /**
     * 次リンク押下できるかどうかを設定する
     *
     * @param canGoNext
     *     次リンク押下できるかどうか
     */
    public void setCanGoNext(boolean canGoNext) {
        this.canGoNext = canGoNext;
    }

    /**
     * 次リンクのhref属性を返す
     *
     * @return nextHref
     */
    public String getNextHref() {
        return nextHref;
    }

    /**
     * 次リンクのhref属性を設定する
     *
     * @param nextHref
     *     次リンクのhref属性
     */
    public void setNextHref(String nextHref) {
        this.nextHref = nextHref;
    }

    /**
     * 前リンク押下できるかどうかを返す
     *
     * @return canGoPrevious
     */
    public boolean isCanGoPrevious() {
        return canGoPrevious;
    }

    /**
     * 前リンク押下できるかどうかを設定する
     *
     * @param canGoPrevious
     *     前リンク押下できるかどうか
     */
    public void setCanGoPrevious(boolean canGoPrevious) {
        this.canGoPrevious = canGoPrevious;
    }

    /**
     * 前リンクのhref属性を返す
     *
     * @return previousHref
     */
    public String getPreviousHref() {
        return previousHref;
    }

    /**
     * 前リンクのhref属性を設定する
     *
     * @param previousHref
     *     前リンクのhref属性
     */
    public void setPreviousHref(String previousHref) {
        this.previousHref = previousHref;
    }

    /**
     * 最後ページまでのリンクを押下できるかどうかを返す
     *
     * @return canGoLast
     */
    public boolean isCanGoLast() {
        return canGoLast;
    }

    /**
     * 最後ページまでのリンクを押下できるかどうかを設定する
     *
     * @param canGoLast
     *     最後ページまでのリンクを押下できるかどうか
     */
    public void setCanGoLast(boolean canGoLast) {
        this.canGoLast = canGoLast;
    }

    /**
     * 最後ページリンクのhref属性を返す
     *
     * @return lastHref
     */
    public String getLastHref() {
        return lastHref;
    }

    /**
     * 最後ページリンクのhref属性を設定する
     *
     * @param lastHref
     *     最後ページリンクのhref属性
     */
    public void setLastHref(String lastHref) {
        this.lastHref = lastHref;
    }

    /**
     * 先頭ページまでのリンクを押下できるかどうかを返す
     *
     * @return canGoFirst
     */
    public boolean isCanGoFirst() {
        return canGoFirst;
    }

    /**
     * 先頭ページまでのリンクを押下できるかどうかを設定する
     *
     * @param canGoFirst
     *     先頭ページまでのリンクを押下できるかどうか
     */
    public void setCanGoFirst(boolean canGoFirst) {
        this.canGoFirst = canGoFirst;
    }

    /**
     * 先頭ページリンクのhref属性を返す
     *
     * @return firstHref
     */
    public String getFirstHref() {
        return firstHref;
    }

    /**
     * 先頭ページリンクのhref属性を設定する
     *
     * @param firstHref
     *     先頭ページリンクのhref属性
     */
    public void setFirstHref(String firstHref) {
        this.firstHref = firstHref;
    }

}
