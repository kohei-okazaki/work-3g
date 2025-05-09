package jp.co.ha.batch.healthInfoFileRegist;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.healthinfoapp.HealthInfoRegistApi;
import jp.co.ha.business.api.healthinfoapp.request.HealthInfoRegistApiRequest;
import jp.co.ha.business.api.healthinfoapp.response.HealthInfoRegistApiResponse;
import jp.co.ha.business.api.healthinfoapp.type.TestMode;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.component.ApiCommunicationDataComponent;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.impl.AccountSearchServiceImpl;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.FileUtil;
import jp.co.ha.common.validator.BeanValidator;
import jp.co.ha.common.validator.ValidateErrorResult;
import jp.co.ha.common.validator.ValidateErrorResult.ValidateError;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.ApiCommunicationData;

/**
 * 健康情報一括登録を実行するバッチ
 *
 * @version 1.0.0
 */
@StepScope
@Component
public class HealthInfoFileRegistBatch implements Tasklet {

    /** {@linkplain HealthInfoProperties} */
    @Autowired
    private HealthInfoProperties prop;
    /** {@linkplain HealthInfoRegistApi} */
    @Autowired
    private HealthInfoRegistApi api;
    /** {@linkplain AccountSearchServiceImpl} */
    @Autowired
    private AccountSearchService accountSearchService;
    /** {@linkplain ApiCommunicationDataComponent} */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;
    /** {@linkplain SlackApiComponent} */
    @Autowired
    private SlackApiComponent slackApiComponent;
    /** {@linkplain BeanValidator} */
    @Autowired
    private BeanValidator<HealthInfoRegistApiRequest> validator;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {

        if (!FileUtil.isExists(prop.getRegistBatchFilePath())) {
            throw new BusinessException(CommonErrorCode.FILE_OR_DIR_ERROR,
                    "ディレクトリが存在しません.ディレクトリ=" + prop.getRegistBatchFilePath());
        }

        List<HealthInfoRegistApiRequest> requestList = new ArrayList<>();
        JsonReader reader = new JsonReader();

        for (File file : FileUtil.getFileList(prop.getRegistBatchFilePath())) {
            HealthInfoRegistFileDto dto = reader.read(file,
                    HealthInfoRegistFileDto.class);
            List<HealthInfoRegistApiRequest> list = dto.getHealthInfoRequestDataList()
                    .stream()
                    .map(e -> {
                        HealthInfoRegistApiRequest request = new HealthInfoRegistApiRequest();
                        BeanUtil.copy(dto, request);
                        BeanUtil.copy(e, request);
                        request.setTestMode(TestMode.DB_REGIST);
                        request.setTransactionId(
                                apiCommunicationDataComponent.getTransactionId());
                        return request;
                    }).collect(Collectors.toList());

            requestList.addAll(list);
        }

        List<Long> seqHealthInfoIdList = new ArrayList<>();
        for (HealthInfoRegistApiRequest request : requestList) {

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
            Account account = accountSearchService.findById(request.getSeqUserId())
                    .orElseThrow(() -> {
                        return new BusinessException(CommonErrorCode.DB_NO_DATA,
                                "アカウント情報が存在しません.seq_user_id=" + request.getSeqUserId());
                    });

            ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                    .withHeader(ApiConnectInfo.X_API_KEY, account.getApiKey())
                    .withUrlSupplier(() -> prop.getHealthInfoApiUrl()
                            + request.getSeqUserId() + "/healthinfo");

            // API通信情報を登録
            ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                    .create(api.getApiName(), request.getTransactionId(),
                            api.getHttpMethod(), api.getUri(apiConnectInfo, request),
                            request);

            HealthInfoRegistApiResponse response = api.callApi(request, apiConnectInfo);
            seqHealthInfoIdList.add(response.getHealthInfo().getSeqHealthInfoId());

            // API通信情報を更新
            apiCommunicationDataComponent.update(apiCommunicationData, apiConnectInfo,
                    response);
        }

        // Slackを送信する
        sendSlack(seqHealthInfoIdList);

        return RepeatStatus.FINISHED;
    }

    /**
     * 指定した健康情報IDリストを改行区切りのファイルとしてアップロードする
     *
     * @param seqHealthInfoIdList
     *     登録した健康情報IDリスト
     * @throws BaseException
     *     文字コードが不正の場合
     */
    private void sendSlack(List<Long> seqHealthInfoIdList) throws BaseException {

        StringJoiner sj = new StringJoiner("\r\n");
        seqHealthInfoIdList.stream().forEach(e -> sj.add(e.toString()));
        slackApiComponent.send(ContentType.BATCH, sj.toString());
    }

}
