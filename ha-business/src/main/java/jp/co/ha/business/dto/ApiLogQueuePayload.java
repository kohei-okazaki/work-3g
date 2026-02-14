package jp.co.ha.business.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jp.co.ha.common.util.HttpStatusDeserializer;
import jp.co.ha.common.util.HttpStatusSerializer;

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
        @JsonProperty("request_date") @JsonSerialize(using = LocalDateTimeSerializer.class) @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo") LocalDateTime requestDate,
        @JsonProperty("http_status") @JsonSerialize(using = HttpStatusSerializer.class) @JsonDeserialize(using = HttpStatusDeserializer.class) HttpStatus httpStatus,
        @JsonProperty("detail") String detail,
        @JsonProperty("response_date") @JsonSerialize(using = LocalDateTimeSerializer.class) @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo") LocalDateTime responseDate) {
}
