package jp.co.ha.business.api.slack;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentTypeDeserializer;

/**
 * SlackAPIの接続情報のJSONクラス
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SlackConnectionData {

    /** 接続情報リスト */
    @JsonProperty("connections")
    private List<Connection> connectionList;

    /**
     * connectionListを返す
     *
     * @return connectionList
     */
    public List<Connection> getConnectionList() {
        return connectionList;
    }

    /**
     * connectionListを設定する
     *
     * @param connectionList
     *     接続情報リスト
     */
    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }

    /**
     * 接続情報
     *
     * @version 1.0.0
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Connection {

        /** コンテンツタイプ */
        @JsonDeserialize(using = ContentTypeDeserializer.class)
        @JsonProperty("content_type")
        private ContentType contentType;
        /** チャンネル名 */
        @JsonProperty("name")
        private String channelName;
        /** Botのトークン */
        @JsonProperty("token")
        private String token;

        /**
         * contentTypeを返す
         *
         * @return contentType
         */
        public ContentType getContentType() {
            return contentType;
        }

        /**
         * contentTypeを設定する
         *
         * @param contentType
         *     コンテンツタイプ
         */
        public void setContentType(ContentType contentType) {
            this.contentType = contentType;
        }

        /**
         * channelNameを返す
         *
         * @return channelName
         */
        public String getChannelName() {
            return channelName;
        }

        /**
         * channelNameを設定する
         *
         * @param channelName
         *     チャンネル名
         */
        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        /**
         * tokenを返す
         *
         * @return token
         */
        public String getToken() {
            return token;
        }

        /**
         * tokenを設定する
         *
         * @param token
         *     Botのトークン
         */
        public void setToken(String token) {
            this.token = token;
        }
    }

}
