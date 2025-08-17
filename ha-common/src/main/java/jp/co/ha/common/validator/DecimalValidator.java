package jp.co.ha.common.validator;

import java.math.BigDecimal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.validator.annotation.Decimal;

/**
 * 浮動小数の妥当性チェックvalidator
 *
 * @see jp.co.ha.common.validator.annotation.Decimal
 * @version 1.0.0
 */
public class DecimalValidator implements ConstraintValidator<Decimal, Number> {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(DecimalValidator.class);

    /** 最小値 */
    private BigDecimal min;
    /** 最大値 */
    private BigDecimal max;
    /** 最小値で同値含むか */
    private boolean minEqual;
    /** 最大値で同値含むか */
    private boolean maxEqual;

    @Override
    public void initialize(Decimal annotation) {
        // アノテーションで設定された値をBigDecimalに変換
        this.min = new BigDecimal(annotation.min());
        this.max = new BigDecimal(annotation.max());
        this.minEqual = annotation.minEqual();
        this.maxEqual = annotation.maxEqual();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        if (BeanUtil.isNull(value)) {
            // nullはチェック対象外
            return true;
        }

        try {
            BigDecimal val = new BigDecimal(value.toString());

            boolean minCheck = minEqual ? (val.compareTo(min) >= 0)
                    : (val.compareTo(min) > 0);
            boolean maxCheck = maxEqual ? (val.compareTo(max) <= 0)
                    : (val.compareTo(max) < 0);

            return minCheck && maxCheck;
        } catch (NumberFormatException e) {
            LOG.error("数値以外のフィールドに@Decimalが指定されています。", e);
            return false;
        }
    }

}
