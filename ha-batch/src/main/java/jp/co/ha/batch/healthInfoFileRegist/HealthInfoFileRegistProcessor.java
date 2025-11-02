package jp.co.ha.batch.healthInfoFileRegist;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.healthinfoapp.request.HealthInfoRegistApiRequest;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.validator.BeanValidator;
import jp.co.ha.common.validator.ValidateErrorResult;
import jp.co.ha.common.validator.ValidateErrorResult.ValidateError;

/**
 * 健康情報一括登録処理-Reader
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class HealthInfoFileRegistProcessor
        implements ItemProcessor<HealthInfoRegistApiRequest, HealthInfoRegistApiRequest> {

    /** 妥当性チェック */
    private BeanValidator<HealthInfoRegistApiRequest> validator;

    /**
     * コンストラクタ
     * 
     * @param validator
     *     妥当性チェック
     */
    public HealthInfoFileRegistProcessor(
            BeanValidator<HealthInfoRegistApiRequest> validator) {
        this.validator = validator;
    }

    @Override
    public HealthInfoRegistApiRequest process(HealthInfoRegistApiRequest item)
            throws Exception {

        ValidateErrorResult result = validator.validate(item);

        for (ValidateError error : result.getErrorList()) {

            if ("apiKey".equals(error.getName())) {
                // APIキーの設定はAPI通信時に行うため、ここでスルーして次ループへ
                continue;
            }

            // 妥当性チェックエラーの場合
            throw new BusinessException(CommonErrorCode.VALIDATE_ERROR,
                    error.getMessage() + ", " + error.getName() + "="
                            + error.getValue());
        }

        return item;
    }

}
