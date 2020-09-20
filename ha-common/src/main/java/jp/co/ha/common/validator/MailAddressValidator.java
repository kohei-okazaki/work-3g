package jp.co.ha.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.MailAddress;

/**
 * メールアドレス形式の妥当性チェックvalidator
 *
 * @see jp.co.ha.common.validator.annotation.MailAddress
 * @version 1.0.0
 */
public class MailAddressValidator implements ConstraintValidator<MailAddress, Object> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (BeanUtil.isNull(value) || StringUtil.isEmpty(value.toString())) {
            return true;
        }
        return RegexType.MAIL_ADDRESS.is(value.toString());
    }

}
