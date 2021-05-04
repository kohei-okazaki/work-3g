package jp.co.ha.root.contents.healthinfo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.web.form.BaseApiRequest;
import jp.co.ha.root.base.BaseRootApiRequest;

/**
 * 健康情報一覧取得APIリクエストクラス
 *
 * @version 1.0.0
 */
public class HealthInfoListApiRequest extends BaseRootApiRequest
        implements BaseApiRequest {

    /** ページ */
    @JsonProperty("page")
    private Integer page = 0;

    /**
     * pageを返す
     *
     * @return page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * pageを設定する
     *
     * @param page
     *     ページ
     */
    public void setPage(Integer page) {
        this.page = page;
    }

}
