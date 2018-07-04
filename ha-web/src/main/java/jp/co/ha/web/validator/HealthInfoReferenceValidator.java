package jp.co.ha.web.validator;

import org.springframework.validation.Errors;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.BaseValidator;
import jp.co.ha.web.form.HealthInfoReferenceForm;

/**
 * 結果照会画面のValidatorクラス<br>
 *
 */
public class HealthInfoReferenceValidator extends BaseValidator<HealthInfoReferenceForm> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object target, Errors errors) {
		HealthInfoReferenceForm form = (HealthInfoReferenceForm) target;

		// 相関チェック
		correlationCheck(form, errors);

	}

	/**
	 * 相関チェックを行う<br>
	 *
	 * @param form
	 *            結果検索フォーム
	 * @param errors
	 *            Errors
	 */
	private void correlationCheck(HealthInfoReferenceForm form, Errors errors) {

		if (StringUtil.isEmpty(form.getHealthInfoId())) {
			if (StringUtil.isTrue(form.getRegDateSelectFlag())) {
				// 直接指定フラグが指定されてる場合
				if (StringUtil.isEmpty(form.getFromRegDate())) {
					errors.rejectValue("fromRegDate", "validate.message.NotEmpty", new String[] {"登録日時"}, "validate.message.NotEmpty");
				}
			} else {
				if (StringUtil.isEmpty(form.getFromRegDate())) {
					errors.rejectValue("fromRegDate", "validate.message.NotEmpty", new String[] {"登録日時(開始)"}, "validate.message.NotEmpty");
				}
				if (StringUtil.isEmpty(form.getFromRegDate())) {
					errors.rejectValue("toRegDate", "validate.message.NotEmpty", new String[] {"登録日時(終了)"}, "validate.message.NotEmpty");
				}
			}
		}

	}

}
