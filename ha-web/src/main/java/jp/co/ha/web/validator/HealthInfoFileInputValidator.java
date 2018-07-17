package jp.co.ha.web.validator;

import org.springframework.validation.Errors;

import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.BaseValidator;
import jp.co.ha.web.form.HealthInfoFileForm;

/**
 * 健康情報CSVアップロードvalidatorクラス<br>
 *
 */
public class HealthInfoFileInputValidator extends BaseValidator<HealthInfoFileForm> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object target, Errors errors) {
		HealthInfoFileForm form = (HealthInfoFileForm) target;

		this.checkRequire(form, errors);
	}

	/**
	 * 必須チェックを行う<br>
	 *
	 * @param form
	 *     健康情報ファイル入力画面フォーム
	 * @param errors
	 */
	private void checkRequire(HealthInfoFileForm form, Errors errors) {
		if (BeanUtil.isNull(form.getMultipartFile())) {
			errors.rejectValue("multipartFile", "validate.message.NotEmpty", new String[] {"健康情報CSVファイル"}, "validate.message.NotEmpty");
		}
	}

}
