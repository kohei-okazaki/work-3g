package jp.co.ha.web.validator;

import org.springframework.validation.Errors;

import jp.co.ha.common.web.BaseValidator;
import jp.co.ha.web.form.HealthInfoForm;

/**
 * 健康情報登録フォームvalidator
 *
 */
public class HealthInfoValidator extends BaseValidator<HealthInfoForm> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object target, Errors errors) {

		HealthInfoForm form = (HealthInfoForm) target;
//		// 必須チェック
//		checKRequire(errors);
//		// 属性チェック
//		checkType(errors);
	}

	/**
	 * fieldValueが半角数字-ピリオドでない場合、errorsオブジェクトにエラーを追加する<br>
	 * @param errors
	 */
	private void checkType(Errors errors) {

		rejectIfNotHalfNumberPeriod(errors, "height");
		rejectIfNotHalfNumberPeriod(errors, "weight");
		rejectIfNotHalfNumberPeriod(errors, "bmi");
		rejectIfNotHalfNumberPeriod(errors, "standardWeight");
	}

	/**
	 * 必須チェックを行う<br>
	 * @param errors
	 */
	private void checKRequire(Errors errors) {

		rejectIfEmpty(errors, "userId");
		rejectIfEmpty(errors, "height");
		rejectIfEmpty(errors, "weight");
		rejectIfEmpty(errors, "bmi");
		rejectIfEmpty(errors, "standardWeight");
	}

}
