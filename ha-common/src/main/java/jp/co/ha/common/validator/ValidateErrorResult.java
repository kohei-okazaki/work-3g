package jp.co.ha.common.validator;

import java.util.ArrayList;
import java.util.List;

import jp.co.ha.common.util.CollectionUtil;

/**
 * 妥当性チェック結果保持クラス
 *
 * @since 1.0
 */
public class ValidateErrorResult {

    /** 妥当性チェックのリスト */
    private List<ValidateError> errorList;

    /**
     * コンストラクタ
     */
    public ValidateErrorResult() {
        this.errorList = new ArrayList<>();
    }

    /**
     * エラー情報を追加する
     *
     * @param error
     *     エラー情報
     */
    public void add(ValidateError error) {
        this.errorList.add(error);
    }

    /**
     * エラー情報を保持しているか返す
     *
     * @return エラー情報はある場合true, それ以外の場合false
     */
    public boolean hasError() {
        return CollectionUtil.exists(errorList);
    }

    /**
     * 妥当性チェックエラーを返す
     *
     * @return ValidateError
     */
    public ValidateError getFirst() {
        return CollectionUtil.getFirst(errorList);
    }

    /**
     * 妥当性チェックエラー
     *
     * @since 1.0
     */
    public static class ValidateError {

        /** フィールド名 */
        private String name;
        /** エラーメッセージ */
        private String message;
        /** 値 */
        private String value;

        /**
         * nameを返す
         *
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * nameを設定する
         *
         * @param name
         *     フィールド名
         */
        public void setName(String name) {
            this.name = name;
        }

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
         *     エラーメッセージ
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * valueを返す
         *
         * @return value
         */
        public String getValue() {
            return value;
        }

        /**
         * valueを設定する
         *
         * @param value
         *     値
         */
        public void setValue(String value) {
            this.value = value;
        }

    }

}
