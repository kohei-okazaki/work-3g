package jp.co.ha.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.type.RegixType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Decimal;

/**
 * 浮動小数の妥当性チェックvalidator
 *
 * @see jp.co.ha.common.validator.DecimalValidator
 *
 */
public class DecimalValidator implements ConstraintValidator<Decimal, Object> {

	/** 最小桁数 */
	private int min;
	/** 最大桁数 */
	private int max;
	/** 最小桁数で同値含むか */
	private boolean minEqual;
	/** 最大桁数で同値含むか */
	private boolean maxEqual;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(Decimal annotation) {
		this.min = annotation.min();
		this.max = annotation.max();
		this.minEqual = annotation.minEqual();
		this.maxEqual = annotation.maxEqual();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (BeanUtil.isNull(value) || StringUtil.isEmpty(value.toString())) {
			return true;
		}
		if (RegixType.DECIMAL.is().test(value.toString())) {
			int length = value.toString().replaceAll(".", "").length();
			if (minEqual && maxEqual) {
				return (min <= length) && (length <= max);
			} else if (minEqual) {
				return (min <= length) && (length < max);
			} else if (maxEqual) {
				return (min < length) && (length <= max);
			} else {
				return (min < length) && (length < max);
			}
		} else {
			return false;
		}
	}

}
