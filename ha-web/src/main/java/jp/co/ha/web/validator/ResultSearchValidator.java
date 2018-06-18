package jp.co.ha.web.validator;

import org.springframework.validation.Errors;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.BaseValidator;
import jp.co.ha.web.form.ResultSearchForm;

/**
 * 結果照会画面のValidatorクラス<br>
 *
 */
public class ResultSearchValidator extends BaseValidator<ResultSearchForm> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return ResultSearchForm.class.isAssignableFrom(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object target, Errors errors) {
		ResultSearchForm form = (ResultSearchForm) target;

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
	private void correlationCheck(ResultSearchForm form, Errors errors) {

		if (StringUtil.isEmpty(form.getDataId())) {
			if (StringUtil.isTrue(form.getRegDateSelectFlag())) {
				// 直接指定フラグが指定されてる場合
				if (StringUtil.isEmpty(form.getFromRegDate())) {
					errors.rejectValue("fromRegDate", "validate.message.NotEmpty", new String[] {"登録日時"}, "validate.message.NotEmpty");
				}
			}
		}

	}

}
