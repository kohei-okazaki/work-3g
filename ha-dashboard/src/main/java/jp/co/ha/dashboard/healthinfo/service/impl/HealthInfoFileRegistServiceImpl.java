package jp.co.ha.dashboard.healthinfo.service.impl;

import static jp.co.ha.business.exception.DashboardErrorCode.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.api.healthinfoapp.HealthInfoRegistApi;
import jp.co.ha.business.api.healthinfoapp.request.HealthInfoRegistApiRequest;
import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse.ResultType;
import jp.co.ha.business.api.healthinfoapp.response.HealthInfoRegistApiResponse;
import jp.co.ha.business.api.healthinfoapp.type.TestMode;
import jp.co.ha.business.component.ApiLogComponent;
import jp.co.ha.business.component.UserComponent;
import jp.co.ha.business.dto.ApiLogQueuePayload;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.io.file.csv.model.HealthInfoCsvUploadModel;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.validator.BeanValidator;
import jp.co.ha.common.validator.ValidateErrorResult;
import jp.co.ha.common.validator.ValidateErrorResult.ValidateError;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoFileRegistService;
import jp.co.ha.db.entity.User;

/**
 * 健康情報ファイル入力画面サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class HealthInfoFileRegistServiceImpl implements HealthInfoFileRegistService {

    /** API通信ログComponent */
    @Autowired
    private ApiLogComponent apiLogComponent;
    /** ユーザComponent */
    @Autowired
    private UserComponent userComponent;
    /** 健康情報登録API */
    @Autowired
    private HealthInfoRegistApi registApi;
    /** 健康情報設定ファイル */
    @Autowired
    private HealthInfoProperties prop;
    /** 妥当性チェックValidator */
    @Autowired
    private BeanValidator<HealthInfoCsvUploadModel> validator;

    @Override
    public void formatCheck(List<HealthInfoCsvUploadModel> modelList, Long seqUserId)
            throws BaseException {

        for (int i = 0; i < modelList.size(); i++) {
            HealthInfoCsvUploadModel model = modelList.get(i);
            ValidateErrorResult result = validator.validate(model);

            // 相関チェック
            if (!model.getSeqUserId().equals(String.valueOf(seqUserId))) {
                throw new BusinessException(ILLEGAL_ACCESS_ERROR, ++i + "行目のユーザIDが不正です");
            }
            if (result.hasError()) {
                ValidateError error = result.getFirst();
                throw new BusinessException(ILLEGAL_ACCESS_ERROR,
                        ++i + "行目のファイルフォーマットが不正です " + error.message());
            }
        }
    }

    @Override
    public ResultType regist(List<HealthInfoCsvUploadModel> modelList, Long seqUserId)
            throws BaseException {

        // ユーザ情報.APIキーを設定
        User user = userComponent.findById(seqUserId).get();

        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withHeader(ApiConnectInfo.X_API_KEY, user.getApiKey())
                .withUrlSupplier(
                        () -> prop.healthInfoApiUrl() + seqUserId + "/healthinfo");

        ResultType result = ResultType.SUCCESS;
        for (HealthInfoRegistApiRequest request : toRequestList(modelList)) {

            // トランザクションIDを採番
            String transactionId = apiLogComponent.transactionId();

            request.setTransactionId(transactionId);

            HealthInfoRegistApiResponse response = registApi.callApi(request,
                    connectInfo);

            ApiLogQueuePayload payload = apiLogComponent
                    .getPayload4AppApi(registApi, connectInfo, request, response,
                            transactionId);
            apiLogComponent.registQueue(payload);

            if (ResultType.FAILURE == response.getResultType()) {
                result = response.getResultType();
            }
        }

        return result;
    }

    /**
     * 健康情報CSVアップロードモデルリストから健康情報登録APIリクエストのリストに変換する
     *
     * @param modelList
     *     健康情報CSVアップロードモデルリスト
     * @return 健康情報登録APIリクエストリスト
     */
    private List<HealthInfoRegistApiRequest> toRequestList(
            List<HealthInfoCsvUploadModel> modelList) {

        return modelList.stream().map(e -> {
            HealthInfoRegistApiRequest request = new HealthInfoRegistApiRequest();

            BeanUtil.copy(e, request, (src, dest) -> {
                HealthInfoCsvUploadModel srcModel = (HealthInfoCsvUploadModel) src;
                HealthInfoRegistApiRequest destRequest = (HealthInfoRegistApiRequest) dest;
                destRequest.setHeight(new BigDecimal(srcModel.getHeight()));
                destRequest.setWeight(new BigDecimal(srcModel.getWeight()));
                destRequest.setTestMode(TestMode.DB_REGIST);
            });

            return request;
        }).collect(Collectors.toList());
    }

}
