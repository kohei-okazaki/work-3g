package jp.co.ha.business.api.slack;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.aws.AwsS3Component.AwsS3Key;
import jp.co.ha.business.api.slack.SlackConnectionData.Connection;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.BaseEnum;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * SlackAPIのラッパークラス
 *
 * @version 1.0.0
 */
@Component
public class SlackApiComponent {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(SlackApiComponent.class);
    /** 接続情報の列挙 */
    private static final AwsS3Key KEY = AwsS3Key.SLACK_CONNECTION_DATA;
    /** Slack URL(共通) */
    private static final String BASE_SLACK_URL = "https://slack.com/api";
    /** Slack URL(メッセージ用) */
    private static final String MESSAGE_URL = BASE_SLACK_URL + "/chat.postMessage";
    /** Slack URL(ファイルアップロード用) */
    private static final String FILE_UPLOAD_URL = BASE_SLACK_URL + "/files.upload";
    /** S3 Component */
    @Autowired
    private AwsS3Component s3;

    /**
     * Slackにメッセージを送信する
     *
     * @param contentType
     *     コンテンツタイプ
     * @param message
     *     メッセージ
     * @throws BaseException
     *     Slackセッションへの接続、またはSlack接続情報JSONの読み込みに失敗した場合
     */
    public void send(ContentType contentType, String message) throws BaseException {

        try {

            SlackConnectionData data = getSlackConnectionData();
            Connection conn = getConnectionByContentType(data, contentType);

            // Slack APIに渡す JSON を作成
            String json = new ObjectMapper().writeValueAsString(Map.of(
                    "channel", getChannelId(conn),
                    "text", message));

            Request request = new Request.Builder()
                    .url(MESSAGE_URL)
                    .addHeader("Authorization", "Bearer " + data.getToken())
                    .addHeader("Content-type", "application/json; charset=utf-8")
                    .post(RequestBody.create(json, MediaType.get("application/json")))
                    .build();

            try (Response response = new OkHttpClient().newCall(request).execute()) {
                String responseBody = response.body().string();
                LOG.debug("Slack API response: " + responseBody);

                if (!response.isSuccessful()) {
                    LOG.warn("Slack送信に失敗しました。code=" + response.code());
                }
            }
        } catch (IOException e) {
            throw new BusinessException(e);
        }

    }

    /**
     * Slackにファイルを送信する
     *
     * @param contentType
     *     コンテンツタイプ
     * @param fileData
     *     ファイルデータ
     * @param fileName
     *     ファイル名
     * @param title
     *     件名
     * @param initialComment
     *     投稿コメント
     * @throws BaseException
     *     Slackセッションへの接続、またはSlack接続情報JSONの読み込みに失敗した場合
     */
    public void sendFile(ContentType contentType, byte[] fileData, String fileName,
            String title, String initialComment) throws BaseException {

        SlackConnectionData data = getSlackConnectionData();
        Connection conn = getConnectionByContentType(data, contentType);

        RequestBody fileBody = RequestBody.create(fileData,
                MediaType.parse("application/octet-stream"));

        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("channels", getChannelId(conn))
                .addFormDataPart("filename", fileName)
                .addFormDataPart("title", title)
                .addFormDataPart("initial_comment", initialComment)
                .addFormDataPart("file", title, fileBody)
                .build();

        Request request = new Request.Builder()
                .url(FILE_UPLOAD_URL)
                .header("Authorization", "Bearer " + data.getToken())
                .post(requestBody)
                .build();

        try (Response response = new OkHttpClient().newCall(request).execute()) {
            String responseBody = response.body().string();
            LOG.debug("Slack API response: " + responseBody);

            JsonNode root = new ObjectMapper().readTree(responseBody);
            if (!root.path("ok").asBoolean()) {
                LOG.warn("Slack file upload failed: " + root.path("error").asText());
            }
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * Slack接続情報を返す
     * 
     * @return Slack接続情報
     * @throws BaseException
     */
    private SlackConnectionData getSlackConnectionData() throws BaseException {
        return new JsonReader()
                .read(s3.getS3ObjectByKey(KEY), SlackConnectionData.class);
    }

    /**
     * 指定したコンテンツタイプから対応するSlack接続情報を取得する
     * 
     * @param data
     *     SlackAPIの接続情報
     * @param contentType
     *     コンテンツタイプ
     * @return Slack接続情報
     * @throws BaseException
     *     JSONの読み込みに失敗した場合
     */
    private Connection getConnectionByContentType(SlackConnectionData data,
            ContentType contentType) throws BaseException {

        return data.getConnectionList().stream()
                .filter(e -> e.getContentType() == contentType)
                .findFirst()
                .orElseThrow(() -> new BusinessException(
                        BusinessErrorCode.AWS_S3_DOWNLOAD_ERROR,
                        "jsonに対象のコンテンツタイプが存在しません. contentType="
                                + contentType.getValue()));
    }

    /**
     * チャンネルIDを返す
     *
     * @param conn
     *     {@linkplain Connection}
     * @return チャンネル名
     */
    private String getChannelId(Connection conn) {
        return conn.getChannelId();
    }

    /**
     * コンテンツタイプの列挙体
     * <ul>
     * <li>DASHBOARD：健康管理ダッシュボード</li>
     * <li>API：健康管理API</li>
     * <li>BATCH：健康管理バッチ</li>
     * <li>ROOT：管理者用サイト</li>
     * </ul>
     *
     * @version 1.0.0
     */
    public static enum ContentType implements BaseEnum {

        /** DASHBOARD：健康管理ダッシュボード */
        DASHBOARD("dashboard"),
        /** API：健康管理API */
        API("api"),
        /** BATCH：健康管理バッチ */
        BATCH("batch"),
        /** ROOT：管理者用サイト */
        ROOT("root");

        /** 値 */
        private String value;

        /**
         * コンストラクタ
         *
         * @param value
         *     値
         */
        private ContentType(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        /**
         * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
         * @param value
         *     値
         * @return ContentType
         */
        public static ContentType of(String value) {
            return BaseEnum.of(ContentType.class, value);
        }
    }

    /**
     * {@linkplain ContentType}のJSONデシリアライズクラス
     *
     * @version 1.0.0
     */
    public static class ContentTypeDeserializer extends JsonDeserializer<ContentType> {

        @Override
        public ContentType deserialize(JsonParser parser, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            return ContentType.of(parser.getValueAsString("content_type"));
        }

    }
}
