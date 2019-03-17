package jp.co.ha.common.validator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.BeanUtil.AccessorType;
import jp.co.ha.common.validator.annotation.Length;
import jp.co.ha.common.validator.annotation.Max;
import jp.co.ha.common.validator.annotation.Min;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;

/**
 * 妥当性チェッククラス<br>
 * 以下の順に妥当性チェックに実施
 * <ul>
 * <li>必須チェック</li>
 * <li>最大桁数チェック</li>
 * <li>最小桁数チェック</li>
 * <li>文字長チェック</li>
 * <li>型チェック</li>
 * </ul>
 *
 * @param <T>
 *     検査対象クラス
 */
public class BeanValidator<T> {

	/** LOG */
	private final static Logger LOG = LoggerFactory.getLogger(BeanValidator.class);

	/**
	 * 指定したクラスの妥当性チェックを行う
	 *
	 * @param t
	 *     validate対象クラス
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ValidateErrorResult validate(T t) {
		ValidateErrorResult result = new ValidateErrorResult();
		Class<T> clazz = (Class<T>) t.getClass();
		List<Field> list = BeanUtil.getFieldList(clazz);
		try {
			for (Field f : list) {
				Method getter = BeanUtil.getAccessor(f.getName(), clazz, AccessorType.GETTER);
				Object value = getter.invoke(t);
				if (f.isAnnotationPresent(Required.class)) {
					validateRequired(value, f, result);
				}
				if (f.isAnnotationPresent(Min.class)) {
					validateMin(value, f, result);
				}
				if (f.isAnnotationPresent(Max.class)) {
					validateMax(value, f, result);
				}
				if (f.isAnnotationPresent(Length.class)) {
					validateLength(value, f, result);
				}
				if (f.isAnnotationPresent(Pattern.class)) {
					validatePattern(value, f, result);
				}
			}
		} catch (Exception e) {
			LOG.error("validate処理に失敗しました", e);
		}

		return result;
	}

	/**
	 * 必須チェック
	 *
	 * @param value
	 *     値
	 * @param f
	 *     フィールド
	 * @param result
	 *     妥当性チェック結果
	 */
	private void validateRequired(Object value, Field f, ValidateErrorResult result) {
		RequiredValidator validator = new RequiredValidator();
		validator.initialize(f.getAnnotation(Required.class));
		boolean notError = validator.isValid(value, null);
		if (!notError) {
			ValidateError error = new ValidateError();
			error.setName(f.getName());
			error.setMessage(f.getAnnotation(Required.class).message());
			error.setValue(null);
			result.add(error);
		}
	}

	/**
	 * 最小桁数チェック
	 *
	 * @param value
	 *     値
	 * @param f
	 *     フィールド
	 * @param result
	 *     妥当性チェック結果
	 */
	private void validateMin(Object value, Field f, ValidateErrorResult result) {
		MinValidator validator = new MinValidator();
		validator.initialize(f.getAnnotation(Min.class));
		boolean notError = validator.isValid(value.toString(), null);
		if (!notError) {
			ValidateError error = new ValidateError();
			error.setName(f.getName());
			error.setMessage(f.getAnnotation(Min.class).message());
			error.setValue(value.toString());
			result.add(error);
		}
	}

	/**
	 * 最大桁数チェック
	 *
	 * @param value
	 *     値
	 * @param f
	 *     フィールド
	 * @param result
	 *     妥当性チェック結果
	 */
	private void validateMax(Object value, Field f, ValidateErrorResult result) {
		MaxValidator validator = new MaxValidator();
		validator.initialize(f.getAnnotation(Max.class));
		boolean notError = validator.isValid(value.toString(), null);
		if (!notError) {
			ValidateError error = new ValidateError();
			error.setName(f.getName());
			error.setMessage(f.getAnnotation(Max.class).message());
			error.setValue(value.toString());
			result.add(error);
		}
	}

	/**
	 * 文字長チェック
	 *
	 * @param value
	 *     値
	 * @param f
	 *     フィールド
	 * @param result
	 *     妥当性チェック結果
	 */
	private void validateLength(Object value, Field f, ValidateErrorResult result) {
		LengthValidator validator = new LengthValidator();
		validator.initialize(f.getAnnotation(Length.class));
		boolean notError = validator.isValid(value.toString(), null);
		if (!notError) {
			ValidateError error = new ValidateError();
			error.setName(f.getName());
			error.setMessage(f.getAnnotation(Length.class).message());
			error.setValue(value.toString());
			result.add(error);
		}
	}

	/**
	 * 型チェック
	 *
	 * @param value
	 *     値
	 * @param f
	 *     フィールド
	 * @param result
	 *     妥当性チェック結果
	 */
	private void validatePattern(Object value, Field f, ValidateErrorResult result) {
		PatternValidator validator = new PatternValidator();
		validator.initialize(f.getAnnotation(Pattern.class));
		boolean notError = validator.isValid(value.toString(), null);
		if (!notError) {
			ValidateError error = new ValidateError();
			error.setName(f.getName());
			error.setMessage(f.getAnnotation(Pattern.class).message());
			error.setValue(value.toString());
			result.add(error);
		}
	}

}
