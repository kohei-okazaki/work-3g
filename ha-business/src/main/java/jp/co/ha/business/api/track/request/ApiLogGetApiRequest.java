package jp.co.ha.business.api.track.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.web.form.BaseApiRequest;

/**
 * API通信ログ取得APIのリクエスト情報クラス
 *
 * @version 1.0.0
 */
public class ApiLogGetApiRequest extends BaseTrackApiRequest implements BaseApiRequest {

    /** 処理対象日 */
    @JsonProperty("date")
    private String targetDate;
    /** 上限値 */
    @JsonProperty("limit")
    private int limit;
    /** 次回呼び出し用トークン */
    @JsonProperty("next_token")
    private String nextToken;

    /**
     * 処理対象日を返す
     * 
     * @return targetDate
     */
    public String getTargetDate() {
        return targetDate;
    }

    /**
     * 処理対象日を設定する
     * 
     * @param targetDate
     *     処理対象日
     */
    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    /**
     * 上限値を返す
     * 
     * @return limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * 上限値を設定する
     * 
     * @param limit
     *     上限値
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * 次回呼び出し用トークンを返す
     * 
     * @return nextToken
     */
    public String getNextToken() {
        return nextToken;
    }

    /**
     * 次回呼び出し用トークンを設定する
     * 
     * @param nextToken
     *     次回呼び出し用トークン
     */
    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

}
