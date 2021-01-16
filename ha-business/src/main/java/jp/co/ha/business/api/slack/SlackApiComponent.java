package jp.co.ha.business.api.slack;

import java.io.IOException;
import java.util.List;

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
    /** AWS-S3Component */
    @Autowired
    private AwsS3Component s3Component;

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

            session.connect();
            SlackChannel channel = session.findChannelByName(conn.getChannelName());

            LOG.debug("送信開始");
            session.sendMessage(channel, message);
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
                .read(s3Component.getS3ObjectByKey(KEY), SlackConnectionData.class);
        List<Connection> list = slackConnectionData.getConnectionList();

        return list.stream()
                .filter(e -> e.getContentType() == contentType)
                .findFirst()
                .orElseThrow(() -> new BusinessException(
                        BusinessErrorCode.AWS_S3_DOWNLOAD_ERROR,
                        "jsonに対象のコンテンツタイプが存在しません. contentType="
                                + contentType.getValue()));
    }

    /**
     * コンテンツタイプの列挙体
     * <ul>
     * <li>BATCH：健康管理バッチ</li>
     * <li>ROOT：管理者用サイト</li>
     * </ul>
     *
     * @version 1.0.0
     */
    public static enum ContentType implements BaseEnum {

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
