package jp.co.ha.business.api.track.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jp.co.ha.common.web.form.BaseApiResponse;

/**
 * 健康情報連携APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class HealthInfoMigrateApiResponse extends BaseTrackApiResponse
        implements BaseApiResponse {

    /** ID */
    @JsonProperty("id")
    private Long id;

    /** 連携日時 */
    @JsonProperty("synced_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
    private LocalDateTime syncedAt;

    /**
     * idを返す
     * 
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * idを設定する
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * syncedAtを返す
     * 
     * @return syncedAt
     */
    public LocalDateTime getSyncedAt() {
        return syncedAt;
    }

    /**
     * syncedAtを設定する
     * 
     * @param syncedAt
     */
    public void setSyncedAt(LocalDateTime syncedAt) {
        this.syncedAt = syncedAt;
    }

}
