package jp.co.ha.web.validator;

import org.springframework.validation.Errors;

import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.validator.BaseWebValidator;
import jp.co.ha.web.form.HealthInfoReferenceForm;

/**
 * 結果照会画面のValidator
 *
 */
public class HealthInfoReferenceValidator extends BaseWebValidator<HealthInfoReferenceForm> {

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
	 * 相関チェックを行う
	 *
	 * @param form
	 *     結果検索フォーム
	 * @param errors
	 *     エラークラス
	 */
	private void correlationCheck(HealthInfoReferenceForm form, Errors errors) {

		if (StringUtil.isEmpty(form.getHealthInfoId())) {
			if (StringUtil.isTrue(form.getRegDateSelectFlag())) {
				// 直接指定フラグが指定されてる場合
				if (StringUtil.isEmpty(form.getFromRegDate())) {
					errors.rejectValue("fromRegDate", ErrorCode.REQUIRE.getValidateMessage(), new String[] { "登録日時" },
							ErrorCode.REQUIRE.getValidateMessage());
				}
			} else {
				if (StringUtil.isEmpty(form.getFromRegDate())) {
					errors.rejectValue("fromRegDate", ErrorCode.REQUIRE.getValidateMessage(), new String[] { "登録日時(開始) " },
							ErrorCode.REQUIRE.getValidateMessage());
				}
				if (StringUtil.isEmpty(form.getToRegDate())) {
					errors.rejectValue("toRegDate", ErrorCode.REQUIRE.getValidateMessage(), new String[] { "登録日時(終了)" },
							ErrorCode.REQUIRE.getValidateMessage());
				}
			}
		}
	}
}
