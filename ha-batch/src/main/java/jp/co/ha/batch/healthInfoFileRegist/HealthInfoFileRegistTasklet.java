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
import jp.co.ha.business.component.UserComponent;
import jp.co.ha.business.dto.ApiCommunicationDataQueuePayload;
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
import jp.co.ha.db.entity.User;

/**
 * 健康情報一括登録-Tasklet
 *
 * @version 1.0.0
 */
@StepScope
@Component
public class HealthInfoFileRegistTasklet implements Tasklet {

    /** 健康情報設定ファイル */
    @Autowired
    private HealthInfoProperties prop;
    /** 健康情報登録API */
    @Autowired
    private HealthInfoRegistApi api;
    /** ユーザComponent */
    @Autowired
    private UserComponent userComponent;
    /** API通信情報Component */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;
    /** Slack Component */
    @Autowired
    private SlackApiComponent slack;
    /** 妥当性チェックValidator */
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

            // ユーザ情報.APIキーを設定
            User user = userComponent.findById(request.getSeqUserId())
                    .orElseThrow(() -> {
                        return new BusinessException(CommonErrorCode.DB_NO_DATA,
                                "ユーザ情報が存在しません. seq_user_id=" + request.getSeqUserId());
                    });

            ApiConnectInfo connectInfo = new ApiConnectInfo()
                    .withHeader(ApiConnectInfo.X_API_KEY, user.getApiKey())
                    .withUrlSupplier(() -> prop.getHealthInfoApiUrl()
                            + request.getSeqUserId() + "/healthinfo");

            HealthInfoRegistApiResponse response = api.callApi(request, connectInfo);
            seqHealthInfoIdList.add(response.getHealthInfo().getSeqHealthInfoId());

            ApiCommunicationDataQueuePayload payload = apiCommunicationDataComponent
                    .getPayload4AppApi(api, connectInfo, request, response,
                            request.getTransactionId());
            apiCommunicationDataComponent.registQueue(payload);
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
        slack.send(ContentType.BATCH, sj.toString());
    }

}
