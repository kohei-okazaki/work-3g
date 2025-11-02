package jp.co.ha.root.base;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.PagingView;
import jp.co.ha.common.web.form.JsonEntity;
import jp.co.ha.root.type.RootApiResult;
import jp.co.ha.root.type.RootApiResult.RootApiResultSerializer;

/**
 * RootAPI基底レスポンスクラス
 *
 * @version 1.0.0
 */
public abstract class BaseRootApiResponse extends JsonEntity {

    /** 処理結果 */
    @JsonProperty("result")
    @JsonSerialize(using = RootApiResultSerializer.class)
    private RootApiResult rootApiResult;
    /** エラー情報 */
    @JsonProperty("errors")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorData> errorList;
    /** ページング */
    @JsonProperty("paging")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PagingView paging;

    /**
     * エラー情報
     *
     * @version 1.0.0
     */
    public static class ErrorData extends JsonEntity {

        /** メッセージ */
        @JsonProperty("message")
        private String message;

        /**
         * メッセージを返す
         *
         * @return message
         */
        public String getMessage() {
            return message;
        }

        /**
         * メッセージを設定する
         *
         * @param message
         *     メッセージ
         */
        public void setMessage(String message) {
            this.message = message;
        }

    }

    /**
     * 処理結果を返す
     *
     * @return rootApiResult
     */
    public RootApiResult getRootApiResult() {
        return rootApiResult;
    }

    /**
     * 処理結果を設定する
     *
     * @param rootApiResult
     *     処理結果
     */
    public void setRootApiResult(RootApiResult rootApiResult) {
        this.rootApiResult = rootApiResult;
    }

    /**
     * エラー情報リストを返す
     *
     * @return errorList
     */
    public List<ErrorData> getErrorList() {
        return errorList;
    }

    /**
     * エラー情報リストを設定する
     *
     * @param errorList
     *     エラー情報リスト
     */
    public void setErrorList(List<ErrorData> errorList) {
        this.errorList = errorList;
    }

    /**
     * エラー情報を追加する
     *
     * @param error
     *     エラー情報
     */
    public void addError(ErrorData error) {
        if (CollectionUtil.isEmpty(errorList)) {
            errorList = new ArrayList<>();
        }
        errorList.add(error);
    }

    /**
     * ページングを返す
     *
     * @return paging
     */
    public PagingView getPaging() {
        return paging;
    }

    /**
     * ページングを設定する
     *
     * @param paging
     *     ページング
     */
    public void setPaging(PagingView paging) {
        this.paging = paging;
    }

}
