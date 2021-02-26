package jp.co.ha.root.contents.top.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.root.base.BaseRootApiRequest;
import jp.co.ha.web.form.BaseApiRequest;

/**
 * Top情報取得APIリクエストクラス
 *
 * @version 1.0.0
 */
public class TopApiRequest extends BaseRootApiRequest implements BaseApiRequest {

    /** 対象年月 */
    @JsonProperty("date")
    private String date;

    /**
     * dateを返す
     *
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * dateを設定する
     *
     * @param date
     *     対象年月
     */
    public void setDate(String date) {
        this.date = date;
    }

}
