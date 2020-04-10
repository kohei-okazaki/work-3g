package jp.co.ha.common.type;

/**
 * アルゴリズム列挙
 *
 * @version 1.0.0
 */
public enum Algorithm implements BaseEnum {

    /** AES */
    AES("AES"),
    /** SHA-256 */
    SHA_256("SHA-256"),
    /** SHA-512 */
    SHA_512("SHA-512");

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    private Algorithm(String value) {
        this.value = value;
    }

    /** 値 */
    private String value;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
     * @param value
     *     値
     * @return Algorithm
     */
    public static Algorithm of(String value) {
        return BaseEnum.of(Algorithm.class, value);
    }
}
