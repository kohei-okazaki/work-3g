package jp.co.ha.root.contents.healthinfo.service;

import static jp.co.ha.common.db.SelectOption.SortType.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.PagingView;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.root.contents.healthinfo.response.HealthInfoListApiResponse;
import jp.co.ha.root.contents.healthinfo.response.HealthInfoListApiResponse.HealthInfoResponse;

/**
 * 健康情報サービス実装クラス
 * 
 * @version 1.0.0
 */
@Service
public class HealthInfoServiceImpl implements HealthInfoService {

    /** 健康情報検索サービス */
    @Autowired
    private HealthInfoSearchService healthInfoSearchService;

    @Override
    public List<HealthInfoResponse> getHealthInfoList(Pageable pageable) {

        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("SEQ_HEALTH_INFO_ID", DESC)
                .pageable(pageable)
                .build();

        List<HealthInfoListApiResponse.HealthInfoResponse> list = healthInfoSearchService
                .findHealthInfoDetailList(selectOption).stream().map(e -> {
                    HealthInfoListApiResponse.HealthInfoResponse response = new HealthInfoListApiResponse.HealthInfoResponse();
                    BeanUtil.copy(e, response);
                    response.setBmiStatus(getBmiStatus(e.getOverWeightStatus()));
                    return response;
                }).collect(Collectors.toList());

        return list;
    }

    @Override
    public PagingView getPagingView(Pageable pageable) {
        return PagingViewFactory.getPageView(pageable, "healthinfo?page",
                healthInfoSearchService.countByHealthInfoIdAndSeqUserId(null, null));
    }

    /**
     * 肥満度ステータス応答情報を返す
     *
     * @param overWeightStatus
     *     肥満度ステータス
     * @return 肥満度ステータス応答情報
     */
    private HealthInfoListApiResponse.BmiStatus getBmiStatus(String overWeightStatus) {

        HealthInfoListApiResponse.BmiStatus status = new HealthInfoListApiResponse.BmiStatus();
        status.setStatus(overWeightStatus);
        if ("10".equals(overWeightStatus)) {
            status.setMessage("低体重");
        } else if ("11".equals(overWeightStatus)) {
            status.setMessage("普通体重");
        } else if ("12".equals(overWeightStatus)) {
            status.setMessage("肥満(1)");
        } else if ("13".equals(overWeightStatus)) {
            status.setMessage("肥満(2)");
        } else if ("14".equals(overWeightStatus)) {
            status.setMessage("肥満(3)");
        } else if ("15".equals(overWeightStatus)) {
            status.setMessage("肥満(4)");
        }
        return status;
    }

}
