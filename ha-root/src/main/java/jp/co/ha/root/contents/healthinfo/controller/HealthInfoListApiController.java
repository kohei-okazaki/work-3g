package jp.co.ha.root.contents.healthinfo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.db.crud.read.impl.HealthInfoSearchServiceImpl;
import jp.co.ha.business.healthInfo.type.HealthInfoStatus;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.PagingView;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.common.validator.annotation.Decimal;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.healthinfo.request.HealthInfoListApiRequest;
import jp.co.ha.root.contents.healthinfo.response.HealthInfoListApiResponse;

/**
 * 健康情報一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class HealthInfoListApiController extends
        BaseRootApiController<HealthInfoListApiRequest, HealthInfoListApiResponse> {

    /** {@linkplain HealthInfoSearchServiceImpl} */
    @Autowired
    private HealthInfoSearchService healthInfoSearchService;

    /**
     * 健康情報一覧取得
     *
     * @param request
     *     健康情報一覧取得APIリクエスト
     * @param page
     *     取得対象ページ
     * @return 健康情報一覧取得APIレスポンス
     */
    @GetMapping(value = "healthinfo", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HealthInfoListApiResponse> list(
            HealthInfoListApiRequest request,
            @RequestParam(name = "page", required = true, defaultValue = "0") @Decimal(min = 0, message = "page is positive") Integer page) {

        // ページング情報を取得(1ページあたりの表示件数はapplication-${env}.ymlより取得)
        Pageable pageable = PagingViewFactory.getPageable(page,
                applicationProperties.getPage());

        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("SEQ_HEALTH_INFO_ID", SortType.DESC)
                .pageable(pageable)
                .build();

        List<HealthInfoListApiResponse.HealthInfoResponse> healthInfoResponseList = healthInfoSearchService
                .findHealthInfoDetailList(selectOption).stream().map(e -> {
                    HealthInfoListApiResponse.HealthInfoResponse response = new HealthInfoListApiResponse.HealthInfoResponse();
                    BeanUtil.copy(e, response);
                    response.setHealthInfoStatus(
                            getHealthInfoStatus(e.getHealthInfoStatus()));
                    response.setBmiStatus(getBmiStatus(e.getOverWeightStatus()));
                    return response;
                }).collect(Collectors.toList());

        PagingView paging = PagingViewFactory.getPageView(pageable, "healthinfo?page",
                healthInfoSearchService.countByHealthInfoIdAndSeqUserId(null, null));

        HealthInfoListApiResponse response = getSuccessResponse();
        response.setHealthInfoResponseList(healthInfoResponseList);
        response.setPaging(paging);

        return ResponseEntity.ok(response);
    }

    @Override
    protected HealthInfoListApiResponse getResponse() {
        return new HealthInfoListApiResponse();
    }

    /**
     * 健康情報ステータス応答情報を返す
     *
     * @param healthInfoStatus
     *     健康情報ステータス
     * @return 健康情報ステータス応答情報
     */
    private HealthInfoListApiResponse.HealthInfoStatus getHealthInfoStatus(
            String healthInfoStatus) {

        HealthInfoListApiResponse.HealthInfoStatus status = new HealthInfoListApiResponse.HealthInfoStatus();
        status.setStatus(healthInfoStatus);
        switch (HealthInfoStatus.of(healthInfoStatus)) {
        case INCREASE:
            status.setMessage("増えました");
            break;
        case DOWN:
            status.setMessage("減りました");
            break;
        case EVEN:
            status.setMessage("変化ありません");
            break;
        default:
            break;
        }
        return status;
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
