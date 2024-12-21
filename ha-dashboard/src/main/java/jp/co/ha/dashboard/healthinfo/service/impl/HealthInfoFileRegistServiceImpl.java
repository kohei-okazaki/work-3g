package jp.co.ha.dashboard.healthinfo.service.impl;

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
import jp.co.ha.business.component.ApiCommunicationDataComponent;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.impl.AccountSearchServiceImpl;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.business.io.file.csv.model.HealthInfoCsvUploadModel;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.validator.BeanValidator;
import jp.co.ha.common.validator.ValidateErrorResult;
import jp.co.ha.common.validator.ValidateErrorResult.ValidateError;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoFileRegistService;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.ApiCommunicationData;

/**
 * 健康情報ファイル入力画面サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class HealthInfoFileRegistServiceImpl implements HealthInfoFileRegistService {

    /** {@linkplain ApiCommunicationDataComponent} */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;
    /** {@linkplain AccountSearchServiceImpl} */
    @Autowired
    private AccountSearchService accountSearchService;
    /** {@linkplain HealthInfoRegistApi} */
    @Autowired
    private HealthInfoRegistApi registApi;
    /** {@linkplain HealthInfoProperties} */
    @Autowired
    private HealthInfoProperties prop;
    /** {@linkplain BeanValidator} */
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
                throw new BusinessException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR,
                        ++i + "行目のユーザIDが不正です");
            }
            if (result.hasError()) {
                ValidateError error = result.getFirst();
                throw new BusinessException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR,
                        ++i + "行目のファイルフォーマットが不正です " + error.getMessage());
            }
        }
    }

    @Override
    public ResultType regist(List<HealthInfoCsvUploadModel> modelList, Long seqUserId)
            throws BaseException {

        // アカウント情報.APIキーを設定
        Account account = accountSearchService.findById(seqUserId).get();

        ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                .withHeader(ApiConnectInfo.X_API_KEY, account.getApiKey())
                .withUrlSupplier(
                        () -> prop.getHealthInfoApiUrl() + seqUserId + "/healthinfo");

        ResultType result = ResultType.SUCCESS;
        for (HealthInfoRegistApiRequest request : toRequestList(modelList)) {

            // API通信情報.トランザクションIDを採番
            Long transactionId = apiCommunicationDataComponent.getTransactionId();

            // API通信情報を登録
            ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                    .create(registApi.getApiName(), transactionId,
                            registApi.getHttpMethod(),
                            registApi.getUri(apiConnectInfo, request));
            request.setTransactionId(transactionId);

            HealthInfoRegistApiResponse response = registApi.callApi(request,
                    apiConnectInfo);

            // API通信情報を更新
            apiCommunicationDataComponent.update(apiCommunicationData, apiConnectInfo,
                    response);

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
