package jp.co.ha.root.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 設定ファイル情報<br>
 * <ul>
 * <li>application.yml</li>
 * <li>application-${environment}.yml</li>
 * </ul>
 * のBean定義
 *
 * @version 1.0.0
 */
@Component
public class ApplicationProperties {

    /** Front画面URL */
    @Value("${front.url}")
    private String frontUrl;
    /** 1ページあたりのページ数 */
    @Value("${front.page}")
    private int page;

    /**
     * frontUrlを返す
     *
     * @return frontUrl
     */
    public String getFrontUrl() {
        return frontUrl;
    }

    /**
     * frontUrlを設定する
     *
     * @param frontUrl
     *     Front画面URL
     */
    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    /**
     * pageを返す
     *
     * @return page
     */
    public int getPage() {
        return page;
    }

    /**
     * pageを設定する
     *
     * @param page
     *     1ページあたりのページ数
     */
    public void setPage(int page) {
        this.page = page;
    }

}
