package jp.co.ha.common.validator;

import java.util.ArrayList;
import java.util.List;

import jp.co.ha.common.util.CollectionUtil;

/**
 * 妥当性チェック結果保持クラス
 *
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

}
