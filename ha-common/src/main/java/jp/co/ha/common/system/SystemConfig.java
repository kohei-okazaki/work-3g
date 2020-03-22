package jp.co.ha.common.system;

import org.springframework.stereotype.Component;

/**
 * system.propertiesのBean
 *
 * @since 1.0
 */
@Component
public class SystemConfig {

    /** ページング数 */
    private String paging;
    /** 健康情報APIの基底URL */
    private String baseApiUrl;

    /**
     * pagingを返す
     *
     * @return paging
     */
    public String getPaging() {
        return paging;
    }

    /**
     * pagingを設定する
     *
     * @param paging
     *     ページング数
     */
    public void setPaging(String paging) {
        this.paging = paging;
    }

    /**
     * baseApiUrlを返す
     *
     * @return baseApiUrl
     */
    public String getBaseApiUrl() {
        return baseApiUrl;
    }

    /**
     * baseApiUrlを設定する
     *
     * @param baseApiUrl
     *     健康情報APIの基底URL
     */
    public void setBaseApiUrl(String baseApiUrl) {
        this.baseApiUrl = baseApiUrl;
    }

}
