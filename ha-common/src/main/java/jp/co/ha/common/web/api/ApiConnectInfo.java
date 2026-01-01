package jp.co.ha.common.web.api;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.http.HttpStatus;

/**
 * API接続情報のBeanクラス<br>
 * API設定情報は以下とする
 * <ul>
 * <li>文字コード(デフォルトでUTF-8)</li>
 * <li>リクエストヘッダーのMap</li>
 * <li>クエリパラメータのMap</li>
 * </ul>
 *
 * @version 1.0.0
 */
public class ApiConnectInfo {

    /** 健康管理API リクエストヘッダー:API-KEY */
    public static final String X_API_KEY = "Api-Key";
    /** 健康情報計算API リクエストヘッダー:X-NODE-TOKEN */
    public static final String X_NODE_TOKEN = "X-NODE-TOKEN";

    /** URLSupplier */
    private Supplier<String> urlSupplier;
    /** 文字コード */
    private Charset charset = Charset.forName("UTF-8");
    /** リクエストヘッダMap */
    private Map<String, String> headerMap = new HashMap<>();
    /** HTTPステータス */
    private HttpStatus httpStatus;
    /** API通信リクエスト日時 */
    private LocalDateTime requestDate;
    /** API通信レスポンス日時 */
    private LocalDateTime responseDate;

    /**
     * URLSupplierを返す
     *
     * @return urlSupplier
     */
    public Supplier<String> getUrlSupplier() {
        return urlSupplier;
    }

    /**
     * URLSupplierを設定する
     *
     * @param urlSupplier
     *     URLSupplier
     */
    public void setUrlSupplier(Supplier<String> urlSupplier) {
        this.urlSupplier = urlSupplier;
    }

    /**
     * URLSupplierを設定する
     *
     * @param urlSupplier
     *     URLSupplier
     * @return ApiConnectInfo
     */
    public ApiConnectInfo withUrlSupplier(Supplier<String> urlSupplier) {
        this.urlSupplier = urlSupplier;
        return this;
    }

    /**
     * 文字コードを返す
     *
     * @return charset
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * 文字コードを設定する
     *
     * @param charset
     *     文字コード
     */
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    /**
     * 文字コードを設定する
     *
     * @param charset
     *     文字コード
     */
    public void setCharset(String charset) {
        this.charset = Charset.forName(charset);
    }

    /**
     * 文字コードを設定する
     *
     * @param charset
     *     文字コード
     * @return ApiConnectInfo
     */
    public ApiConnectInfo withCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    /**
     * リクエストヘッダMapを返す
     *
     * @return headerMap
     */
    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    /**
     * ヘッダーを追加する
     *
     * @param key
     *     パラメータキー
     * @param value
     *     パラメータ値
     */
    public void addHeader(String key, String value) {
        this.headerMap.put(key, value);
    }

    /**
     * ヘッダーを追加する
     *
     * @param key
     *     パラメータキー
     * @param value
     *     パラメータ値
     * @return ApiConnectInfo
     */
    public ApiConnectInfo withHeader(String key, String value) {
        this.headerMap.put(key, value);
        return this;
    }

    /**
     * HTTPステータスを返す
     *
     * @return httpStatus
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * HTTPステータスを設定する
     *
     * @param httpStatus
     *     HTTPステータス
     */
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    /**
     * API通信リクエスト日時を返す
     * 
     * @return requestDate
     */
    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    /**
     * API通信リクエスト日時を設定する
     * 
     * @param requestDate
     *     API通信リクエスト日時
     */
    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * API通信レスポンス日時を返す
     * 
     * @return responseDate
     */
    public LocalDateTime getResponseDate() {
        return responseDate;
    }

    /**
     * API通信レスポンス日時を設定する
     * 
     * @param responseDate
     *     API通信レスポンス日時
     */
    public void setResponseDate(LocalDateTime responseDate) {
        this.responseDate = responseDate;
    }

}
