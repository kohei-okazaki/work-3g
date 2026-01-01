package jp.co.ha.batch.healthcheck;

import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ha.business.component.ApiLogComponent;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.aws.AwsSesComponent.MailTemplateKey;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * ヘルスチェックAPI-基底Tasklet
 * 
 * @version 1.0.0
 */
public abstract class BaseHealthCheckApiTasklet implements Tasklet {

    /** キー名:トランザクションID */
    public static final String KEY_TRANSACTION_ID = "transactionId";
    /** キー名:レスポンスタイプ */
    public static final String KEY_RESPONSE_TYPE = "response_type";
    /** キー名:API名 */
    public static final String KEY_API_NAME = "apiName";
    /** キー名:メール件名 */
    public static final String KEY_TITLE_TEXT = "titleText";
    /** ヘルスチェックAPIメールテンプレートID */
    protected static final MailTemplateKey TEMPLATE_ID = MailTemplateKey.HEALTHINFO_CHECK_TEMPLATE;

    /** LOG */
    protected final Logger LOG = LoggerFactory.getLogger(BaseHealthCheckApiTasklet.class);
    /** API通信情報Component */
    @Autowired
    protected ApiLogComponent apiCommunicationDataComponent;
    /** 健康情報設定ファイル */
    @Autowired
    protected HealthInfoProperties healthInfoProperties;

    /**
     * トランザクションIDを返す
     * 
     * @param executionContext
     *     ExecutionContext
     * @return トランザクションID
     */
    protected String getTransactionId(ExecutionContext executionContext) {

        String transactionId = executionContext.containsKey(KEY_TRANSACTION_ID)
                ? executionContext.getString(KEY_TRANSACTION_ID)
                : apiCommunicationDataComponent.getTransactionId();

        LOG.debug("transactionId=" + transactionId);

        return transactionId;
    }
}
