package jp.co.ha.tool.db;

/**
 * カラム情報保持クラス
 *
 * @version 1.0.0
 * @param name
 *     カラム名
 * @param comment
 *     コメント
 * @param type
 *     カラム定義
 * @param isPrimary
 *     プライマリー
 * @param isSequence
 *     シーケンス
 * @param isCrypt
 *     暗号化
 * @param isNotNull
 *     NotNull制約
 * @param defaultValue
 *     デフォルト値
 */
public record Column(
        String name,
        String comment,
        String type,
        boolean isPrimary,
        boolean isSequence,
        boolean isCrypt,
        boolean isNotNull,
        String defaultValue) {
}
