package jp.co.ha.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Url;

/**
 * URL妥当性チェックvalidator
 *
 * @see jp.co.ha.common.validator.annotation.Url
 * @version 1.0.0
 */
public class UrlValidator implements ConstraintValidator<Url, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (BeanUtil.isNull(value) || StringUtil.isEmpty(value.toString())) {
            return true;
        }
        return RegexType.URL.is(value.toString());
    }

}
