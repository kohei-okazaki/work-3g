package jp.co.ha.root.contents.healthinfo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.healthInfo.type.HealthInfoStatus;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.healthinfo.request.HealthInfoListApiRequest;
import jp.co.ha.root.contents.healthinfo.response.HealthInfoListApiResponse;
import jp.co.ha.root.type.RootApiResult;

/**
 * 健康情報一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class HealthInfoListApiController extends
        BaseRootApiController<HealthInfoListApiRequest, HealthInfoListApiResponse> {

    /** 健康情報検索サービス */
    @Autowired
    private HealthInfoSearchService healthInfoSearchService;

    /**
     * 健康情報一覧取得
     *
     * @param request
     *     健康情報一覧取得APIリクエスト
     * @return 健康情報一覧取得APIレスポンス
     */
    @GetMapping(value = "healthinfo", produces = { MediaType.APPLICATION_JSON_VALUE })
    public HealthInfoListApiResponse list(HealthInfoListApiRequest request) {

        List<HealthInfoListApiResponse.HealthInfoResponse> healthInfoResponseList = healthInfoSearchService
                .findHealthInfoDetailList().stream().map(e -> {
                    HealthInfoListApiResponse.HealthInfoResponse response = new HealthInfoListApiResponse.HealthInfoResponse();
                    BeanUtil.copy(e, response);
                    response.setHealthInfoStatus(
                            getHealthInfoStatus(e.getHealthInfoStatus()));
                    response.setBmiStatus(getBmiStatus(e.getSeqBmiRangeMtId(),
                            e.getOverWeightStatus()));
                    return response;
                }).collect(Collectors.toList());

        HealthInfoListApiResponse response = new HealthInfoListApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);
        response.setHealthInfoResponseList(healthInfoResponseList);

        return response;
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
     * @param seqBmiRangeMtId
     *     BMI範囲マスタID
     * @param overWeightStatus
     *     肥満度ステータス
     * @return 肥満度ステータス応答情報
     */
    private HealthInfoListApiResponse.BmiStatus getBmiStatus(Integer seqBmiRangeMtId,
            String overWeightStatus) {

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
