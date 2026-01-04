package jp.co.ha.business.api.slack;

import static jp.co.ha.common.aws.AwsSystemsManagerComponent.*;
import static jp.co.ha.common.exception.CommonErrorCode.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.ha.business.api.slack.SlackConnectionData.Connection;
import jp.co.ha.common.aws.AwsSystemsManagerComponent;
import jp.co.ha.common.exception.SystemRuntimeException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.BaseEnum;
import okhttp3.MediaType;
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
    /** Slack URL(共通) */
    private static final String BASE_SLACK_URL = "https://slack.com/api";
    /** Slack URL(メッセージ用) */
    private static final String MESSAGE_URL = BASE_SLACK_URL + "/chat.postMessage";
    /** Slack URL(外部アップロード) */
    private static final String FILES_GET_UPLOAD_URL_EXTERNAL = BASE_SLACK_URL
            + "/files.getUploadURLExternal";
    /** Slack URL(外部アップロード) */
    private static final String FILES_COMPLETE_UPLOAD_EXTERNAL = BASE_SLACK_URL
            + "/files.completeUploadExternal";

    /** S3 Component */
    @Autowired
    private AwsSystemsManagerComponent ssm;

    /**
     * Slackにメッセージを送信する
     *
     * @param contentType
     *     コンテンツタイプ
     * @param message
     *     メッセージ
     */
    public void send(ContentType contentType, String message) {

        try {

            SlackConnectionData data = getSlackConnectionData();
            Connection conn = getConnectionByContentType(data, contentType);

            // Slack APIに渡す JSON を作成
            String json = new ObjectMapper().writeValueAsString(Map.of(
                    "channel", conn.getChannelId(),
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
            throw new SystemRuntimeException(e);
        }

    }

    /**
     * 指定した例外のスタックトレースをslackにsnipet形式で送信する
     * 
     * @param contentType
     *     コンテンツタイプ
     * @param t
     *     例外クラス
     * @see #sendFile(ContentType, byte[], String, String)
     */
    public void sendError(ContentType contentType, Throwable t) {

        // スタックトレースを文字列化
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();

        // バイト配列に変換
        byte[] fileData = stackTrace.getBytes(StandardCharsets.UTF_8);

        // ファイル名とタイトル
        String fileName = "stacktrace";
        String title = fileName;

        sendFile(contentType, fileData, fileName, title);
    }

    /**
     * Slackにファイルを送信する
     * 
     * @param contentType
     *     コンテンツタイプ
     * @param file
     *     ファイル
     * @param title
     *     件名
     * @see #sendFile(ContentType, byte[], String, String)
     */
    public void sendFile(ContentType contentType, File file, String title) {
        try {
            sendFile(contentType, Files.readAllBytes(file.toPath()), file.getName(),
                    title);
        } catch (IOException e) {
            throw new SystemRuntimeException(e);
        }
    }

    /**
     * Slackにファイルを送信する
     * 
     * @param contentType
     *     コンテンツタイプ
     * @param file
     *     ファイル
     * @param title
     *     件名
     * @param initialComment
     *     投稿コメント
     * @see #sendFile(ContentType, byte[], String, String, String)
     */
    public void sendFile(ContentType contentType, File file, String title,
            String initialComment) {
        try {
            sendFile(contentType, Files.readAllBytes(file.toPath()), file.getName(),
                    title, initialComment);
        } catch (IOException e) {
            throw new SystemRuntimeException(e);
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
     * @see #sendFile(ContentType, byte[], String, String, String)
     */
    public void sendFile(ContentType contentType, byte[] fileData, String fileName,
            String title) {
        sendFile(contentType, fileData, fileName, title, "");
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
     */
    public void sendFile(ContentType contentType, byte[] fileData, String fileName,
            String title, String initialComment) {

        try {
            SlackConnectionData data = getSlackConnectionData();
            Connection conn = getConnectionByContentType(data, contentType);
            OkHttpClient client = new OkHttpClient();
            ObjectMapper mapper = new ObjectMapper();

            // 1) 一時アップロードURLを取得
            RequestBody urlReq = new okhttp3.FormBody.Builder()
                    .add("filename", fileName)
                    .add("length", String.valueOf(fileData.length))
                    .build();

            Request getUrl = new Request.Builder()
                    .url(FILES_GET_UPLOAD_URL_EXTERNAL)
                    .addHeader("Authorization", "Bearer " + data.getToken())
                    .post(urlReq)
                    .build();

            String uploadUrl;
            String fileId;
            try (Response res = client.newCall(getUrl).execute()) {
                String body = res.body().string();
                LOG.debug("getUploadURLExternal: " + body);
                JsonNode json = mapper.readTree(body);
                if (!json.path("ok").asBoolean()) {
                    throw new SystemRuntimeException(FILE_UPLOAD_ERROR,
                            "getUploadURLExternal failed: "
                                    + json.path("error").asText());
                }
                uploadUrl = json.path("upload_url").asText();
                fileId = json.path("file_id").asText();
            }

            // 2) 取得したURLにファイル本体をPOST（トークン不要）
            RequestBody fileBody = RequestBody.create(fileData,
                    MediaType.parse("application/octet-stream"));
            Request putFile = new Request.Builder()
                    .url(uploadUrl)
                    .post(fileBody)
                    .addHeader("Content-Length", String.valueOf(fileData.length))
                    .build();

            try (Response res = client.newCall(putFile).execute()) {
                if (!res.isSuccessful()) {
                    throw new SystemRuntimeException(FILE_UPLOAD_ERROR,
                            "upload to external URL failed: HTTP " + res.code());
                }
            }

            // 3) アップロード完了をSlackに通知（ここで共有先を指定）
            String completeJson = mapper.writeValueAsString(Map.of(
                    "channel_id", conn.getChannelId(),
                    "initial_comment", initialComment, // 任意
                    "files", List.of(Map.of(
                            "id", fileId,
                            "title", title))));

            Request completeReq = new Request.Builder()
                    .url(FILES_COMPLETE_UPLOAD_EXTERNAL)
                    .addHeader("Authorization", "Bearer " + data.getToken())
                    .addHeader("Content-type", "application/json; charset=utf-8")
                    .post(RequestBody.create(completeJson,
                            MediaType.get("application/json")))
                    .build();

            try (Response res = client.newCall(completeReq).execute()) {
                String body = res.body().string();
                LOG.debug("completeUploadExternal: " + body);
                JsonNode json = mapper.readTree(body);
                if (!json.path("ok").asBoolean()) {
                    throw new SystemRuntimeException(FILE_UPLOAD_ERROR,
                            "completeUploadExternal failed: "
                                    + json.path("error").asText());
                }
            }

        } catch (IOException e) {
            throw new SystemRuntimeException(e);
        }
    }

    /**
     * Slack接続情報を返す
     * 
     * @return Slack接続情報
     */
    private SlackConnectionData getSlackConnectionData() {

        try {
            return new ObjectMapper().readValue(ssm.getValue(KEY_SLACK_TOKEN),
                    SlackConnectionData.class);
        } catch (Exception e) {
            throw new SystemRuntimeException(e);
        }
    }

    /**
     * 指定したコンテンツタイプから対応するSlack接続情報を取得する
     * 
     * @param data
     *     SlackAPIの接続情報
     * @param contentType
     *     コンテンツタイプ
     * @return Slack接続情報
     */
    private Connection getConnectionByContentType(SlackConnectionData data,
            ContentType contentType) {

        return data.getConnectionList().stream()
                .filter(e -> e.getContentType() == contentType)
                .findFirst()
                .orElseThrow(() -> new SystemRuntimeException(AWS_S3_DOWNLOAD_ERROR,
                        "jsonに対象のコンテンツタイプが存在しません. contentType="
                                + contentType.getValue()));
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
}
