package jp.co.ha.common.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validate.annotation.Required;

/**
 * 必須チェックvalidator<br>
 *
 */
public class RequiredValidator implements ConstraintValidator<Required, Object> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		if (BeanUtil.isNull(value)) {
			// フィールドがBigDecimalなどの数値の型の場合、ここでfalseを返す
			return false;
		} else {
			boolean b = StringUtil.isEmpty(value.toString());
			return !StringUtil.isEmpty(value.toString());
		}

	}

}
