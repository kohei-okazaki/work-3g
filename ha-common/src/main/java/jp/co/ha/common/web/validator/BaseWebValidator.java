package jp.co.ha.common.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.type.RegixType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.form.BaseForm;

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
	 * @param fieldsName
	 *     画面表示項目名
	 * @param msgArgs
	 *     メッセージ引数
	 */
	protected void rejectIfEmpty(Errors errors, String fieldsName, String msgArgs) {
		Object field = getFieldValue(errors, fieldsName);
		if (BeanUtil.isNull(field) || StringUtil.isEmpty(field.toString())) {
			errors.rejectValue(fieldsName, ErrorCode.REQUIRE.getErrorMessage(), new String[] { msgArgs },
					ErrorCode.REQUIRE.getErrorMessage());
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
			errors.rejectValue(fieldValue, ErrorCode.LENGTH_OVER.getErrorMessage());
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
			errors.rejectValue(fieldValue, ErrorCode.LENGTH_LACK.getErrorMessage());
		}
	}

	/**
	 * fieldValueが半角数字-ピリオドでない場合、errorsオブジェクトにエラーを追加する<br>
	 *
	 * @param errors
	 *     Errors
	 * @param field
	 *     Formフィールド名
	 * @param nameArgs
	 *     名前
	 */
	protected void rejectIfNotHalfNumberPeriod(Errors errors, String field, String nameArgs) {
		if (!RegixType.HALF_NUMBER_PERIOD.is().test(getFieldValue(errors, field))) {
			errors.rejectValue(field, ErrorCode.TYPE_VALID.getErrorMessage(), new String[] { nameArgs },
					ErrorCode.TYPE_VALID.getErrorMessage());
		}
	}

	/**
	 * 入力値を返す<br>
	 *
	 * @param errors
	 *     Errors
	 * @param field
	 *     Formフィールド名
	 * @return 入力値
	 */
	protected String getFieldValue(Errors errors, String field) {
		Object value = errors.getFieldValue(field);
		return BeanUtil.isNull(value) ? StringUtil.EMPTY : value.toString();
	}
}
