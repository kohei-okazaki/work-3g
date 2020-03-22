package jp.co.ha.business.api.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * APIのテストモード種別の列挙
 *
 * @since 1.0
 */
public enum TestMode implements BaseEnum {

    /** テストモード(DB登録を実施しない */
    TEST("1"),
    /** DB登録を行うモード */
    DB_REGIST("0");

    /** 値 */
    private String value;

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    private TestMode(String value) {
        this.value = value;
    }

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
     * @return TestMode
     */
    public static TestMode of(String value) {
        return BaseEnum.of(TestMode.class, value);
    }
}
