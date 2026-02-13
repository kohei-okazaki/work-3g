package jp.co.ha.common.aws;

import static jp.co.ha.common.exception.CommonErrorCode.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.CollectionUtil;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
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
    private final ObjectMapper MAPPER;
    /** AWS設定ファイル情報 */
    @Autowired
    private AwsProperties awsProps;
    /** AWS認証情報Component */
    @Autowired
    private AwsAuthComponent auth;

    /**
     * コンストラクタ
     * 
     * @param mapper
     *     ObjectMapper
     */
    public AwsSqsComponent(ObjectMapper mapper) {
        this.MAPPER = mapper;
    }

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
    public void enqueue(String queueName, Object payload) throws BaseException {
        this.enqueue(queueName, payload, null);
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
    public void enqueue(String queueName, Object payload, String groupId)
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
            throw new SystemException(AWS_SQS_ENQUEUE_ERROR, e);
        }
    }

    /**
     * queue情報を取得
     * 
     * @param <T>
     *     queue情報型
     * @param queueName
     *     キュー名
     * @param clazz
     *     レスポンスクラス型
     * @param waitTimeSecond
     *     長輪講
     * @param visibilityTimeout
     *     処理中の再配信防止
     * @param maxSize
     *     最大処理件数
     * @return queue情報のリスト
     * @throws BaseException
     *     queue情報の取得に失敗した場合
     */
    public <T> List<DequeueResult<T>> pollQueueBatchWithHandle(String queueName,
            Class<T> clazz, int waitTimeSecond, int visibilityTimeout, int maxSize)
            throws BaseException {

        try {
            SqsClient sqs = getSqsClient();

            String queueUrl = sqs
                    .getQueueUrl(
                            GetQueueUrlRequest.builder().queueName(queueName).build())
                    .queueUrl();

            ReceiveMessageResponse response = sqs.receiveMessage(r -> r
                    .queueUrl(queueUrl)
                    // 長輪講
                    .waitTimeSeconds(waitTimeSecond)
                    // 処理中の再配信防止
                    .visibilityTimeout(visibilityTimeout)
                    // 最大処理件数
                    .maxNumberOfMessages(maxSize));

            if (CollectionUtil.isEmpty(response.messages())) {
                return Arrays.asList();
            }

            List<DequeueResult<T>> resultList = new ArrayList<>();
            for (Message message : response.messages()) {
                T payload = MAPPER.readValue(message.body(), clazz);
                resultList.add(new DequeueResult<>(payload, message.receiptHandle(),
                        message.messageId()));
            }

            return resultList;

        } catch (Exception e) {
            throw new SystemException(AWS_SQS_POLL_ERROR, e);
        }
    }

    /**
     * queue情報を取得
     * 
     * @param <T>
     *     queue情報型
     * @param queueName
     *     キュー名
     * @param clazz
     *     レスポンスクラス型
     * @param waitTimeSecond
     *     長輪講
     * @param visibilityTimeout
     *     処理中の再配信防止
     * @param maxSize
     *     最大処理件数
     * @return queue情報のリスト
     * @throws BaseException
     *     queue情報の取得に失敗した場合
     */
    public <T> List<T> pollQueueBatch(String queueName,
            Class<T> clazz, int waitTimeSecond, int visibilityTimeout, int maxSize)
            throws BaseException {

        try {
            SqsClient sqs = getSqsClient();

            String queueUrl = sqs
                    .getQueueUrl(
                            GetQueueUrlRequest.builder().queueName(queueName).build())
                    .queueUrl();

            ReceiveMessageResponse response = sqs.receiveMessage(r -> r
                    .queueUrl(queueUrl)
                    // 長輪講
                    .waitTimeSeconds(waitTimeSecond)
                    // 処理中の再配信防止
                    .visibilityTimeout(visibilityTimeout)
                    // 最大処理件数
                    .maxNumberOfMessages(maxSize));

            if (CollectionUtil.isEmpty(response.messages())) {
                return Arrays.asList();
            }

            List<T> resultList = new ArrayList<>();
            for (Message message : response.messages()) {
                T payload = MAPPER.readValue(message.body(), clazz);
                resultList.add(payload);
            }

            return resultList;

        } catch (Exception e) {
            throw new SystemException(AWS_SQS_POLL_ERROR, e);
        }
    }

    /**
     * 単一削除（ReceiptHandle 指定）
     * 
     * @param queueName
     *     キュー名
     * @param receiptHandle
     *     receiptHandle
     * @throws BaseException
     *     queue情報の取得、またはqueueの削除に失敗した場合
     */
    public void ackOne(String queueName, String receiptHandle)
            throws BaseException {
        try {
            SqsClient sqs = getSqsClient();
            String queueUrl = sqs.getQueueUrl(GetQueueUrlRequest.builder()
                    .queueName(queueName).build()).queueUrl();

            sqs.deleteMessage(DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(receiptHandle)
                    .build());
            LOG.debug("delete queue, queueUrl=" + queueUrl);
        } catch (Exception e) {
            throw new SystemException(AWS_SQS_ACK_ERROR, e);
        }
    }

    /**
     * {@linkplain SqsClient}を返す
     * 
     * @return SqsClient
     * @throws BaseException
     *     AWSクライアント接続エラー
     */
    private SqsClient getSqsClient() throws BaseException {

        try {
            // HttpClient にタイムアウトを設定する
            SdkHttpClient httpClient = ApacheHttpClient.builder()
                    .connectionTimeout(
                            Duration.ofMillis(awsProps.sqsConnnectionTimeout()))
                    .socketTimeout(Duration.ofMillis(awsProps.sqsSocketTimeout()))
                    .build();

            return SqsClient.builder()
                    .region(awsProps.region())
                    .credentialsProvider(auth.getProvider())
                    .httpClient(httpClient)
                    .build();
        } catch (Exception e) {
            throw new SystemException(AWS_CLIENT_CONNECT_ERROR, e);
        }
    }

    /**
     * deQueue結果クラス
     * 
     * @version 1.0.0
     * @param payload
     *     Payload
     * @param receiptHandle
     *     receiptHandle
     * @param messageId
     *     messageId
     * @param <T>
     *     キュー型
     */
    public static record DequeueResult<T>(T payload, String receiptHandle,
            String messageId) {
    }
}
