package jp.co.ha.common.validator;

import java.util.ArrayList;
import java.util.List;

import jp.co.ha.common.util.CollectionUtil;

/**
 * 妥当性チェック結果保持クラス
 *
 * @version 1.0.0
 */
public class ValidateErrorResult {

    /** 妥当性チェックのリスト */
    private List<ValidateError> errorList = new ArrayList<>();

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
     * エラー情報リストを返す
     *
     * @return errorList
     */
    public List<ValidateError> getErrorList() {
        return errorList;
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
     * @version 1.0.0
     * @param name
     *     フィールド名
     * @param message
     *     エラーメッセージ
     * @param value
     *     値
     */
    public static record ValidateError(String name, String message, String value) {
    }

}
