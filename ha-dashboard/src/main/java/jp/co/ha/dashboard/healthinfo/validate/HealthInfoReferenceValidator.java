package jp.co.ha.dashboard.healthinfo.validate;

import static jp.co.ha.common.exception.ValidateErrorCode.*;

import java.time.LocalDate;

import org.springframework.validation.Errors;

import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.validator.BaseWebValidator;
import jp.co.ha.dashboard.healthinfo.form.HealthInfoReferenceForm;

/**
 * 結果照会画面のValidator
 *
 * @version 1.0.0
 */
public class HealthInfoReferenceValidator
        extends BaseWebValidator<HealthInfoReferenceForm> {

    @Override
    public void validate(HealthInfoReferenceForm form, Errors errors) {

        // 相関チェック
        correlationCheck(form, errors);

    }

    /**
     * 相関チェックを行う
     *
     * @param form
     *     結果検索フォーム
     * @param errors
     *     {@linkplain Errors}
     */
    private void correlationCheck(HealthInfoReferenceForm form, Errors errors) {

        if (StringUtil.hasValue(form.getSeqHealthInfoId())) {
            // 健康情報IDを指定している場合、後続の日付チェックを行わない
            return;
        }

        if (CommonFlag.TRUE.is(form.getHealthInfoRegDateSelectFlag())) {
            // 健康情報作成日の直接指定フラグが指定されてる場合
            super.rejectIfEmpty(errors, "fromHealthInfoRegDate", "健康情報作成日");

        } else {
            if (BeanUtil.isNull(form.getFromHealthInfoRegDate())) {
                // 健康情報作成日(開始)が指定されてない場合
                super.rejectIfEmpty(errors, "fromHealthInfoRegDate", "健康情報作成日(開始)");

            } else if (BeanUtil.isNull(form.getToHealthInfoRegDate())) {
                // 健康情報作成日(終了)が指定されてない場合
                super.rejectIfEmpty(errors, "toHealthInfoRegDate", "健康情報作成日(終了)");

            } else {

                LocalDate fromDate = form.getFromHealthInfoRegDate();
                LocalDate toDate = form.getToHealthInfoRegDate();
                if (DateTimeUtil.isAfter(fromDate, toDate, false)) {
                    // 健康情報作成日(終了) < 健康情報作成日(開始) となっている場合
                    errors.rejectValue("toHealthInfoRegDate",
                            DATE_OVER.getOuterErrorCode(), new String[] { "健康情報作成日(終了)" },
                            DATE_OVER.getOuterErrorCode());
                }
            }
        }
    }

}
