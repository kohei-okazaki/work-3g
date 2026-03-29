package jp.co.ha.business.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * API通信ログのSQS-Payload
 * 
 * @param transactionId
 *     トランザクションID
 * @param apiName
 *     API名
 * @param httpMethod
 *     HTTPメソッド
 * @param url
 *     URL
 * @param body
 *     リクエストボディ
 * @param requestDate
 *     要求日時
 * @param httpStatus
 *     HTTPステータス
 * @param detail
 *     詳細
 * @param responseDate
 *     応答日時
 * @version 1.0.0
 */
public record ApiLogQueuePayload(
        @JsonProperty("transaction_id") String transactionId,
        @JsonProperty("api_name") String apiName,
        @JsonProperty("http_method") String httpMethod,
        @JsonProperty("url") String url,
        @JsonProperty("body") String body,
        @JsonProperty("request_date") @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo") LocalDateTime requestDate,
        // JsonDeserialize自体は成功するが、recordへのバインドがうまくいかないためInteger型でNPE回避。
        // @JsonProperty("http_status") @JsonSerialize(using =
        // HttpStatusSerializer.class) @JsonDeserialize(using =
        // HttpStatusDeserializer.class) HttpStatus httpStatus,
        @JsonProperty("http_status") Integer httpStatus,
        @JsonProperty("detail") String detail,
        @JsonProperty("response_date") @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo") LocalDateTime responseDate) {
}
