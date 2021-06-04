package jp.co.ha.business.api.healthinfoapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.api.healthinfoapp.request.HealthInfoReferenceApiRequest;
import jp.co.ha.business.api.healthinfoapp.response.HealthInfoReferenceApiResponse;
import jp.co.ha.business.api.healthinfoapp.service.CommonService;
import jp.co.ha.business.api.healthinfoapp.service.HealthInfoReferenceService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.web.form.BaseRestApiResponse;
import jp.co.ha.db.entity.HealthInfo;

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

    @Override
    public void checkRequest(HealthInfoReferenceApiRequest request) throws BaseException {

        // API利用判定
        checkApiUse(request);
    }

    @Override
    public void execute(HealthInfoReferenceApiRequest request,
            HealthInfoReferenceApiResponse response) throws BaseException {

        List<HealthInfo> healthInfoList = healthInfoSearchService
                .findByHealthInfoIdAndSeqUserId(request.getSeqHealthInfoId(),
                        request.getSeqUserId());
        if (CollectionUtil.isEmpty(healthInfoList)) {
            throw new BusinessException(CommonErrorCode.DB_NO_DATA,
                    "該当のレコードが見つかりません seqHealthInfoId:" + request.getSeqHealthInfoId()
                            + ", seqUserId:" + request.getSeqUserId());
        } else if (CollectionUtil.isMultiple(healthInfoList)) {
            throw new BusinessException(CommonErrorCode.MULTIPLE_DATA,
                    "該当のデータが複数存在します seqHealthInfoId:" + request.getSeqHealthInfoId()
                            + ", seqUserId:" + request.getSeqUserId());
        }

        HealthInfo entity = CollectionUtil.getFirst(healthInfoList);
        {
            BaseRestApiResponse.Account account = new BaseRestApiResponse.Account();
            account.setSeqUserId(request.getSeqUserId());
            response.setAccount(account);
        }
        {
            HealthInfoReferenceApiResponse.HealthInfo healthInfo = new HealthInfoReferenceApiResponse.HealthInfo();
            BeanUtil.copy(entity, healthInfo);
            response.setHealthInfo(healthInfo);
        }

    }

}
