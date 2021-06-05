package jp.co.ha.business.api.root.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.type.BaseEnum;

/**
 * RootAPIの基底レスポンスクラス
 *
 * @version 1.0.0
 */
public abstract class BaseRootApiResponse {

    /** 処理結果 */
    @JsonProperty("result")
    private Result result;

    /**
     * resultを返す
     *
     * @return result
     */
    public Result getResult() {
        return result;
    }

    /**
     * resultを設定する
     *
     * @param result
     *     処理結果
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * 処理結果の列挙
     *
     * @version 1.0.0
     */
    public static enum Result implements BaseEnum {

        /** 成功 */
        SUCCESS("0"),
        /** 失敗 */
        FAILURE("1");

        /** 値 */
        private String value;

        /**
         * コンストラクタ
         *
         * @param value
         *     値
         */
        private Result(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        /**
         * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
         * @param value
         *     値
         * @return Result
         */
        public static Result of(String value) {
            return BaseEnum.of(Result.class, value);
        }

    }
}
