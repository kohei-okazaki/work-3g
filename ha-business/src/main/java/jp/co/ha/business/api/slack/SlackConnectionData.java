package jp.co.ha.business.api.slack;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;

/**
 * SlackAPIの接続情報のJSONクラス
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SlackConnectionData {

    /** Botのトークン */
    @JsonProperty("token")
    private String token;
    /** 接続情報リスト */
    @JsonProperty("connections")
    private List<Connection> connectionList;

    /**
     * Botのトークンを返す
     *
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * Botのトークンを設定する
     *
     * @param token
     *     Botのトークン
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 接続情報リストを返す
     *
     * @return connectionList
     */
    public List<Connection> getConnectionList() {
        return connectionList;
    }

    /**
     * 接続情報リストを設定する
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
        @JsonProperty("channel_name")
        private String channelName;
        /** チャンネルID */
        @JsonProperty("channel_id")
        private String channelId;

        /**
         * コンテンツタイプを返す
         *
         * @return contentType
         */
        public ContentType getContentType() {
            return contentType;
        }

        /**
         * コンテンツタイプを設定する
         *
         * @param contentType
         *     コンテンツタイプ
         */
        public void setContentType(ContentType contentType) {
            this.contentType = contentType;
        }

        /**
         * チャンネル名を返す
         *
         * @return channelName
         */
        public String getChannelName() {
            return channelName;
        }

        /**
         * チャンネル名を設定する
         *
         * @param channelName
         *     チャンネル名
         */
        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        /**
         * チャンネルIDを返す
         * 
         * @return channelId
         */
        public String getChannelId() {
            return channelId;
        }

        /**
         * チャンネルIDを設定する
         * 
         * @param channelId
         *     チャンネルID
         */
        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

    }

}
