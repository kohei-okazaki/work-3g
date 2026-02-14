package jp.co.ha.common.validator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Component;

import jp.co.ha.common.exception.SystemRuntimeException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.BeanUtil.AccessorType;
import jp.co.ha.common.validator.ValidateErrorResult.ValidateError;
import jp.co.ha.common.validator.annotation.Flag;
import jp.co.ha.common.validator.annotation.Length;
import jp.co.ha.common.validator.annotation.Max;
import jp.co.ha.common.validator.annotation.Min;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.validator.annotation.ValidateIgnore;

/**
 * 妥当性チェッククラス<br>
 * 以下の順に妥当性チェック実施
 *
 * <ul>
 * <li>必須チェック</li>
 * <li>最大桁数チェック</li>
 * <li>最小桁数チェック</li>
 * <li>文字長チェック</li>
 * <li>型チェック</li>
 * <li>フラグ型チェック</li>
 * </ul>
 *
 * @param <T>
 *     検査対象クラス
 * @version 1.0.0
 */
@Component
public class BeanValidator<T> {

    /**
     * 指定したクラスの妥当性チェックを行う<br>
     * <code>ignore</code>は妥当性チェックを行わない
     *
     * @param t
     *     validate対象クラス
     * @param ignore
     *     妥当性チェックを行わないフィールド
     * @return 妥当性チェック結果
     */
    @SuppressWarnings("unchecked")
    public ValidateErrorResult validate(T t, String... ignore) {
        ValidateErrorResult result = new ValidateErrorResult();
        Class<T> clazz = (Class<T>) t.getClass();
        try {
            for (Field f : BeanUtil.getFieldList(clazz)) {
                if (isIgnore(f) || Arrays.asList(ignore).contains(f.getName())) {
                    continue;
                }
                Object value = BeanUtil
                        .getAccessor(f.getName(), clazz, AccessorType.GETTER).invoke(t);
                String property = Optional.ofNullable(value).orElse("").toString();
                if (f.isAnnotationPresent(Required.class)) {
                    validateRequired(property, f, result);
                }
                if (f.isAnnotationPresent(Min.class)) {
                    validateMin(property, f, result);
                }
                if (f.isAnnotationPresent(Max.class)) {
                    validateMax(property, f, result);
                }
                if (f.isAnnotationPresent(Length.class)) {
                    validateLength(property, f, result);
                }
                if (f.isAnnotationPresent(Pattern.class)) {
                    validatePattern(property, f, result);
                }
                if (f.isAnnotationPresent(Flag.class)) {
                    validateFlag(property, f, result);
                }
            }
        } catch (Exception e) {
            throw new SystemRuntimeException(e);
        }

        return result;
    }

    /**
     * 指定したクラスの妥当性チェックを行う
     *
     * @param t
     *     validate対象クラス
     * @return 妥当性チェック結果
     */
    public ValidateErrorResult validate(T t) {
        return validate(t, "");
    }

    /**
     * validate処理を実施するフィールドがどうか判定する
     *
     * @param f
     *     フィールド
     * @return validate処理を実施しない場合true, それ以外の場合false
     */
    private boolean isIgnore(Field f) {
        return f.isAnnotationPresent(ValidateIgnore.class);
    }

    /**
     * 文字列型の必須チェック
     *
     * @param value
     *     値
     * @param f
     *     フィールド
     * @param result
     *     妥当性チェック結果
     */
    private void validateRequired(String value, Field f, ValidateErrorResult result) {
        RequiredValidator validator = new RequiredValidator();
        validator.initialize(f.getAnnotation(Required.class));
        boolean notError = validator.isValid(value, null);
        if (!notError) {
            result.add(new ValidateError(f.getName(),
                    f.getAnnotation(Required.class).message(), value));
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
    private void validateMin(String value, Field f, ValidateErrorResult result) {
        MinValidator validator = new MinValidator();
        validator.initialize(f.getAnnotation(Min.class));
        boolean notError = validator.isValid(value, null);
        if (!notError) {
            result.add(new ValidateError(f.getName(),
                    f.getAnnotation(Min.class).message(), value));
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
    private void validateMax(String value, Field f, ValidateErrorResult result) {
        MaxValidator validator = new MaxValidator();
        validator.initialize(f.getAnnotation(Max.class));
        boolean notError = validator.isValid(value, null);
        if (!notError) {
            result.add(new ValidateError(f.getName(),
                    f.getAnnotation(Max.class).message(), value));
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
    private void validateLength(String value, Field f, ValidateErrorResult result) {
        LengthValidator validator = new LengthValidator();
        validator.initialize(f.getAnnotation(Length.class));
        boolean notError = validator.isValid(value, null);
        if (!notError) {
            result.add(new ValidateError(f.getName(),
                    f.getAnnotation(Length.class).message(), value));
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
    private void validatePattern(String value, Field f, ValidateErrorResult result) {
        PatternValidator validator = new PatternValidator();
        validator.initialize(f.getAnnotation(Pattern.class));
        boolean notError = validator.isValid(value, null);
        if (!notError) {
            result.add(new ValidateError(f.getName(),
                    f.getAnnotation(Pattern.class).message(), value));
        }
    }

    /**
     * フラグ型チェック
     *
     * @param value
     *     値
     * @param f
     *     フィールド
     * @param result
     *     妥当性チェック結果
     */
    private void validateFlag(String value, Field f, ValidateErrorResult result) {
        FlagValidator validator = new FlagValidator();
        validator.initialize(f.getAnnotation(Flag.class));
        boolean notError = validator.isValid(value, null);
        if (!notError) {
            result.add(new ValidateError(f.getName(),
                    f.getAnnotation(Flag.class).message(), value));
        }
    }

}
