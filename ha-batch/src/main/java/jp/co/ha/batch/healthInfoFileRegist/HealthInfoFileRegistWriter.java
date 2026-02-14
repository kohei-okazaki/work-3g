package jp.co.ha.batch.healthInfoFileRegist;

import static jp.co.ha.common.exception.CommonErrorCode.*;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.healthinfoapp.HealthInfoRegistApi;
import jp.co.ha.business.api.healthinfoapp.request.HealthInfoRegistApiRequest;
import jp.co.ha.business.api.healthinfoapp.response.HealthInfoRegistApiResponse;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.component.ApiLogComponent;
import jp.co.ha.business.component.UserComponent;
import jp.co.ha.business.dto.ApiLogQueuePayload;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.db.entity.User;

/**
 * 健康情報一括登録処理-Writer
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class HealthInfoFileRegistWriter
        implements ItemWriter<HealthInfoRegistApiRequest>, StepExecutionListener {

    /** 健康情報登録API */
    private HealthInfoRegistApi api;
    /** ユーザComponent */
    private UserComponent userComponent;
    /** 健康情報設定ファイル */
    private HealthInfoProperties prop;
    /** API通信ログComponent */
    private ApiLogComponent apiLogComponent;
    /** Slack Component */
    private SlackApiComponent slack;
    /** 健康情報IDリスト */
    private List<Long> seqHealthInfoIdList = new ArrayList<>();

    /**
     * コンストラクタ
     * 
     * @param api
     *     健康情報登録API
     * @param userComponent
     *     ユーザComponent
     * @param prop
     *     健康情報設定ファイル
     * @param apiCommunicationDataComponent
     *     API通信ログComponent
     * @param slack
     *     Slack Component
     */
    public HealthInfoFileRegistWriter(HealthInfoRegistApi api,
            UserComponent userComponent, HealthInfoProperties prop,
            ApiLogComponent apiCommunicationDataComponent,
            SlackApiComponent slack) {
        this.api = api;
        this.userComponent = userComponent;
        this.prop = prop;
        this.apiLogComponent = apiCommunicationDataComponent;
        this.slack = slack;
    }

    @Override
    public void write(Chunk<? extends HealthInfoRegistApiRequest> chunk)
            throws Exception {

        for (HealthInfoRegistApiRequest request : chunk.getItems()) {

            // ユーザ情報.APIキーを取得
            User user = userComponent.findById(request.getSeqUserId())
                    .orElseThrow(() -> new BusinessException(DB_NO_DATA,
                            "ユーザ情報が存在しません. seq_user_id=" + request.getSeqUserId()));

            ApiConnectInfo connectInfo = new ApiConnectInfo()
                    .withHeader(ApiConnectInfo.X_API_KEY, user.getApiKey())
                    .withUrlSupplier(() -> prop.healthInfoApiUrl()
                            + request.getSeqUserId() + "/healthinfo");

            HealthInfoRegistApiResponse response = api.callApi(request, connectInfo);
            if (response != null && response.getHealthInfo() != null) {
                seqHealthInfoIdList.add(response.getHealthInfo().getSeqHealthInfoId());
            }

            ApiLogQueuePayload payload = apiLogComponent
                    .getPayload4AppApi(api, connectInfo, request, response,
                            request.getTransactionId());
            apiLogComponent.registQueue(payload);
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        if (!CollectionUtil.isEmpty(seqHealthInfoIdList)) {
            StringJoiner sj = new StringJoiner(StringUtil.NEW_LINE);
            seqHealthInfoIdList
                    .forEach(seqHealthInfoId -> sj.add(seqHealthInfoId.toString()));
            slack.send(ContentType.BATCH, sj.toString());
        }
        return stepExecution.getExitStatus();
    }

}
