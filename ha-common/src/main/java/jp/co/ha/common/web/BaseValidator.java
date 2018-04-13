package jp.co.ha.common.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 基底Validator<br>
 * すべてのvalidatorはこの抽象クラスを継承すること<br>
 *
 * @param <F> validate対象form
 */
public abstract class BaseValidator<F extends BaseForm> implements Validator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract boolean supports(Class<?> clazz);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract void validate(Object target, Errors errors);

}
