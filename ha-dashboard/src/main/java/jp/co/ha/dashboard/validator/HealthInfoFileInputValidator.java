package jp.co.ha.dashboard.validator;

import org.springframework.validation.Errors;

import jp.co.ha.common.exception.ValidateErrorCode;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.dashboard.form.HealthInfoFileForm;
import jp.co.ha.web.validator.BaseWebValidator;

/**
 * 健康情報CSVアップロードvalidator
 *
 */
public class HealthInfoFileInputValidator extends BaseWebValidator<HealthInfoFileForm> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void validate(HealthInfoFileForm form, Errors errors) {

		// 必須チェック
		this.checkRequire(form, errors);

	}

	/**
	 * 必須チェックを行う
	 *
	 * @param form
	 *     健康情報ファイル入力画面フォーム
	 * @param errors
	 *     エラークラス
	 */
	private void checkRequire(HealthInfoFileForm form, Errors errors) {
		if (BeanUtil.isNull(form.getMultipartFile())) {
			errors.rejectValue("multipartFile", ValidateErrorCode.REQUIRE.getOuterErrorCode(), new String[] { "健康情報CSVファイル" },
					ValidateErrorCode.REQUIRE.getOuterErrorCode());
		}
	}



}
