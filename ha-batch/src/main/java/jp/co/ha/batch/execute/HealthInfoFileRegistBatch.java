package jp.co.ha.batch.execute;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.batch.dto.HealthInfoRegistFileDto;
import jp.co.ha.batch.type.BatchResult;
import jp.co.ha.business.api.healthinfo.HealthInfoRegistApi;
import jp.co.ha.business.api.healthinfo.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoRegistResponse;
import jp.co.ha.business.api.healthinfo.type.TestMode;
import jp.co.ha.business.db.crud.create.ApiCommunicationDataCreateService;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.update.ApiCommunicationDataUpdateService;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.FileUtil;
import jp.co.ha.common.validator.BeanValidator;
import jp.co.ha.common.validator.ValidateErrorResult;
import jp.co.ha.common.validator.ValidateErrorResult.ValidateError;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.web.api.ApiConnectInfo;
import jp.co.ha.web.form.BaseRestApiResponse;

/**
 * 健康情報ファイル登録Batch
 *
 * @version 1.0.0
 */
@Component("healthInfoFileRegistBatch")
public class HealthInfoFileRegistBatch extends BaseBatch {

    /** 健康情報設定ファイル */
    @Autowired
    private HealthInfoProperties prop;
    /** 健康情報登録API */
    @Autowired
    private HealthInfoRegistApi api;
    /** アカウント検索サービス */
    @Autowired
    private AccountSearchService accountSearchService;
    /** API通信情報作成サービス */
    @Autowired
    private ApiCommunicationDataCreateService apiCommunicationDataCreateService;
    /** API通信情報更新サービス */
    @Autowired
    private ApiCommunicationDataUpdateService apiCommunicationDataUpdateService;

    /** 妥当性チェック */
    @Autowired
    private BeanValidator<HealthInfoRegistRequest> validator;

    @Override
    public BatchResult execute(CommandLine cmd) throws BaseException {

        List<HealthInfoRegistRequest> requestList = new ArrayList<>();
        JsonReader reader = new JsonReader();

        for (File file : FileUtil.getFileList(prop.getRegistBatchFilePath())) {
            HealthInfoRegistFileDto dto = reader.read(file,
                    HealthInfoRegistFileDto.class);
            List<HealthInfoRegistRequest> list = dto.getHealthInfoRequestDataList()
                    .stream()
                    .map(e -> {
                        HealthInfoRegistRequest request = new HealthInfoRegistRequest();
                        BeanUtil.copy(dto, request);
                        BeanUtil.copy(e, request);
                        request.setTestMode(TestMode.DB_REGIST);
                        return request;
                    }).collect(Collectors.toList());

            requestList.addAll(list);
        }

        for (HealthInfoRegistRequest request : requestList) {

            // 妥当性チェックを行う
            ValidateErrorResult result = validator.validate(request);
            for (ValidateError error : result.getErrorList()) {
                if (!"apiKey".equals(error.getName())) {
                    throw new BusinessException(CommonErrorCode.VALIDATE_ERROR,
                            error.getMessage() + ", " + error.getName() + "="
                                    + error.getValue());
                }
            }

            // アカウント情報.APIキーを設定
            Account account = accountSearchService.findById(request.getSeqUserId()).get();

            ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                    .withHeader("Api-Key", account.getApiKey())
                    .withUrlSupplier(() -> prop.getHealthInfoApiUrl()
                            + request.getSeqUserId() + "/healthinfo");

            // API通信情報を登録
            ApiCommunicationData apiCommunicationData = createApiCommunicationData(
                    "健康情報登録API", request.getSeqUserId());

            HealthInfoRegistResponse response = api.callApi(request, apiConnectInfo);

            // API通信情報を更新
            updateApiCommunicationData(apiCommunicationData, apiConnectInfo, response);

        }

        return BatchResult.SUCCESS;
    }

    @Override
    public Options getOptions() {
        Options options = new Options();
        return options;
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
