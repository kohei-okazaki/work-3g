package jp.co.ha.business.api.aws;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

/**
 * AWS-Simple Queue ServiceのComponent
 *
 * @version 1.0.0
 */
@Component
public class AwsSqsComponent {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(AwsSqsComponent.class);
    /** ObjectMapper */
    private static final ObjectMapper MAPPER = new ObjectMapper();
    /** AWS設定ファイル情報 */
    @Autowired
    private AwsProperties awsProps;
    /** AWS認証情報Component */
    @Autowired
    private AwsAuthComponent auth;

    /**
     * queue登録
     * 
     * @param queueName
     *     キュー名
     * @param payload
     *     Queue情報
     * @throws BaseException
     *     Queueの登録に失敗した場合
     */
    public void send(String queueName, Object payload) throws BaseException {
        this.send(queueName, payload, null);
    }

    /**
     * queue登録<br>
     * <b>※FIFOのキューの場合、こちらのメソッドを呼び出すこと</b>
     * 
     * @param queueName
     *     キュー名
     * @param payload
     *     Queue情報
     * @param groupId
     *     グループID
     * @throws BaseException
     *     Queueの登録に失敗した場合
     */
    public void send(String queueName, Object payload, String groupId)
            throws BaseException {
        try {
            String strPayload = MAPPER.writeValueAsString(payload);
            SqsClient sqs = getSqsClient();
            String queueUrl = sqs
                    .getQueueUrl(
                            GetQueueUrlRequest.builder()
                                    .queueName(queueName).build())
                    .queueUrl();
            LOG.debug("queueUrl=" + queueUrl + ", queuename=" + queueName);

            SendMessageRequest.Builder builder = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(strPayload);
            if (Objects.nonNull(groupId)) {
                // 順序担保時のグループ用
                builder.messageGroupId(groupId)
                        // 一意にする
                        .messageDeduplicationId(UUID.randomUUID().toString());
            }
            // キュー送信
            sqs.sendMessage(builder.build());
        } catch (Exception e) {
            throw new BusinessException(
                    BusinessErrorCode.AWS_CLIENT_CONNECT_ERROR,
                    "キュー情報の登録に失敗しました", e);
        }
    }

    /**
     * {@linkplain SqsClient}を返す
     * 
     * @return SqsClient
     */
    private SqsClient getSqsClient() {

        // HttpClient にタイムアウトを設定する
        SdkHttpClient httpClient = ApacheHttpClient.builder()
                .connectionTimeout(Duration.ofMillis(awsProps.getSqsConnnectionTimeout()))
                .socketTimeout(Duration.ofMillis(awsProps.getSqsSocketTimeout()))
                .build();

        return SqsClient.builder()
                .region(awsProps.getRegion())
                .credentialsProvider(auth.getAWSCredentialsProvider())
                .httpClient(httpClient)
                .build();
    }
}
