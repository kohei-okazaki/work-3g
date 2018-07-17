package jp.co.ha.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import jp.co.ha.common.util.BeanUtil;
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
		System.out.println(form.toString());
		// 必須チェック
		checKRequire(errors);
		// 属性チェック
		checkType(errors);
	}

	/**
	 * 属性チェックを行う<br>
	 *
	 * @param errors
	 *     エラー
	 */
	private void checkType(Errors errors) {
		FieldError e = errors.getFieldError("height");
		if (BeanUtil.isNull(e)) {
			return;
		}
		boolean b = e.isBindingFailure();
		if (b) {
			rejectIfNotHalfNumberPeriod(errors, "height", "身長");
		}
//		rejectIfNotHalfNumberPeriod(errors, "height", "身長");
		rejectIfNotHalfNumberPeriod(errors, "weight", "体重");
	}

	/**
	 * 必須チェックを行う<br>
	 *
	 * @param errors
	 *     エラー
	 */
	private void checKRequire(Errors errors) {
		rejectIfEmpty(errors, "height", "身長");
		rejectIfEmpty(errors, "weight", "体重");
	}

}
