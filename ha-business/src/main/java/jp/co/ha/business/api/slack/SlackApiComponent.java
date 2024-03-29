package jp.co.ha.business.api.slack;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.aws.AwsS3Key;
import jp.co.ha.business.api.slack.SlackConnectionData.Connection;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.system.SystemConfig;
import jp.co.ha.common.type.BaseEnum;

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
    /** {@linkplain AwsS3Component} */
    @Autowired
    private AwsS3Component s3;
    /** {@linkplain SystemConfig} */
    @Autowired
    private SystemConfig systemConfig;

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
            Connection conn = getConnectionByContentType(contentType);
            SlackSession session = SlackSessionFactory
                    .createWebSocketSlackSession(conn.getToken());
            String channelName = getChannelName(conn);

            session.connect();
            SlackChannel channel = session.findChannelByName(channelName);

            LOG.debug("send start. channel=" + channelName);
            session.sendMessage(channel, message);
            LOG.debug("send end. channel=" + channelName);

            session.disconnect();
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * Slackにファイルを送信する
     *
     * @param contentType
     *     コンテンツタイプ
     * @param data
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
    public void sendFile(ContentType contentType, byte[] data, String fileName,
            String title, String initialComment) throws BaseException {

        try {
            Connection conn = getConnectionByContentType(contentType);
            SlackSession session = SlackSessionFactory
                    .createWebSocketSlackSession(conn.getToken());

            session.connect();
            SlackChannel channel = session.findChannelByName(getChannelName(conn));

            LOG.debug("送信開始");
            session.sendFile(channel, new ByteArrayInputStream(data), fileName, title,
                    initialComment);
            LOG.debug("送信終了");

            session.disconnect();
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 指定したコンテンツタイプから対応するSlack接続情報を取得する
     *
     * @param contentType
     *     コンテンツタイプ
     * @return Slack接続情報
     * @throws BaseException
     *     JSONの読み込みに失敗した場合
     */
    private Connection getConnectionByContentType(ContentType contentType)
            throws BaseException {

        SlackConnectionData slackConnectionData = new JsonReader()
                .read(s3.getS3ObjectByKey(KEY), SlackConnectionData.class);

        return slackConnectionData.getConnectionList().stream()
                .filter(e -> e.getContentType() == contentType)
                .findFirst()
                .orElseThrow(() -> new BusinessException(
                        BusinessErrorCode.AWS_S3_DOWNLOAD_ERROR,
                        "jsonに対象のコンテンツタイプが存在しません. contentType="
                                + contentType.getValue()));
    }

    /**
     * チャンネル名を返す<br>
     * 環境名 + Slack接続情報.名前
     *
     * @param conn
     *     {@linkplain Connection}
     * @return チャンネル名
     */
    private String getChannelName(Connection conn) {
        return systemConfig.getEnvironment().getValue() + "_" + conn.getChannelName();
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
