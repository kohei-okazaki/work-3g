package jp.co.ha.business.api.healthinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.api.healthinfo.request.HealthInfoReferenceRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoReferenceResponse;
import jp.co.ha.business.api.healthinfo.service.CommonService;
import jp.co.ha.business.api.healthinfo.service.HealthInfoReferenceService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.web.form.BaseRestApiResponse;

/**
 * 健康情報照会サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class HealthInfoReferenceServiceImpl extends CommonService
        implements HealthInfoReferenceService {

    /** 健康情報検索サービス */
    @Autowired
    private HealthInfoSearchService healthInfoSearchService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkRequest(HealthInfoReferenceRequest request) throws BaseException {

        // API利用判定
        checkApiUse(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(HealthInfoReferenceRequest request,
            HealthInfoReferenceResponse response) throws BaseException {

        List<HealthInfo> healthInfoList = healthInfoSearchService
                .findByHealthInfoIdAndUserId(request.getSeqHealthInfoId(),
                        request.getUserId());
        if (CollectionUtil.isEmpty(healthInfoList)) {
            throw new BusinessException(CommonErrorCode.DB_NO_DATA,
                    "該当のレコードが見つかりません seqHealthInfoId:" + request.getSeqHealthInfoId()
                            + ", userId:" + request.getUserId());
        } else if (CollectionUtil.isMultiple(healthInfoList)) {
            throw new BusinessException(CommonErrorCode.MULTIPLE_DATA,
                    "該当のデータが複数存在します seqHealthInfoId:" + request.getSeqHealthInfoId()
                            + ", userId:" + request.getUserId());
        }

        HealthInfo entity = CollectionUtil.getFirst(healthInfoList);
        {
            BaseRestApiResponse.Account account = new BaseRestApiResponse.Account();
            account.setUserId(request.getUserId());
            response.setAccount(account);
        }
        {
            HealthInfoReferenceResponse.HealthInfo healthInfo = new HealthInfoReferenceResponse.HealthInfo();
            BeanUtil.copy(entity, healthInfo);
            response.setHealthInfo(healthInfo);
        }

    }

}
