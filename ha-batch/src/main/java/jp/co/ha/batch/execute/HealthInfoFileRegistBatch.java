package jp.co.ha.batch.execute;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
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
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.component.ApiCommunicationDataComponent;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemRuntimeException;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.FileUtil;
import jp.co.ha.common.validator.BeanValidator;
import jp.co.ha.common.validator.ValidateErrorResult;
import jp.co.ha.common.validator.ValidateErrorResult.ValidateError;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.web.api.ApiConnectInfo;

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
    /** API通信情報Component */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;
    /** SlackComponent */
    @Autowired
    private SlackApiComponent slackApiComponent;
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

        List<Integer> seqHealthInfoIdList = new ArrayList<>();
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
                    .withHeader(ApiConnectInfo.X_API_KEY, account.getApiKey())
                    .withUrlSupplier(() -> prop.getHealthInfoApiUrl()
                            + request.getSeqUserId() + "/healthinfo");

            // API通信情報を登録
            ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                    .create(api.getApiName(), request.getSeqUserId());

            HealthInfoRegistResponse response = api.callApi(request, apiConnectInfo);
            seqHealthInfoIdList.add(response.getHealthInfo().getSeqHealthInfoId());

            // API通信情報を更新
            apiCommunicationDataComponent.update(apiCommunicationData, apiConnectInfo,
                    response);
        }

        // Slackを送信する
        sendSlack(seqHealthInfoIdList);

        return BatchResult.SUCCESS;
    }

    @Override
    public Options getOptions() {
        Options options = new Options();
        return options;
    }

    /**
     * 指定した健康情報IDリストを改行区切りのファイルとしてアップロードする
     *
     * @param seqHealthInfoIdList
     *     登録した健康情報IDリスト
     * @throws BaseException
     */
    private void sendSlack(List<Integer> seqHealthInfoIdList) throws BaseException {

        // Slackのbatch_${env}チャンネルにメッセージを投稿
        StringJoiner sj = new StringJoiner("\r\n");
        // 健康情報一括登録完了.\r\n健康情報IDリスト\r\n
        seqHealthInfoIdList.stream().forEach(e -> sj.add(e.toString()));
        try {
            slackApiComponent.sendFile(ContentType.BATCH,
                    sj.toString().getBytes(Charset.UTF_8.getValue()), "fileName",
                    "健康情報IDリスト", "健康情報一括登録完了.");
        } catch (UnsupportedEncodingException e) {
            // 文字コードが異なる場合に発生。但し、UTF-8を固定しているため発生しない
            throw new SystemRuntimeException(e);
        }
    }

}
