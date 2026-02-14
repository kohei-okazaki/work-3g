package jp.co.ha.business.dto;

/**
 * 問い合わせDTO
 * 
 * @param seqUserId
 *     ユーザID
 * @param title
 *     問い合わせ件名
 * @param type
 *     問い合わせタイプ
 * @param body
 *     問い合わせ詳細内容
 * @version 1.0.0
 */
public record InquiryDto(
        long seqUserId,
        String title,
        String type,
        String body) {
}
