package jp.co.ha.dashboard.validator;

import org.springframework.validation.Errors;

import jp.co.ha.common.exception.ValidateErrorCode;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.validator.BaseWebValidator;
import jp.co.ha.dashboard.form.HealthInfoReferenceForm;

/**
 * 結果照会画面のValidator
 *
 */
public class HealthInfoReferenceValidator extends BaseWebValidator<HealthInfoReferenceForm> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void validate(HealthInfoReferenceForm form, Errors errors) {

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
			if (CommonFlag.TRUE.is(form.getHealthInfoRegDateSelectFlag())) {
				// 直接指定フラグが指定されてる場合
				if (StringUtil.isEmpty(form.getFromHealthInfoRegDate())) {
					errors.rejectValue("fromHealthInfoRegDate", ValidateErrorCode.REQUIRE.getOuterErrorCode(), new String[] { "健康情報作成日" },
							ValidateErrorCode.REQUIRE.getOuterErrorCode());
				}
			} else {
				if (StringUtil.isEmpty(form.getFromHealthInfoRegDate())) {
					errors.rejectValue("fromHealthInfoRegDate", ValidateErrorCode.REQUIRE.getOuterErrorCode(), new String[] { "健康情報作成日(開始)" },
							ValidateErrorCode.REQUIRE.getOuterErrorCode());
				}
				if (StringUtil.isEmpty(form.getToHealthInfoRegDate())) {
					errors.rejectValue("toHealthInfoRegDate", ValidateErrorCode.REQUIRE.getOuterErrorCode(), new String[] { "健康情報作成日(終了)" },
							ValidateErrorCode.REQUIRE.getOuterErrorCode());
				}
			}
		}
	}


}
