package jp.co.ha.web.validator;

import org.springframework.validation.Errors;

import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.validator.BaseWebValidator;
import jp.co.ha.web.form.HealthInfoFileForm;

/**
 * 健康情報CSVアップロードvalidator
 *
 */
public class HealthInfoFileInputValidator extends BaseWebValidator<HealthInfoFileForm> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object target, Errors errors) {
		HealthInfoFileForm form = (HealthInfoFileForm) target;

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
			errors.rejectValue("multipartFile", ErrorCode.REQUIRE.getValidateMessage(), new String[] { "健康情報CSVファイル" },
					ErrorCode.REQUIRE.getValidateMessage());
		}
	}

}
