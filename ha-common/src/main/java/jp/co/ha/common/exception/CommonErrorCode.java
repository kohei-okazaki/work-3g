package jp.co.ha.common.exception;

import jp.co.ha.common.log.Logger.LogLevel;
import jp.co.ha.common.type.BaseEnum;

/**
 * 共通エラーコード
 *
 * @version 1.0.0
 */
public enum CommonErrorCode implements BaseErrorCode, BaseEnum {

    /* ERROR */
    /** 実行環境エラー */
    RUNTIME_ERROR("RUNTIME_ERROR", "CE0001", LogLevel.ERROR),
    /** 予期せぬ例外 */
    UNEXPECTED_ERROR("UNEXPECTED_ERROR", "CE0002", LogLevel.ERROR),
    /** アルゴリズムエラー */
    ALGORITH_ERROR("ALGORITH_ERROR", "CE0003", LogLevel.ERROR),
    /** DB暗号化・復号エラー */
    DB_ENCRYPT_ERROR("DB_CRYPT_ERROR", "CE0004", LogLevel.ERROR),
    /** DBアクセスエラー */
    DB_ACCESS_ERROR("DB_ACCESS_ERROR", "CE0005", LogLevel.ERROR),
    /** SQLの実行に失敗エラー */
    DB_SQL_EXE_ERROR("DB_SQL_EXE_ERROR", "CE0006", LogLevel.ERROR),
    /** SQL指定なしエラー */
    DB_SQL_SELECT_ERROR("DB_SQL_SELECT_ERROR", "CE0007", LogLevel.ERROR),
    /** DB切断エラー */
    DB_CLOSE_ERROR("DB_CLOSE_ERROR", "CE0008", LogLevel.ERROR),

    /* WARN */
    /** 該当データ存在しないエラー */
    DB_NO_DATA("DB_NO_DATA", "CW0009", LogLevel.WARN),
    /** JSON変換エラー例外 */
    JSON_FORMAT_ERROR("JSON_FORMAT_ERROR", "CW0010", LogLevel.WARN),
    /** JSON変換エラー例外 */
    JSON_MAPPING_ERROR("JSON_MAPPING_ERROR", "CW0011", LogLevel.WARN),
    /** JSON変換エラー例外 */
    JSON_PARSE_ERROR("JSON_PARSE_ERROR", "CW0012", LogLevel.WARN),
    /** ファイルアップロード例外 */
    FILE_UPLOAD_ERROR("FILE_UPLOAD_ERROR", "CW0013", LogLevel.WARN),
    /** ファイル読込例外 */
    FILE_READING_ERROR("FILE_READING_ERROR", "CW0014", LogLevel.WARN),
    /** ファイル書込処理エラー */
    FILE_WRITE_ERROR("FILE_WRITE_ERROR", "CW0015", LogLevel.WARN),
    /** データ重複エラー */
    MULTIPLE_DATA("MULTIPLE_DATA", "CW0016", LogLevel.WARN),
    /** 妥当性チェックエラー */
    VALIDATE_ERROR("VALIDATE_ERROR", "CW0017", LogLevel.WARN),
    /** ファイルまたはディレクトリ操作エラー */
    FILE_OR_DIR_ERROR("FILE_OR_DIR_ERROR", "CW0018", LogLevel.WARN);

    /**
     * コンストラクタ
     *
     * @param value
     *     value
     * @param outerErrorCode
     *     ErrorCode(外部用)
     * @param logLevel
     *     ログレベル
     */
    private CommonErrorCode(String value, String outerErrorCode, LogLevel logLevel) {
        this.value = value;
        this.outerErrorCode = outerErrorCode;
        this.logLevel = logLevel;
    }

    /** value */
    private String value;
    /** ErrorCode(外部用) */
    private String outerErrorCode;
    /** ログレベル */
    private LogLevel logLevel;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOuterErrorCode() {
        return this.outerErrorCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogLevel getLogLevel() {
        return this.logLevel;
    }

    /**
     * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
     * @param value
     *     値
     * @return CommonErrorCode
     */
    public static CommonErrorCode of(String value) {
        return BaseEnum.of(CommonErrorCode.class, value);
    }

}
