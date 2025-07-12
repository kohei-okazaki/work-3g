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
         * messageを返す
         *
         * @return message
         */
        public String getMessage() {
            return message;
        }

        /**
         * messageを設定する
         *
         * @param message
         *     メッセージ
         */
        public void setMessage(String message) {
            this.message = message;
        }

    }

    /**
     * rootApiResultを返す
     *
     * @return rootApiResult
     */
    public RootApiResult getRootApiResult() {
        return rootApiResult;
    }

    /**
     * rootApiResultを設定する
     *
     * @param rootApiResult
     *     処理結果
     */
    public void setRootApiResult(RootApiResult rootApiResult) {
        this.rootApiResult = rootApiResult;
    }

    /**
     * errorListを返す
     *
     * @return errorList
     */
    public List<ErrorData> getErrorList() {
        return errorList;
    }

    /**
     * errorListを設定する
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
     * pagingを返す
     *
     * @return paging
     */
    public PagingView getPaging() {
        return paging;
    }

    /**
     * pagingを設定する
     *
     * @param paging
     *     ページング
     */
    public void setPaging(PagingView paging) {
        this.paging = paging;
    }

}
