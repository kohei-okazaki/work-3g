package jp.co.ha.common.web;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.util.StringUtil;

/**
 * 基底Validator<br>
 * すべてのvalidatorはこの抽象クラスを継承すること<br>
 *
 * @param <F> validate対象form
 */
public abstract class BaseValidator<F extends BaseForm> implements Validator {

	/**
	 * fieldのformの値が空文字の場合、errorsオブジェクトにエラーを追加する<br>
	 * @param errors
	 * @param field
	 */
	protected void rejectIfEmpty(Errors errors, String field) {
		ValidationUtils.rejectIfEmpty(errors, field, ErrorCode.REQUIRE.getErrorCode());
	}

	/**
	 * fieldのformの値がmaxを超過してる場合、errorsオブジェクトにエラーを追加する<br>
	 * @param errors
	 * @param field
	 * @param max
	 */
	protected void rejectIfLengthMax(Errors errors, String field, int max) {
		String fieldValue = errors.getFieldValue(field).toString();
		if (max < fieldValue.length()) {
			errors.rejectValue(fieldValue, ErrorCode.LENGTH.getErrorCode());
		}
	}

	/**
	 * fieldのformの値がmin未満の場合、errorsオブジェクトにエラーを追加する<br>
	 * @param errors
	 * @param field
	 * @param min
	 */
	protected void rejectIfLengthMin(Errors errors, String field, int min) {
		String fieldValue = errors.getFieldValue(field).toString();
		if (fieldValue.length() < min) {
			errors.rejectValue(fieldValue, ErrorCode.LENGTH.getErrorCode());
		}
	}

	/**
	 * fieldValueが半角数字-ピリオドでない場合、errorsオブジェクトにエラーを追加する<br>
	 * @param errors
	 * @param field
	 */
	protected void rejectIfNotHalfNumberPeriod(Errors errors, String field) {
		String fieldValue = errors.getFieldValue(field).toString();
		if (!StringUtil.isHalfNumberPeriod(fieldValue)) {
			errors.rejectValue(field, "errors.halfNumberPeriod");
		}
	}
}
