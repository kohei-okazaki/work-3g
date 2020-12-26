package jp.co.ha.dashboard.healthinfo.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.api.healthinfo.HealthInfoRegistApi;
import jp.co.ha.business.api.healthinfo.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoRegistResponse;
import jp.co.ha.business.api.healthinfo.type.TestMode;
import jp.co.ha.business.db.crud.create.ApiCommunicationDataCreateService;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.update.ApiCommunicationDataUpdateService;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.business.io.file.csv.model.HealthInfoCsvUploadModel;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.validator.BeanValidator;
import jp.co.ha.common.validator.ValidateErrorResult;
import jp.co.ha.common.validator.ValidateErrorResult.ValidateError;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoFileRegistService;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.web.api.ApiConnectInfo;
import jp.co.ha.web.form.BaseRestApiResponse;
import jp.co.ha.web.form.BaseRestApiResponse.ResultType;

/**
 * 健康情報ファイル入力画面サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class HealthInfoFileRegistServiceImpl implements HealthInfoFileRegistService {

    /** API通信情報作成サービス */
    @Autowired
    private ApiCommunicationDataCreateService apiCommunicationDataCreateService;
    /** API通信情報更新サービス */
    @Autowired
    private ApiCommunicationDataUpdateService apiCommunicationDataUpdateService;
    /** アカウント検索サービス */
    @Autowired
    private AccountSearchService accountSearchService;
    /** 健康情報登録API */
    @Autowired
    private HealthInfoRegistApi registApi;
    /** 健康情報関連プロパティ */
    @Autowired
    private HealthInfoProperties prop;
    /** 妥当性チェック */
    @Autowired
    private BeanValidator<HealthInfoCsvUploadModel> validator;

    @Override
    public void formatCheck(List<HealthInfoCsvUploadModel> modelList, Integer seqUserId)
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
    public ResultType regist(List<HealthInfoCsvUploadModel> modelList, Integer seqUserId)
            throws BaseException {

        // アカウント情報.APIキーを設定
        Account account = accountSearchService.findById(seqUserId).get();

        ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                .withHeader("Api-Key", account.getApiKey())
                .withUrlSupplier(
                        () -> prop.getHealthInfoApiUrl() + seqUserId + "/healthinfo");

        ResultType result = ResultType.SUCCESS;
        for (HealthInfoRegistRequest request : toRequestList(modelList, seqUserId)) {

            // API通信情報を登録
            ApiCommunicationData apiCommunicationData = createApiCommunicationData(
                    registApi.getApiName(), request.getSeqUserId());

            HealthInfoRegistResponse response = registApi.callApi(request,
                    apiConnectInfo);

            // API通信情報を更新
            updateApiCommunicationData(apiCommunicationData, apiConnectInfo, response);

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
     * @param seqUserId
     *     ユーザID
     * @return 健康情報登録APIリクエストリスト
     * @throws BaseException
     *     基底例外
     */
    private List<HealthInfoRegistRequest> toRequestList(
            List<HealthInfoCsvUploadModel> modelList, Integer seqUserId)
            throws BaseException {

        return modelList.stream().map(e -> {
            HealthInfoRegistRequest request = new HealthInfoRegistRequest();

            BeanUtil.copy(e, request, (src, dest) -> {
                HealthInfoCsvUploadModel srcModel = (HealthInfoCsvUploadModel) src;
                HealthInfoRegistRequest destRequest = (HealthInfoRegistRequest) dest;
                destRequest.setHeight(new BigDecimal(srcModel.getHeight()));
                destRequest.setWeight(new BigDecimal(srcModel.getWeight()));
                destRequest.setTestMode(TestMode.DB_REGIST);
            });

            return request;
        }).collect(Collectors.toList());
    }

    /**
     * API通信情報を登録する
     *
     * @param apiName
     *     API名
     * @param seqUserId
     *     ユーザID
     * @return API通信情報
     */
    private ApiCommunicationData createApiCommunicationData(String apiName,
            Integer seqUserId) {

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = new ApiCommunicationData();
        apiCommunicationData.setApiName(apiName);
        apiCommunicationData.setSeqUserId(seqUserId);
        apiCommunicationData.setRequestDate(DateTimeUtil.getSysDate());
        apiCommunicationDataCreateService.create(apiCommunicationData);

        return apiCommunicationData;
    }

    /**
     * API通信情報を更新する
     *
     * @param apiCommunicationData
     *     API通信情報
     * @param connectInfo
     *     API接続情報
     * @param response
     *     APIレスポンス情報
     */
    private void updateApiCommunicationData(ApiCommunicationData apiCommunicationData,
            ApiConnectInfo connectInfo, BaseRestApiResponse response) {

        apiCommunicationData.setHttpStatus(String.valueOf(connectInfo.getHttpStatus()));
        apiCommunicationData.setResult(response.getResultType().getValue());
        String detail = null;
        if (response.getErrorInfo() != null) {
            detail = response.getErrorInfo().getDetail();
        }
        apiCommunicationData.setDetail(detail);
        apiCommunicationData.setResponseDate(DateTimeUtil.getSysDate());
        apiCommunicationDataUpdateService.update(apiCommunicationData);

    }

}
