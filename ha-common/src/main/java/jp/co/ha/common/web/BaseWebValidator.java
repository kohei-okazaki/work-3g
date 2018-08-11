package jp.co.ha.common.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.type.RegixType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;

/**
 * 基底Validator<br>
 * webのvalidatorはこの抽象クラスを継承すること<br>
 *
 * @param <F>
 *     validate対象form
 */
public abstract class BaseWebValidator<F extends BaseForm> implements Validator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		Class<?> clz = BeanUtil.getParameterType(this.getClass());
		return clz.isAssignableFrom(clazz);
	}

	/**
	 * fieldのformの値が空文字の場合、errorsオブジェクトにエラーを追加する<br>
	 *
	 * @param errors
	 *     エラー情報
	 * @param field
	 *     検査対象field
	 * @param fieldsName
	 *     画面表示項目名
	 * @param nameArg
	 *     メッセージ引数
	 */
	protected void rejectIfEmpty(Errors errors, String fieldsName, String nameArgs) {
		// 値を取得
		Object field = getFieldValue(errors, fieldsName);
		if (BeanUtil.isNull(field) || StringUtil.isEmpty(field.toString())) {
			errors.rejectValue(fieldsName, "validate.message.NotEmpty", new String[] { nameArgs }, "validate.message.NotEmpty");
		}
	}

	/**
	 * fieldのformの値がmaxを超過してる場合、errorsオブジェクトにエラーを追加する<br>
	 *
	 * @param errors
	 *     Errors
	 * @param field
	 *     Formフィールド名
	 * @param max
	 *     最大値
	 */
	protected void rejectIfLengthMax(Errors errors, String field, int max) {
		String fieldValue = getFieldValue(errors, field);
		if (max < fieldValue.length()) {
			errors.rejectValue(fieldValue, ErrorCode.LENGTH_OVER.getInternalErrorCode());
		}
	}

	/**
	 * fieldのformの値がmin未満の場合、errorsオブジェクトにエラーを追加する<br>
	 *
	 * @param errors
	 *     Errors
	 * @param field
	 *     Formフィールド名
	 * @param min
	 *     最小値
	 */
	protected void rejectIfLengthMin(Errors errors, String field, int min) {
		String fieldValue = getFieldValue(errors, field);
		if (fieldValue.length() < min) {
			errors.rejectValue(fieldValue, ErrorCode.LENGTH_LACK.getInternalErrorCode());
		}
	}

	/**
	 * fieldValueが半角数字-ピリオドでない場合、errorsオブジェクトにエラーを追加する<br>
	 *
	 * @param errors
	 *     Errors
	 * @param field
	 *     Formフィールド名
	 */
	protected void rejectIfNotHalfNumberPeriod(Errors errors, String fieldsName, String nameArgs) {
		// 値を取得
		String fieldValue = getFieldValue(errors, fieldsName);
		if (!RegixType.isPattern(fieldValue, RegixType.HALF_NUMBER_PERIOD)) {
			errors.rejectValue(fieldsName, "validate.message.TypeError", new String[] { nameArgs }, "validate.message.TypeError");
		}
	}

	/**
	 * 入力値を返す<br>
	 *
	 * @param errors
	 *     Errors
	 * @param field
	 *     Formフィールド名
	 * @return
	 */
	protected String getFieldValue(Errors errors, String field) {
		Object value = errors.getFieldValue(field);
		return BeanUtil.isNull(value) ? StringUtil.EMPTY : value.toString();
	}
}
