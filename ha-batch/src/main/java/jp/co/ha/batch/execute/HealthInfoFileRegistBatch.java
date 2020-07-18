package jp.co.ha.batch.execute;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.cli.Options;
import org.springframework.stereotype.Component;

import jp.co.ha.batch.dto.HealthInfoRegistData;
import jp.co.ha.batch.type.BatchResult;
import jp.co.ha.business.api.healthinfo.HealthInfoRegistApi;
import jp.co.ha.business.api.healthinfo.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.healthinfo.type.TestMode;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.impl.AccountSearchServiceImpl;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.system.BatchBeanLoader;
import jp.co.ha.common.system.SystemMemory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.FileUtil;
import jp.co.ha.common.validator.BeanValidator;
import jp.co.ha.common.validator.ValidateErrorResult;
import jp.co.ha.common.validator.ValidateErrorResult.ValidateError;
import jp.co.ha.db.entity.Account;
import jp.co.ha.web.api.ApiConnectInfo;

/**
 * 健康情報ファイル登録Batch
 *
 * @version 1.0.0
 */
@Component("healthInfoFileRegistBatch")
public class HealthInfoFileRegistBatch extends BaseBatch {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(HealthInfoFileRegistBatch.class);
    /** 健康情報設定ファイル */
    private HealthInfoProperties prop = BatchBeanLoader
            .getBean(HealthInfoProperties.class);
    /** 健康情報登録API */
    private HealthInfoRegistApi api = BatchBeanLoader.getBean(HealthInfoRegistApi.class);
    /** アカウント検索サービス */
    private AccountSearchService accountSearchService = BatchBeanLoader
            .getBean(AccountSearchServiceImpl.class);
    /** 妥当性チェック */
    @SuppressWarnings("unchecked")
    private BeanValidator<HealthInfoRegistRequest> validator = BatchBeanLoader
            .getBean(BeanValidator.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public BatchResult execute() throws BaseException {

        LOG.info("健康情報ファイル登録Batch START メモリ使用量"
                + SystemMemory.getInstance().getMemoryUsage());
        List<HealthInfoRegistRequest> requestList = new ArrayList<>();
        JsonReader reader = new JsonReader();

        for (File file : FileUtil.getFileList(prop.getRegistBatchFilePath())) {
            HealthInfoRegistData dto = reader.read(file, HealthInfoRegistData.class);
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
            if (result.hasError()) {
                ValidateError error = result.getFirst();
                throw new BusinessException(CommonErrorCode.VALIDATE_ERROR,
                        error.getMessage() + " " + error.getName() + "="
                                + error.getValue());
            }

            ApiConnectInfo apiConnectInfo = new ApiConnectInfo();
            // アカウント情報.APIキーを設定
            Account account = accountSearchService.findById(request.getUserId()).get();
            apiConnectInfo.addHeader("Api-Key", account.getApiKey());
            apiConnectInfo.setUrlSupplier(() -> prop.getHealthInfoApiUrl()
                    + request.getUserId() + "/healthinfo");
            api.callApi(request, apiConnectInfo);
        }

        LOG.info("健康情報ファイル登録Batch END メモリ使用量"
                + SystemMemory.getInstance().getMemoryUsage());
        return BatchResult.SUCCESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Options getOptions(List<String> optionList) {
        Options options = new Options();
        return options;
    }

}
